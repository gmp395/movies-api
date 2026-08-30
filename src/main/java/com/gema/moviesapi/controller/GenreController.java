package com.gema.moviesapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gema.moviesapi.dto.GenreDTORequest;
import com.gema.moviesapi.dto.GenreDTOResponse;
import com.gema.moviesapi.service.GenreService;

// @RestController = @Controller + @ResponseBody: cada método devuelve
// directamente JSON, en vez de tener que indicarlo aparte
@RestController
// @RequestMapping define la ruta base común a todos los endpoints de esta clase
@RequestMapping("/api/genres")
public class GenreController {

    // final: la dependencia no puede reasignarse una vez asignada en el constructor
    private final GenreService genreService;

    /*
     * Inyección por constructor (no @Autowired): Spring detecta
     * automáticamente que esta clase necesita un GenreService al ver
     * que solo tiene un constructor, y se lo pasa solo al crear el bean.
     */
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // GET /api/genres -> obtener todos los géneros
    @GetMapping
    public ResponseEntity<List<GenreDTOResponse>> findAll() {
        return ResponseEntity.ok(genreService.findAll());
    }

    // GET /api/genres/{id} -> obtener un género por su id
    @GetMapping("/{id}")
    public ResponseEntity<GenreDTOResponse> findById(@PathVariable Long id) {
        GenreDTOResponse genre = genreService.findById(id);
        if (genre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genre);
    }

    // POST /api/genres -> crear un nuevo género
    @PostMapping
    public ResponseEntity<GenreDTOResponse> save(@RequestBody GenreDTORequest dto) {
        GenreDTOResponse saved = genreService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/genres/{id} -> actualizar un género existente
    @PutMapping("/{id}")
    public ResponseEntity<GenreDTOResponse> update(@PathVariable Long id, @RequestBody GenreDTORequest dto) {
        GenreDTOResponse updated = genreService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/genres/{id} -> eliminar un género
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = genreService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}