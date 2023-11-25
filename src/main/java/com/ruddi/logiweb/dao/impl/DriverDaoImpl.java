package com.ruddi.logiweb.dao.impl;

import com.ruddi.logiweb.dao.api.DriverDao;
import com.ruddi.logiweb.model.Driver;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverDaoImpl extends GenericDaoImpl<Driver> implements DriverDao {
    public DriverDaoImpl() {
        init(Driver.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Driver> getResting() {
        return entityManager.createQuery(
                "select a from Driver a where a.status='RESTING'").getResultList();
    }

    @Override
    public Driver findByEmail(String email) {
        return (Driver) entityManager.createQuery(
                "select a from Driver a where a.email=" + email).getSingleResult();
    }
}
