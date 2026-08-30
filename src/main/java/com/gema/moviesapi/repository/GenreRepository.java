package com.gema.moviesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gema.moviesapi.entity.GenreEntity;

// <GenreEntity, Long> = entidad que gestiona, tipo de su id
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}