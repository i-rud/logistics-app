package com.ruddi.logiweb.service;

import com.ruddi.logiweb.dao.api.CargoDao;
import com.ruddi.logiweb.dto.CargoDto;
import com.ruddi.logiweb.model.Cargo;
import com.ruddi.logiweb.service.impl.CargoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CargoServiceImplTest {
    @Mock
    CargoDao dao;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    CargoServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveTest() {
        Cargo cargo = new Cargo();
        Mockito.doNothing().when(dao).save(cargo);
        Assertions.assertDoesNotThrow(() -> service.save(mapper.map(cargo, CargoDto.class)));
    }

    @Test
    void updateTest() {
        Cargo cargo = new Cargo();
        Mockito.doNothing().when(dao).update(cargo);
        Assertions.assertDoesNotThrow(() -> service.update(mapper.map(cargo, CargoDto.class)));
    }

    @Test
    void removeByEntityTest() {
        Cargo cargo = new Cargo();
        Mockito.doNothing().when(dao).remove(cargo);
        Assertions.assertDoesNotThrow(() -> service.remove(mapper.map(cargo, CargoDto.class)));
    }

    @Test
    void removeByIdTest() {
        Mockito.doNothing().when(dao).remove(2);
        Assertions.assertDoesNotThrow(() -> service.remove(2));
    }

    @Test
    void findByIdTest() {
        Cargo cargo = new Cargo();
        Mockito.when(dao.find(3)).thenReturn(cargo);
        Assertions.assertEquals(service.find(3), mapper.map(cargo, CargoDto.class));
    }

    @Test
    void getAllTest() {
        List<Cargo> cargos = new ArrayList<>();
        Mockito.when(dao.getAll()).thenReturn(cargos);
        Assertions.assertEquals(service.getAll(), cargos.stream()
                .map(entity -> mapper.map(entity, CargoDto.class)).collect(Collectors.toList()));
    }

    @Test
    void getPreparedTest() {
        List<Cargo> cargos = new ArrayList<>();
        Mockito.when(dao.getPrepared()).thenReturn(cargos);
        Assertions.assertEquals(service.getPrepared(), cargos.stream()
                .map(entity -> mapper.map(entity, CargoDto.class)).collect(Collectors.toList()));
    }
 }
