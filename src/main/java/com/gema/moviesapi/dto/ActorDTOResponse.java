package com.gema.moviesapi.dto;

/*
 * DTO de salida: lo que la API devuelve al cliente tras consultar o guardar
 * un actor. Aquí sí incluye "id" porque el cliente necesita saberlo,
 * por ejemplo para usarlo después al asociarlo a una película.
 */
public record ActorDTOResponse(Long id, String name) {
}