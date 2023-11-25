package com.ruddi.logiweb.service.impl;

import com.ruddi.logiweb.dao.api.TruckDao;
import com.ruddi.logiweb.dto.TruckDto;
import com.ruddi.logiweb.model.Truck;
import com.ruddi.logiweb.service.api.TruckService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TruckServiceImpl extends GenericServiceImpl<Truck, TruckDto, TruckDao> implements TruckService {
    @Autowired
    public TruckServiceImpl(TruckDao dao, ModelMapper mapper) {
        super(Truck.class, TruckDto.class, dao, mapper);
    }

    @Override
    @Transactional(readOnly=true)
    public List<TruckDto> getAvailable() {
        log.info("getAvailable() method was called");
        return dao.getAvailable().stream().map(entity ->
                mapper.map(entity, TruckDto.class)).collect(Collectors.toList());
    }
}
