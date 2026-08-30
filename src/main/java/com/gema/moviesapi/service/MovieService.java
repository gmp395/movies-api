package com.gema.moviesapi.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gema.moviesapi.dto.MovieDTORequest;
import com.gema.moviesapi.dto.MovieDTOResponse;
import com.gema.moviesapi.entity.ActorEntity;
import com.gema.moviesapi.entity.GenreEntity;
import com.gema.moviesapi.entity.MovieEntity;
import com.gema.moviesapi.entity.YearEntity;
import com.gema.moviesapi.mapper.MovieMapper;
import com.gema.moviesapi.repository.ActorRepository;
import com.gema.moviesapi.repository.GenreRepository;
import com.gema.moviesapi.repository.MovieRepository;
import com.gema.moviesapi.repository.YearRepository;

@Service
public class MovieService {

    // final: ninguna de estas dependencias puede reasignarse tras el constructor
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final YearRepository yearRepository;
    private final ActorRepository actorRepository;

    /*
     * Inyección por constructor (no @Autowired): MovieService necesita
     * cuatro repositorios distintos, porque para construir una película
     * completa tiene que resolver sus relaciones con Genre, Year y Actor,
     * no solo guardar en su propia tabla.
     */
    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository,
            YearRepository yearRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.yearRepository = yearRepository;
        this.actorRepository = actorRepository;
    }

    // Obtener todas las películas
    public List<MovieDTOResponse> findAll() {
        return movieRepository.findAll().stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener una película por su id; devuelve null si no existe
    public MovieDTOResponse findById(Long id) {
        return movieRepository.findById(id)
                .map(MovieMapper::toDTO)
                .orElse(null);
    }

    /*
     * Crear una nueva película: antes de construir la Entity hay que
     * resolver genreId/yearId/actorIds a sus objetos reales, buscándolos
     * en sus repositorios. Si el género o el año indicados no existen,
     * se devuelve null (el Controller lo traducirá a un error 400/404).
     */
    public MovieDTOResponse save(MovieDTORequest dto) {
        GenreEntity genre = genreRepository.findById(dto.genreId()).orElse(null);
        YearEntity year = yearRepository.findById(dto.yearId()).orElse(null);

        if (genre == null || year == null) {
            return null;
        }

        Set<ActorEntity> actors = resolveActors(dto.actorIds());

        MovieEntity entity = MovieMapper.toEntity(dto, genre, year, actors);
        MovieEntity saved = movieRepository.save(entity);
        return MovieMapper.toDTO(saved);
    }

    // Actualizar una película existente; devuelve null si no existe,
    // o si el género/año indicados no existen
    public MovieDTOResponse update(Long id, MovieDTORequest dto) {
        MovieEntity existing = movieRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        GenreEntity genre = genreRepository.findById(dto.genreId()).orElse(null);
        YearEntity year = yearRepository.findById(dto.yearId()).orElse(null);

        if (genre == null || year == null) {
            return null;
        }

        existing.setTitle(dto.title());
        existing.setGenre(genre);
        existing.setYear(year);
        existing.setActors(resolveActors(dto.actorIds()));

        MovieEntity updated = movieRepository.save(existing);
        return MovieMapper.toDTO(updated);
    }

    // Eliminar una película por su id; devuelve false si no existía
    public boolean deleteById(Long id) {
        if (!movieRepository.existsById(id)) {
            return false;
        }
        movieRepository.deleteById(id);
        return true;
    }

    // Endpoint extra: buscar películas por título (coincidencia parcial)
    public List<MovieDTOResponse> findByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Endpoint extra: buscar películas por nombre de género
    public List<MovieDTOResponse> findByGenre(String genreName) {
        return movieRepository.findByGenreNameIgnoreCase(genreName).stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    /*
     * Método privado de apoyo: convierte el Set<Long> de ids de actores
     * del DTO en un Set<ActorEntity> real, buscando cada uno en el
     * repositorio. Los ids que no existan simplemente se ignoran
     * (no rompen la creación de la película por un actor mal escrito).
     */
    private Set<ActorEntity> resolveActors(Set<Long> actorIds) {
        if (actorIds == null) {
            return Set.of();
        }
        return actorIds.stream()
                .map(actorId -> actorRepository.findById(actorId).orElse(null))
                .filter(actor -> actor != null)
                .collect(Collectors.toSet());
    }
}