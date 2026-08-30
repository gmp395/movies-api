package com.gema.moviesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gema.moviesapi.entity.YearEntity;

// <YearEntity, Long> = entidad que gestiona, tipo de su id
public interface YearRepository extends JpaRepository<YearEntity, Long> {
}