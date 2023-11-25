package com.ruddi.logiweb.dao.api;

import com.ruddi.logiweb.model.Driver;

import java.util.List;

public interface DriverDao extends GenericDao<Driver> {
    List<Driver> getResting();
    Driver findByEmail(String email);
}
