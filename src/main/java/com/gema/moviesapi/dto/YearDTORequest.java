package com.gema.moviesapi.dto;

/*
 * DTO de entrada: lo que el cliente envía al crear o actualizar un año.
 * No incluye "id" porque ese lo genera la base de datos, el cliente
 * no debe poder asignarlo ni modificarlo manualmente.
 */
public record YearDTORequest(Integer releaseYear) {
}