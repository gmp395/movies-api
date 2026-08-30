package com.gema.moviesapi.dto;

import java.util.Set;

/*
 * DTO de entrada: lo que el cliente envía al crear o actualizar una película.
 * Las relaciones (género, año, actores) se representan solo con sus ids,
 * no con los objetos completos: el cliente indica a qué género/año/actores
 * ya existentes quiere asociar la película, no los crea de nuevo aquí.
 */
public record MovieDTORequest(
        String title,
        Long genreId,
        Long yearId,
        Set<Long> actorIds) {
}
