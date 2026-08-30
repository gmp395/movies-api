package com.gema.moviesapi.mapper;

import com.gema.moviesapi.dto.GenreDTORequest;
import com.gema.moviesapi.dto.GenreDTOResponse;
import com.gema.moviesapi.entity.GenreEntity;

public class GenreMapper {

    /*
     * Convierte el DTO de entrada en una Entity nueva, lista para guardar
     * en la base de datos. No se asigna "id" porque lo genera la BD.
     */
    public static GenreEntity toEntity(GenreDTORequest dto) {
        return new GenreEntity(dto.name());
    }

    /*
     * Convierte una Entity (ya guardada, con su id) en el DTO de salida
     * que se devuelve al cliente.
     */
    public static GenreDTOResponse toDTO(GenreEntity entity) {
        return new GenreDTOResponse(entity.getId(), entity.getName());
    }
}