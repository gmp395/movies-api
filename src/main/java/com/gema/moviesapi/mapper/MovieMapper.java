package com.gema.moviesapi.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.gema.moviesapi.dto.ActorDTOResponse;
import com.gema.moviesapi.dto.MovieDTORequest;
import com.gema.moviesapi.dto.MovieDTOResponse;
import com.gema.moviesapi.entity.ActorEntity;
import com.gema.moviesapi.entity.GenreEntity;
import com.gema.moviesapi.entity.MovieEntity;
import com.gema.moviesapi.entity.YearEntity;

public class MovieMapper {

    /*
     * A diferencia de los otros mappers, aquí no basta con los datos del DTO:
     * genre, year y actors llegan ya resueltos (buscados previamente en la
     * base de datos por el Service, usando los ids del DTORequest), porque
     * el Mapper no debe depender de los Repositories para hacer consultas.
     */
    public static MovieEntity toEntity(MovieDTORequest dto, GenreEntity genre, YearEntity year, Set<ActorEntity> actors) {
        MovieEntity movie = new MovieEntity(dto.title(), genre, year);
        movie.setActors(actors);
        return movie;
    }

    /*
     * Convierte una Entity (ya guardada, con sus relaciones cargadas) en el
     * DTO de salida. Aquí sí se reutilizan los otros Mappers (Genre, Year,
     * Actor) para construir los DTOs anidados del Response.
     */
    public static MovieDTOResponse toDTO(MovieEntity entity) {
        Set<ActorDTOResponse> actorDTOs = entity.getActors().stream()
                .map(ActorMapper::toDTO)
                .collect(Collectors.toSet());

        return new MovieDTOResponse(
                entity.getId(),
                entity.getTitle(),
                GenreMapper.toDTO(entity.getGenre()),
                YearMapper.toDTO(entity.getYear()),
                actorDTOs);
    }
}