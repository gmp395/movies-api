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

import com.gema.moviesapi.dto.ActorDTORequest;
import com.gema.moviesapi.dto.ActorDTOResponse;
import com.gema.moviesapi.service.ActorService;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    // GET /api/actors -> obtener todos los actores
    @GetMapping
    public ResponseEntity<List<ActorDTOResponse>> findAll() {
        return ResponseEntity.ok(actorService.findAll());
    }

    // GET /api/actors/{id} -> obtener un actor por su id
    @GetMapping("/{id}")
    public ResponseEntity<ActorDTOResponse> findById(@PathVariable Long id) {
        ActorDTOResponse actor = actorService.findById(id);
        if (actor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actor);
    }

    // POST /api/actors -> crear un nuevo actor
    @PostMapping
    public ResponseEntity<ActorDTOResponse> save(@RequestBody ActorDTORequest dto) {
        ActorDTOResponse saved = actorService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/actors/{id} -> actualizar un actor existente
    @PutMapping("/{id}")
    public ResponseEntity<ActorDTOResponse> update(@PathVariable Long id, @RequestBody ActorDTORequest dto) {
        ActorDTOResponse updated = actorService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/actors/{id} -> eliminar un actor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = actorService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
