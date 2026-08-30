package com.gema.moviesapi.dto;

/*
 * DTO de salida: lo que la API devuelve al cliente tras consultar o guardar
 * un género. Aquí sí incluye "id" porque el cliente necesita saberlo,
 * por ejemplo para usarlo después al crear una película con ese género.
 */
public record GenreDTOResponse(Long id, String name) {
}