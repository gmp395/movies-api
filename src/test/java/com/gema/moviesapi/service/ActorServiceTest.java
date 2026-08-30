package com.gema.moviesapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gema.moviesapi.dto.ActorDTORequest;
import com.gema.moviesapi.dto.ActorDTOResponse;
import com.gema.moviesapi.entity.ActorEntity;
import com.gema.moviesapi.repository.ActorRepository;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorService actorService;

    @Test
    void findAll_devuelveListaDeActores() {
        ActorEntity actor = new ActorEntity("Keanu Reaves");
        when(actorRepository.findAll()).thenReturn(List.of(actor));

        List<ActorDTOResponse> result = actorService.findAll();

        assertEquals(1, result.size());
        assertEquals("Keanu Reaves", result.get(0).name());
    }

    @Test
    void findById_devuelveNullSiNoExiste() {
        when(actorRepository.findById(99L)).thenReturn(Optional.empty());

        ActorDTOResponse result = actorService.findById(99L);

        assertNull(result);
    }

    @Test
    void save_creaUnActorNuevo() {
        ActorDTORequest dto = new ActorDTORequest("Carrie-Anne Moss");
        ActorEntity savedEntity = new ActorEntity("Carrie-Anne Moss");
        when(actorRepository.save(org.mockito.ArgumentMatchers.any(ActorEntity.class)))
                .thenReturn(savedEntity);

        ActorDTOResponse result = actorService.save(dto);

        assertEquals("Carrie-Anne Moss", result.name());
    }
}