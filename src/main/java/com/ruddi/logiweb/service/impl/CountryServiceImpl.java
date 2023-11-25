package com.ruddi.logiweb.service.impl;

import com.ruddi.logiweb.dao.api.CountryDao;
import com.ruddi.logiweb.dto.CountryDto;
import com.ruddi.logiweb.model.Country;
import com.ruddi.logiweb.service.api.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl extends GenericServiceImpl<Country, CountryDto, CountryDao> implements CountryService {
    @Autowired
    public CountryServiceImpl(CountryDao dao, ModelMapper mapper) {
        super(Country.class, CountryDto.class, dao, mapper);
    }
}
