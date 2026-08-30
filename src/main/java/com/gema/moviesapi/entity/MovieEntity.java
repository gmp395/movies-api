package com.gema.moviesapi.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// @Entity le dice a Hibernate que esta clase representa una tabla en la BD
@Entity
// @Table define el nombre exacto de la tabla; si no se pone, usaría el nombre de la clase
@Table(name = "movies")
public class MovieEntity {

    // @Id marca este campo como clave primaria de la tabla
    @Id
    // @GeneratedValue indica que el id lo genera automáticamente la BD (auto-increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Long (no int) porque puede ser null antes de guardar y admite valores grandes
    private Long id;

    // Título de la película
    private String title;

    // @ManyToOne: muchas películas pueden tener el mismo género (lado "muchos")
    @ManyToOne
    // @JoinColumn crea la columna de clave foránea "genre_id" en la tabla movies,
    // que apunta al id de la tabla genres
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    // @ManyToOne: muchas películas pueden tener el mismo año (lado "muchos")
    @ManyToOne
    // @JoinColumn crea la columna de clave foránea "year_id" en la tabla movies,
    // que apunta al id de la tabla years
    @JoinColumn(name = "year_id")
    private YearEntity year;

    // @ManyToMany: una película puede tener varios actores, y un actor puede
    // participar en varias películas
    @ManyToMany
    // @JoinTable crea la tabla intermedia "movie_actor" que guarda los pares
    // movie_id / actor_id. joinColumns = columna que apunta a esta entidad (Movie);
    // inverseJoinColumns = columna que apunta a la otra entidad (Actor)
    @JoinTable(
        name = "movie_actor",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    // Se usa Set en vez de List porque un mismo actor no debería poder aparecer
    // dos veces en la misma película; Set no permite elementos duplicados,
    // y es la convención estándar en JPA para relaciones @ManyToMany
    // Se inicializa como new HashSet<>() directamente aquí, en vez de dejarlo
    // sin inicializar, para que el campo nunca sea null y evitar
    // NullPointerException al intentar añadir actores más adelante
    private Set<ActorEntity> actors = new HashSet<>();

    // Constructor vacío: obligatorio para que Hibernate pueda crear instancias
    public MovieEntity() {
    }

    // Constructor con argumentos: para crear objetos nuevos cómodamente desde el código
    public MovieEntity(String title, GenreEntity genre, YearEntity year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    // Getter de id: solo lectura, no hay setter porque el id lo asigna la BD
    public Long getId() {
        return id;
    }

    // Getter y setter de title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter y setter de genre
    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }

    // Getter y setter de year
    public YearEntity getYear() {
        return year;
    }

    public void setYear(YearEntity year) {
        this.year = year;
    }

    // Getter y setter de actors
    public Set<ActorEntity> getActors() {
        return actors;
    }

    public void setActors(Set<ActorEntity> actors) {
        this.actors = actors;
    }
}