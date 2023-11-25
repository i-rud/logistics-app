package com.ruddi.logiweb.service;

import com.ruddi.logiweb.dao.api.TruckDao;
import com.ruddi.logiweb.dto.TruckDto;
import com.ruddi.logiweb.model.Truck;
import com.ruddi.logiweb.service.impl.TruckServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TruckServiceImplTest {
    @Mock
    TruckDao dao;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    TruckServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAvailableTest() {
        List<Truck> trucks = new ArrayList<>();
        Mockito.when(dao.getAvailable()).thenReturn(trucks);
        Assertions.assertEquals(service.getAvailable(), trucks.stream()
                .map(entity -> mapper.map(entity, TruckDto.class)).collect(Collectors.toList()));
    }
}
