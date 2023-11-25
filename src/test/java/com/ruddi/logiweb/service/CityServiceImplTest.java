package com.ruddi.logiweb.service;

import com.ruddi.logiweb.dao.api.CityDao;
import com.ruddi.logiweb.service.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class CityServiceImplTest {
    @Mock
    CityDao dao;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    CityServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test() {

    }
}
