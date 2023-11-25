package com.ruddi.logiweb.service;

import com.ruddi.logiweb.bl.GraphService;
import com.ruddi.logiweb.dao.api.CountryDao;
import com.ruddi.logiweb.dao.api.OrderDao;
import com.ruddi.logiweb.dao.impl.CountryDaoImpl;
import com.ruddi.logiweb.dto.OrderDto;
import com.ruddi.logiweb.model.Order;
import com.ruddi.logiweb.service.impl.CountryServiceImpl;
import com.ruddi.logiweb.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl service;

    @Mock
    CountryServiceImpl countryService;

    @Mock
    OrderDao dao;

    @Mock
    ModelMapper mapper;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void removeTest() {
        Order order = new Order();
        order.setCargos(new ArrayList<>());
        order.setDrivers(new ArrayList<>());

        Mockito.when(dao.find(2)).thenReturn(order);
        Mockito.doNothing().when(dao).remove(2);

        Assertions.assertDoesNotThrow(() -> service.remove(2));
    }
}
