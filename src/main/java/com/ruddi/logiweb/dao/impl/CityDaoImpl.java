package com.ruddi.logiweb.dao.impl;

import com.ruddi.logiweb.dao.api.CityDao;
import com.ruddi.logiweb.model.City;
import org.springframework.stereotype.Repository;

@Repository
public class CityDaoImpl extends GenericDaoImpl<City> implements CityDao {
    public CityDaoImpl() {
        init(City.class);
    }
}
