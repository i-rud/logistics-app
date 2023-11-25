package com.ruddi.logiweb.service.impl;

import com.ruddi.logiweb.dao.api.DriverDao;
import com.ruddi.logiweb.dto.DriverDto;
import com.ruddi.logiweb.dto.UserDto;
import com.ruddi.logiweb.model.Driver;
import com.ruddi.logiweb.model.UserRole;
import com.ruddi.logiweb.service.api.DriverService;
import com.ruddi.logiweb.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * driver service
 * @author Ivan Rud
 */
@Service
@Slf4j
public class DriverServiceImpl extends GenericServiceImpl<Driver, DriverDto, DriverDao> implements DriverService {
    @Autowired
    UserService userService;

    /**
     * password encoder
     */
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public DriverServiceImpl(DriverDao dao, ModelMapper mapper) {
        super(Driver.class, DriverDto.class, dao, mapper);
    }

    /**
     * saving driver
     * @param entity entity to save
     */
    @Override
    @Transactional
    public void save(DriverDto entity) {
        log.info("save(entity) method was called");
        UserDto user = UserDto.builder()
                .username(entity.getEmail())
                .password(passwordEncoder.encode(entity.getPassword()))
                .role(UserRole.DRIVER.getRole())
                .build();

        userService.save(user);
        dao.save(mapper.map(entity, Driver.class));
    }

    /**
     * updating driver
     * @param entity entity to update
     */
    @Override
    @Transactional
    public void update(DriverDto entity){
        log.info("update(entity) method was called");
        UserDto user = userService.findByUsername(entity.getEmail());
        if((entity.getPassword() != null && !entity.getPassword().equals(""))) {
            user.setPassword(passwordEncoder.encode(entity.getPassword()));
            userService.update(user);
        }

        dao.update(mapper.map(entity, Driver.class));
    }

    /**
     * removing driver by id
     * @param id entity id to remove
     */
    @Override
    @Transactional
    public void remove(int id) {
        log.info("remove(id) method was called");
        DriverDto driver = find(id);
        UserDto user = userService.findByUsername(driver.getEmail());
        userService.remove(user);
        dao.remove(id);
    }

    /**
     * getting list of resting drivers
     * @return list of resting drivers
     */
    @Override
    @Transactional(readOnly=true)
    public List<DriverDto> getResting() {
        log.info("getResting() method was called");
        return dao.getResting().stream().map(entity ->
                mapper.map(entity, DriverDto.class)).collect(Collectors.toList());
    }

    /**
     * refreshing drivers hours worked
     */
    @Override
    @Transactional
    public void refresh() {
        log.info("refresh() method was called");
        List<DriverDto> drivers = getAll();

        for(DriverDto driver : drivers) {
            driver.setHoursWorked(0);
            update(driver);
        }
    }
}
