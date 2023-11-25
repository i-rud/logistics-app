package com.ruddi.logiweb.service.impl;

import com.ruddi.logiweb.dao.api.CargoDao;
import com.ruddi.logiweb.dto.CargoDto;
import com.ruddi.logiweb.model.Cargo;
import com.ruddi.logiweb.service.api.CargoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * cargo service class
 * @author Ivan Rud
 */
@Service
@Slf4j
public class CargoServiceImpl extends GenericServiceImpl<Cargo, CargoDto, CargoDao> implements CargoService {
    @Autowired
    public CargoServiceImpl(CargoDao dao, ModelMapper mapper) {
        super(Cargo.class, CargoDto.class, dao, mapper);
    }

    /**
     * getting prepared cargo
     * @return list of prepared cargos
     */
    @Override
    @Transactional(readOnly=true)
    public List<CargoDto> getPrepared() {
        log.info("getPrepared() method was called");
        return dao.getPrepared().stream().map(entity ->
                mapper.map(entity, CargoDto.class)).collect(Collectors.toList());
    }
}
