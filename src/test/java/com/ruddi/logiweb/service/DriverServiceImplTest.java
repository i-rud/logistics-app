package com.ruddi.logiweb.service;

import com.ruddi.logiweb.dao.api.DriverDao;
import com.ruddi.logiweb.dao.api.UserDao;
import com.ruddi.logiweb.dto.CargoDto;
import com.ruddi.logiweb.dto.DriverDto;
import com.ruddi.logiweb.dto.OrderDto;
import com.ruddi.logiweb.dto.TruckDto;
import com.ruddi.logiweb.model.Cargo;
import com.ruddi.logiweb.model.Driver;
import com.ruddi.logiweb.model.DriverStatus;
import com.ruddi.logiweb.model.Order;
import com.ruddi.logiweb.service.impl.DriverServiceImpl;
import com.ruddi.logiweb.service.impl.UserServiceImpl;
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

public class DriverServiceImplTest {
    @InjectMocks
    DriverServiceImpl service;

    @Mock
    DriverDao dao;

    @Mock
    UserServiceImpl userService;

    @Mock
    UserDao userDao;

    @Mock
    ModelMapper mapper;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveTest() {
        Driver driver = new Driver();
        Mockito.doNothing().when(dao).save(driver);
        Assertions.assertThrows(Exception.class, () -> service.save(mapper.map(driver, DriverDto.class)));
    }

    @Test
    void updateTest() {
        Driver driver = new Driver();
        Mockito.doNothing().when(dao).update(driver);
        Assertions.assertThrows(Exception.class, () -> service.update(mapper.map(driver, DriverDto.class)));
    }

//    @Test
//    void removeTest() {
//        Mockito.doNothing().when(dao).remove(2);
//        Mockito.when(service.find(2)).thenReturn(new DriverDto());
//        Mockito.when(userService.findByUsername("")).thenReturn(null);
//        Assertions.assertDoesNotThrow(() -> service.remove(2));
//    }

    @Test
    void getRestingTest() {
        List<Driver> drivers = new ArrayList<>();
        Mockito.when(dao.getResting()).thenReturn(drivers);
        Assertions.assertEquals(service.getResting(), drivers.stream()
                .map(entity -> mapper.map(entity, DriverDto.class)).collect(Collectors.toList()));
    }

    @Test
    void refreshTest() {
        Assertions.assertDoesNotThrow(() -> service.refresh());
    }
}
