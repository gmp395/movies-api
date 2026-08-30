package com.gema.moviesapi.mapper;

import com.gema.moviesapi.dto.ActorDTORequest;
import com.gema.moviesapi.dto.ActorDTOResponse;
import com.gema.moviesapi.entity.ActorEntity;

public class ActorMapper {

    /*
     * Convierte el DTO de entrada en una Entity nueva, lista para guardar
     * en la base de datos. No se asigna "id" porque lo genera la BD.
     */
    public static ActorEntity toEntity(ActorDTORequest dto) {
        return new ActorEntity(dto.name());
    }

    /*
     * Convierte una Entity (ya guardada, con su id) en el DTO de salida
     * que se devuelve al cliente.
     */
    public static ActorDTOResponse toDTO(ActorEntity entity) {
        return new ActorDTOResponse(entity.getId(), entity.getName());
    }
}