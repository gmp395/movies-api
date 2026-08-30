package com.gema.moviesapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity le dice a Hibernate que esta clase representa una tabla en la BD
@Entity
// @Table define el nombre exacto de la tabla; si no se pone, usaría el nombre de la clase
@Table(name = "genres")
public class GenreEntity {

    // @Id marca este campo como clave primaria de la tabla
    @Id
    // @GeneratedValue indica que el id lo genera automáticamente la BD (auto-increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Long (no int) porque puede ser null antes de guardar y admite valores grandes
    private Long id;

    // Nombre del género (ej. "Comedia", "Acción")
    private String name;

    // Constructor vacío: obligatorio para que Hibernate pueda crear instancias
    public GenreEntity() {
    }

    // Constructor con argumento: para crear objetos nuevos cómodamente desde el código
    public GenreEntity(String name) {
        this.name = name;
    }

    // Getter de id: solo lectura, no hay setter porque el id lo asigna la BD
    public Long getId() {
        return id;
    }

    // Getter de name
    public String getName() {
        return name;
    }

    // Setter de name: permite modificar el nombre desde fuera de la clase
    public void setName(String name) {
        this.name = name;
    }
}