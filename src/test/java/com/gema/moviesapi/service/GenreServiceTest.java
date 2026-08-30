package com.gema.moviesapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gema.moviesapi.dto.GenreDTORequest;
import com.gema.moviesapi.dto.GenreDTOResponse;
import com.gema.moviesapi.entity.GenreEntity;
import com.gema.moviesapi.repository.GenreRepository;

// @ExtendWith activa Mockito para esta clase de test
@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    // @Mock crea una versión falsa del repository, controlada por nosotras
    @Mock
    private GenreRepository genreRepository;

    // @InjectMocks crea un GenreService real, pero le inyecta el mock de arriba
    @InjectMocks
    private GenreService genreService;

    @Test
    void findAll_devuelveListaDeGeneros() {
        // Arrange: preparamos lo que el mock debe devolver cuando se le pregunte
        GenreEntity genre = new GenreEntity("Comedia");
        when(genreRepository.findAll()).thenReturn(List.of(genre));

        // Act: ejecutamos el método real que queremos probar
        List<GenreDTOResponse> result = genreService.findAll();

        // Assert: comprobamos que el resultado es el esperado
        assertEquals(1, result.size());
        assertEquals("Comedia", result.get(0).name());
    }

    @Test
    void findById_devuelveNullSiNoExiste() {
        when(genreRepository.findById(99L)).thenReturn(Optional.empty());

        GenreDTOResponse result = genreService.findById(99L);

        assertNull(result);
    }

    @Test
    void save_creaUnGeneroNuevo() {
        GenreDTORequest dto = new GenreDTORequest("Terror");
        GenreEntity savedEntity = new GenreEntity("Terror");
        when(genreRepository.save(org.mockito.ArgumentMatchers.any(GenreEntity.class)))
                .thenReturn(savedEntity);

        GenreDTOResponse result = genreService.save(dto);

        assertEquals("Terror", result.name());
    }
}