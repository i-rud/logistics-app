package com.ruddi.logiweb.dao.api;

import java.util.List;

public interface GenericDao<T> {
    void save(T entity);
    void update(T entity);
    void remove(T entity);
    void remove(int id);
    T find(int id);
    List<T> getAll();
}
