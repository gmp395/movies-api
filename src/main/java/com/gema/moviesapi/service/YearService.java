package com.gema.moviesapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gema.moviesapi.dto.YearDTORequest;
import com.gema.moviesapi.dto.YearDTOResponse;
import com.gema.moviesapi.entity.YearEntity;
import com.gema.moviesapi.mapper.YearMapper;
import com.gema.moviesapi.repository.YearRepository;

// @Service marca esta clase como un bean gestionado por Spring, para que
// pueda ser inyectada después en el Controller
@Service
public class YearService {

    // final: la dependencia no puede reasignarse una vez asignada en el constructor
    private final YearRepository yearRepository;

    /*
     * Inyección por constructor (no @Autowired): Spring detecta
     * automáticamente que esta clase necesita un YearRepository al ver
     * que solo tiene un constructor, y se lo pasa solo al crear el bean.
     */
    public YearService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    // Obtener todos los años
    public List<YearDTOResponse> findAll() {
        return yearRepository.findAll().stream()
                .map(YearMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener un año por su id; devuelve null si no existe
    public YearDTOResponse findById(Long id) {
        return yearRepository.findById(id)
                .map(YearMapper::toDTO)
                .orElse(null);
    }

    // Crear un nuevo año
    public YearDTOResponse save(YearDTORequest dto) {
        YearEntity entity = YearMapper.toEntity(dto);
        YearEntity saved = yearRepository.save(entity);
        return YearMapper.toDTO(saved);
    }

    /*
     * Actualizar un año existente: se busca la entidad ya guardada con
     * findById (mantiene su id intacto) y se modifica con el setter,
     * igual que en GenreService, para que Hibernate haga un UPDATE real.
     * Devuelve null si el año no existe.
     */
    public YearDTOResponse update(Long id, YearDTORequest dto) {
        return yearRepository.findById(id)
                .map(entity -> {
                    entity.setReleaseYear(dto.releaseYear());
                    YearEntity updated = yearRepository.save(entity);
                    return YearMapper.toDTO(updated);
                })
                .orElse(null);
    }

    // Eliminar un año por su id; devuelve false si no existía
    public boolean deleteById(Long id) {
        if (!yearRepository.existsById(id)) {
            return false;
        }
        yearRepository.deleteById(id);
        return true;
    }
}