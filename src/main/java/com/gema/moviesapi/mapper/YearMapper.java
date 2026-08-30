package com.gema.moviesapi.mapper;

import com.gema.moviesapi.dto.YearDTORequest;
import com.gema.moviesapi.dto.YearDTOResponse;
import com.gema.moviesapi.entity.YearEntity;

public class YearMapper {

    /*
     * Convierte el DTO de entrada en una Entity nueva, lista para guardar
     * en la base de datos. No se asigna "id" porque lo genera la BD.
     */
    public static YearEntity toEntity(YearDTORequest dto) {
        return new YearEntity(dto.releaseYear());
    }

    /*
     * Convierte una Entity (ya guardada, con su id) en el DTO de salida
     * que se devuelve al cliente.
     */
    public static YearDTOResponse toDTO(YearEntity entity) {
        return new YearDTOResponse(entity.getId(), entity.getReleaseYear());
    }
}