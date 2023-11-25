package com.ruddi.logiweb.dao.impl;

import com.ruddi.logiweb.dao.api.TruckDao;
import com.ruddi.logiweb.model.Truck;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckDaoImpl extends GenericDaoImpl<Truck> implements TruckDao {
    public TruckDaoImpl() {
        init(Truck.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Truck> getAvailable() {
        return entityManager.createQuery(
                "select a from Truck a where a.condition='OK' and a.status='FREE'").getResultList();
    }
}
