package com.gema.moviesapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gema.moviesapi.dto.GenreDTORequest;
import com.gema.moviesapi.dto.GenreDTOResponse;
import com.gema.moviesapi.entity.GenreEntity;
import com.gema.moviesapi.mapper.GenreMapper;
import com.gema.moviesapi.repository.GenreRepository;

// @Service marca esta clase como un bean gestionado por Spring, para que
// pueda ser inyectada después en el Controller
@Service
public class GenreService {

    // final: la dependencia no puede reasignarse una vez asignada en el constructor
    private final GenreRepository genreRepository;

    /*
     * Inyección por constructor (no @Autowired): Spring detecta
     * automáticamente que esta clase necesita un GenreRepository al ver
     * que solo tiene un constructor, y se lo pasa solo al crear el bean
     * (objeto que Spring crea y gestiona automáticamente por ti, para
     *  poder inyectarlo donde se necesite sin tener que escribir new a mano).
     */
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    // Obtener todos los géneros
    public List<GenreDTOResponse> findAll() {
        return genreRepository.findAll().stream()
                .map(GenreMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener un género por su id; devuelve null si no existe
    // (el Controller decidirá qué código HTTP devolver)
    public GenreDTOResponse findById(Long id) {
        return genreRepository.findById(id)
                .map(GenreMapper::toDTO)
                .orElse(null);
    }

    // Crear un nuevo género
    public GenreDTOResponse save(GenreDTORequest dto) {
        GenreEntity entity = GenreMapper.toEntity(dto);
        GenreEntity saved = genreRepository.save(entity);
        return GenreMapper.toDTO(saved);
    }

    /*
     * Actualizar un género existente. Se busca la entidad ya guardada con
     * findById (mantiene su id intacto) y se modifica con el setter, en
     * vez de borrarla y crear una nueva: así Hibernate hace un UPDATE real
     * sobre la fila existente, sin romper las películas que ya apunten a
     * este género mediante genre_id.
     * Devuelve null si el género no existe.
     */
    public GenreDTOResponse update(Long id, GenreDTORequest dto) {
        return genreRepository.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    GenreEntity updated = genreRepository.save(entity);
                    return GenreMapper.toDTO(updated);
                })
                .orElse(null);
    }

    // Eliminar un género por su id; devuelve false si no existía
    public boolean deleteById(Long id) {
        if (!genreRepository.existsById(id)) {
            return false;
        }
        genreRepository.deleteById(id);
        return true;
    }
}