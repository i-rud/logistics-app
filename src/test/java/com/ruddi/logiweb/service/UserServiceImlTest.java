package com.ruddi.logiweb.service;

import com.ruddi.logiweb.dao.api.UserDao;
import com.ruddi.logiweb.model.User;
import com.ruddi.logiweb.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class UserServiceImlTest {

    @Mock
    UserDao dao;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    UserServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findByUsernameTest() {
        String username = " ";
        User user = new User();

        Mockito.when(dao.findUserByUsername(username)).thenReturn(user);
        Assertions.assertDoesNotThrow(() -> service.findByUsername(username));
    }

    @Test
    void loadByUserNameTest() {
        String username = " ";
        User user = new User();

        Mockito.when(dao.findUserByUsername(username)).thenReturn(user);
        Assertions.assertDoesNotThrow(() -> service.loadUserByUsername(username));
    }
}
