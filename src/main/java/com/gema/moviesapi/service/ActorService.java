package com.gema.moviesapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gema.moviesapi.dto.ActorDTORequest;
import com.gema.moviesapi.dto.ActorDTOResponse;
import com.gema.moviesapi.entity.ActorEntity;
import com.gema.moviesapi.mapper.ActorMapper;
import com.gema.moviesapi.repository.ActorRepository;

// @Service marca esta clase como un bean gestionado por Spring, para que
// pueda ser inyectada después en el Controller
@Service
public class ActorService {

    // final: la dependencia no puede reasignarse una vez asignada en el constructor
    private final ActorRepository actorRepository;

    /*
     * Inyección por constructor (no @Autowired): Spring detecta
     * automáticamente que esta clase necesita un ActorRepository al ver
     * que solo tiene un constructor, y se lo pasa solo al crear el bean.
     */
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    // Obtener todos los actores
    public List<ActorDTOResponse> findAll() {
        return actorRepository.findAll().stream()
                .map(ActorMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener un actor por su id; devuelve null si no existe
    public ActorDTOResponse findById(Long id) {
        return actorRepository.findById(id)
                .map(ActorMapper::toDTO)
                .orElse(null);
    }

    // Crear un nuevo actor
    public ActorDTOResponse save(ActorDTORequest dto) {
        ActorEntity entity = ActorMapper.toEntity(dto);
        ActorEntity saved = actorRepository.save(entity);
        return ActorMapper.toDTO(saved);
    }

    /*
     * Actualizar un actor existente: se busca la entidad ya guardada con
     * findById (mantiene su id intacto) y se modifica con el setter,
     * igual que en GenreService/YearService, para que Hibernate haga
     * un UPDATE real. Devuelve null si el actor no existe.
     */
    public ActorDTOResponse update(Long id, ActorDTORequest dto) {
        return actorRepository.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    ActorEntity updated = actorRepository.save(entity);
                    return ActorMapper.toDTO(updated);
                })
                .orElse(null);
    }

    // Eliminar un actor por su id; devuelve false si no existía
    public boolean deleteById(Long id) {
        if (!actorRepository.existsById(id)) {
            return false;
        }
        actorRepository.deleteById(id);
        return true;
    }
}