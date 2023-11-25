package com.ruddi.logiweb.dao.api;

import com.ruddi.logiweb.model.Cargo;

import java.util.List;

public interface CargoDao extends GenericDao<Cargo>{
    List<Cargo> getPrepared();
}
