package com.gema.moviesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gema.moviesapi.entity.ActorEntity;

// <ActorEntity, Long> = entidad que gestiona, tipo de su id
public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
}
