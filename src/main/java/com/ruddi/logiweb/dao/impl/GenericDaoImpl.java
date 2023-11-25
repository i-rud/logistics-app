package com.ruddi.logiweb.dao.impl;

import com.ruddi.logiweb.dao.api.GenericDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@Repository
public class GenericDaoImpl<T> implements GenericDao<T> {
    private Class<T> persistentClass;

    @PersistenceContext
    EntityManager entityManager;

    public void init(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entityManager.contains(entity) ?
                entity : entityManager.merge(entity));
    }

    @Override
    public void remove(int id) {
        T entity = find(id);
        remove(entity);
    }

    @Override
    public T find(int id) {
        return entityManager.find(persistentClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return entityManager.createQuery(
                "select a from " + persistentClass.getName() + " a").getResultList();
    }
}
