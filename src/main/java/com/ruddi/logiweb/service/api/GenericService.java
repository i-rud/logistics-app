package com.ruddi.logiweb.service.api;

import java.util.List;

public interface GenericService<T, DT> {
    void save(DT entity);
    void update(DT entity);
    void remove(DT entity);
    void remove(int id);
    DT find(int id);
    List<DT> getAll();
}
