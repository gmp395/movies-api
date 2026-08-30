package com.gema.moviesapi.dto;

import java.util.Set;

/*
 * DTO de salida: lo que la API devuelve al cliente tras consultar o guardar
 * una película. A diferencia del Request, aquí sí se incluyen los DTOs
 * completos de género, año y actores (no solo sus ids), para que el
 * cliente reciba toda la información sin tener que hacer llamadas
 * adicionales a la API.
 */
public record MovieDTOResponse(
        Long id,
        String title,
        GenreDTOResponse genre,
        YearDTOResponse year,
        Set<ActorDTOResponse> actors) {
}