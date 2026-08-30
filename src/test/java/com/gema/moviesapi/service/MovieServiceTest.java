package com.gema.moviesapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gema.moviesapi.dto.MovieDTORequest;
import com.gema.moviesapi.dto.MovieDTOResponse;
import com.gema.moviesapi.entity.GenreEntity;
import com.gema.moviesapi.entity.MovieEntity;
import com.gema.moviesapi.entity.YearEntity;
import com.gema.moviesapi.repository.ActorRepository;
import com.gema.moviesapi.repository.GenreRepository;
import com.gema.moviesapi.repository.MovieRepository;
import com.gema.moviesapi.repository.YearRepository;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    // MovieService necesita CUATRO repositorios mockeados, uno por cada
    // relación que tiene que resolver (Genre, Year, Actor) más el suyo propio
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private YearRepository yearRepository;
    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void save_creaUnaPeliculaCuandoGeneroYAnioExisten() {
        GenreEntity genre = new GenreEntity("Acción");
        YearEntity year = new YearEntity(1999);
        MovieEntity savedMovie = new MovieEntity("The Matrix", genre, year);

        MovieDTORequest dto = new MovieDTORequest("The Matrix", 1L, 1L, Set.of());

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(yearRepository.findById(1L)).thenReturn(Optional.of(year));
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(savedMovie);

        MovieDTOResponse result = movieService.save(dto);

        assertEquals("The Matrix", result.title());
    }

    @Test
    void save_devuelveNullSiElGeneroNoExiste() {
        MovieDTORequest dto = new MovieDTORequest("The Matrix", 99L, 1L, Set.of());

        when(genreRepository.findById(99L)).thenReturn(Optional.empty());
        when(yearRepository.findById(1L)).thenReturn(Optional.of(new YearEntity(1999)));

        MovieDTOResponse result = movieService.save(dto);

        assertNull(result);
    }

    @Test
    void findByTitle_devuelvePeliculasQueCoincidenConElTitulo() {
        GenreEntity genre = new GenreEntity("Acción");
        YearEntity year = new YearEntity(1999);
        MovieEntity movie = new MovieEntity("The Matrix", genre, year);

        when(movieRepository.findByTitleContainingIgnoreCase("matrix"))
                .thenReturn(List.of(movie));

        List<MovieDTOResponse> result = movieService.findByTitle("matrix");

        assertEquals(1, result.size());
        assertEquals("The Matrix", result.get(0).title());
    }
}