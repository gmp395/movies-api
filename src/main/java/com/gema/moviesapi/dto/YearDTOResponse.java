package com.gema.moviesapi.dto;

/*
 * DTO de salida: lo que la API devuelve al cliente tras consultar o guardar
 * un año. Aquí sí incluye "id" porque el cliente necesita saberlo,
 * por ejemplo para usarlo después al crear una película con ese año.
 */
public record YearDTOResponse(Long id, Integer releaseYear) {
}