package com.ruddi.logiweb.dao.api;

import com.ruddi.logiweb.model.Truck;

import java.util.List;

public interface TruckDao extends GenericDao<Truck> {
    List<Truck> getAvailable();
}
