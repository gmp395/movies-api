package com.gema.moviesapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gema.moviesapi.entity.MovieEntity;

// <MovieEntity, Long> = entidad que gestiona, tipo de su id
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    // Spring genera el SQL automáticamente a partir del nombre del método:
    // "buscar películas donde title contenga el texto, sin distinguir mayúsculas/minúsculas"
    List<MovieEntity> findByTitleContainingIgnoreCase(String title);

    // "buscar películas donde genre.name sea igual al valor, sin distinguir mayúsculas/minúsculas"
    List<MovieEntity> findByGenreNameIgnoreCase(String genreName);
}