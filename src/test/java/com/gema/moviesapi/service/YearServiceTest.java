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

import com.gema.moviesapi.dto.YearDTORequest;
import com.gema.moviesapi.dto.YearDTOResponse;
import com.gema.moviesapi.entity.YearEntity;
import com.gema.moviesapi.repository.YearRepository;

@ExtendWith(MockitoExtension.class)
class YearServiceTest {

    @Mock
    private YearRepository yearRepository;

    @InjectMocks
    private YearService yearService;

    @Test
    void findAll_devuelveListaDeAnios() {
        YearEntity year = new YearEntity(1999);
        when(yearRepository.findAll()).thenReturn(List.of(year));

        List<YearDTOResponse> result = yearService.findAll();

        assertEquals(1, result.size());
        assertEquals(1999, result.get(0).releaseYear());
    }

    @Test
    void findById_devuelveNullSiNoExiste() {
        when(yearRepository.findById(99L)).thenReturn(Optional.empty());

        YearDTOResponse result = yearService.findById(99L);

        assertNull(result);
    }

    @Test
    void save_creaUnAnioNuevo() {
        YearDTORequest dto = new YearDTORequest(2010);
        YearEntity savedEntity = new YearEntity(2010);
        when(yearRepository.save(org.mockito.ArgumentMatchers.any(YearEntity.class)))
                .thenReturn(savedEntity);

        YearDTOResponse result = yearService.save(dto);

        assertEquals(2010, result.releaseYear());
    }
}
