package com.ruddi.logiweb.dao.impl;

import com.ruddi.logiweb.dao.api.CargoDao;
import com.ruddi.logiweb.model.Cargo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CargoDaoImpl extends GenericDaoImpl<Cargo> implements CargoDao {
    public CargoDaoImpl() {
        init(Cargo.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Cargo> getPrepared() {
        return entityManager.createQuery(
                "select a from Cargo a where a.status='PREPARED'").getResultList();
    }
}
