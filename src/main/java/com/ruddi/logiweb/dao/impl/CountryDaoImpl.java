package com.ruddi.logiweb.dao.impl;

import com.ruddi.logiweb.dao.api.CountryDao;
import com.ruddi.logiweb.model.Country;
import org.springframework.stereotype.Repository;

@Repository
public class CountryDaoImpl extends GenericDaoImpl<Country> implements CountryDao {
    public CountryDaoImpl() {
        init(Country.class);
    }
}
