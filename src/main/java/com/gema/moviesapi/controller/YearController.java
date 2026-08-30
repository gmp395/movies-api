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

import com.gema.moviesapi.dto.YearDTORequest;
import com.gema.moviesapi.dto.YearDTOResponse;
import com.gema.moviesapi.service.YearService;

@RestController
@RequestMapping("/api/years")
public class YearController {

    private final YearService yearService;

    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    // GET /api/years -> obtener todos los años
    @GetMapping
    public ResponseEntity<List<YearDTOResponse>> findAll() {
        return ResponseEntity.ok(yearService.findAll());
    }

    // GET /api/years/{id} -> obtener un año por su id
    @GetMapping("/{id}")
    public ResponseEntity<YearDTOResponse> findById(@PathVariable Long id) {
        YearDTOResponse year = yearService.findById(id);
        if (year == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(year);
    }

    // POST /api/years -> crear un nuevo año
    @PostMapping
    public ResponseEntity<YearDTOResponse> save(@RequestBody YearDTORequest dto) {
        YearDTOResponse saved = yearService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/years/{id} -> actualizar un año existente
    @PutMapping("/{id}")
    public ResponseEntity<YearDTOResponse> update(@PathVariable Long id, @RequestBody YearDTORequest dto) {
        YearDTOResponse updated = yearService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/years/{id} -> eliminar un año
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = yearService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
