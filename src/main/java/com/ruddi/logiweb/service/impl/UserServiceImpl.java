package com.ruddi.logiweb.service.impl;

import com.ruddi.logiweb.dao.api.UserDao;
import com.ruddi.logiweb.dto.UserDto;
import com.ruddi.logiweb.model.CustomUserDetails;
import com.ruddi.logiweb.model.User;
import com.ruddi.logiweb.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Slf4j
public class UserServiceImpl extends GenericServiceImpl<User, UserDto, UserDao> implements UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao dao, ModelMapper mapper) {
        super(User.class, UserDto.class, dao, mapper);
    }

    @Override
    @Transactional(readOnly=true)
    public UserDto findByUsername(String username) {
        log.info("findByUsername(username) method was called");
        return mapper.map(dao.findUserByUsername(username), UserDto.class);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.findUserByUsername(username);
        log.info("loadByUsername(username) method was called");
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        return customUserDetails;
    }
}
