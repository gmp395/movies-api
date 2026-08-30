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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gema.moviesapi.dto.MovieDTORequest;
import com.gema.moviesapi.dto.MovieDTOResponse;
import com.gema.moviesapi.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // GET /api/movies -> obtener todas las películas
    @GetMapping
    public ResponseEntity<List<MovieDTOResponse>> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    // GET /api/movies/{id} -> obtener una película por su id
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTOResponse> findById(@PathVariable Long id) {
        MovieDTOResponse movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    // POST /api/movies -> crear una nueva película
    @PostMapping
    public ResponseEntity<MovieDTOResponse> save(@RequestBody MovieDTORequest dto) {
        MovieDTOResponse saved = movieService.save(dto);
        if (saved == null) {
            // El genreId o yearId enviados no corresponden a ningún registro existente
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/movies/{id} -> actualizar una película existente
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTOResponse> update(@PathVariable Long id, @RequestBody MovieDTORequest dto) {
        MovieDTOResponse updated = movieService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/movies/{id} -> eliminar una película
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = movieService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    /*
     * Sexto endpoint (findBy): busca películas por título o por género,
     * según qué parámetro venga en la URL. Ejemplos:
     * GET /api/movies/search?title=matrix
     * GET /api/movies/search?genre=comedia
     */
    @GetMapping("/search")
    public ResponseEntity<List<MovieDTOResponse>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre) {

        if (title != null) {
            return ResponseEntity.ok(movieService.findByTitle(title));
        }
        if (genre != null) {
            return ResponseEntity.ok(movieService.findByGenre(genre));
        }
        return ResponseEntity.badRequest().build();
    }
}