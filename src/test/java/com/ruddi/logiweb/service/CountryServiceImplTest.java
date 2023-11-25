package com.ruddi.logiweb.service;

import com.ruddi.logiweb.dao.api.CountryDao;
import com.ruddi.logiweb.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class CountryServiceImplTest {
    @Mock
    CountryDao dao;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    CountryServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test() {

    }
}
