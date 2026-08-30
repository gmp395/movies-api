package com.gema.moviesapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity le dice a Hibernate que esta clase representa una tabla en la BD
@Entity
// @Table define el nombre exacto de la tabla; si no se pone, usaría el nombre de la clase
@Table(name = "years")
public class YearEntity {

    // @Id marca este campo como clave primaria de la tabla
    @Id
    // @GeneratedValue indica que el id lo genera automáticamente la BD (auto-increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Long (no int) porque puede ser null antes de guardar y admite valores grandes
    private Long id;

    /*
     * Se llama "releaseYear" y no "year" ni "value" porque ambos son
     * palabras reservadas en distintos motores SQL: YEAR es tipo de dato
     * en MySQL, y VALUE se usa en sentencias INSERT en H2 (como comprobamos
     * al arrancar la app por primera vez). releaseYear evita conflictos
     * en cualquier motor y además es más descriptivo.
     */
    // Integer (wrapper) en vez de int, para poder ser null antes de guardar
    private Integer releaseYear;

    // Constructor vacío: obligatorio para que Hibernate pueda crear instancias
    public YearEntity() {
    }

    // Constructor con argumento: para crear objetos nuevos cómodamente desde el código
    public YearEntity(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    // Getter de id: solo lectura, no hay setter porque el id lo asigna la BD
    public Long getId() {
        return id;
    }

    // Getter de releaseYear
    public Integer getReleaseYear() {
        return releaseYear;
    }

    // Setter de releaseYear: permite modificar el año desde fuera de la clase
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}