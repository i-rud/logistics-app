package com.ruddi.logiweb.service.impl;

import com.ruddi.logiweb.dao.api.CityDao;
import com.ruddi.logiweb.dto.CityDto;
import com.ruddi.logiweb.model.City;
import com.ruddi.logiweb.service.api.CityService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CityServiceImpl extends GenericServiceImpl<City, CityDto, CityDao> implements CityService {
    @Autowired
    public CityServiceImpl(CityDao dao, ModelMapper mapper) {
        super(City.class, CityDto.class, dao, mapper);
    }
}
