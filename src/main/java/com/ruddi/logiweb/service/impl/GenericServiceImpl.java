package com.ruddi.logiweb.service.impl;

import com.ruddi.logiweb.dao.api.GenericDao;
import com.ruddi.logiweb.service.api.GenericService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * generic service class
 * @author Ivan Rud
 * @param <T> persistent class type
 * @param <DT> dto class type
 * @param <D> dao class type
 */
@Service
@AllArgsConstructor
@Slf4j
public class GenericServiceImpl<T, DT, D extends GenericDao<T>> implements GenericService<T, DT> {
    private Class<T> persistentClass;
    private Class<DT> persistentDtoClass;

    D dao;
    ModelMapper mapper;

    /**
     * saving entity
     * @param entity entity to save
     */
    @Override
    @Transactional
    public void save(DT entity) {
        dao.save(mapper.map(entity, persistentClass));
        log.info("save(entity) method was called");
    }

    /**
     * updating entity
     * @param entity entity to update
     */
    @Override
    @Transactional
    public void update(DT entity) {
        dao.update(mapper.map(entity, persistentClass));
        log.info("update(entity) method was called");
    }

    /**
     * removing entity by object
     * @param entity entity to remove
     */
    @Override
    @Transactional
    public void remove(DT entity) {
        dao.remove(mapper.map(entity, persistentClass));
        log.info("remove(entity) method was called");
    }

    /**
     * removing entity by id
     * @param id entity id to remove
     */
    @Override
    @Transactional
    public void remove(int id)  {
        dao.remove(id);
        log.info("remove(id) method was called");
    }

    /**
     * finding entity by id
     * @param id entity id to find
     * @return entity or null
     */
    @Override
    @Transactional(readOnly=true)
    public DT find(int id) {
        log.info("find(id) method was called");
        return mapper.map(dao.find(id), persistentDtoClass);
    }

    /**
     * getting all entity list
     * @return list of entities
     */
    @Override
    @Transactional(readOnly=true)
    public List<DT> getAll() {
        log.info("getAll() method was called");
        return dao.getAll().stream().map(entity ->
                mapper.map(entity, persistentDtoClass)).collect(Collectors.toList());
    }
}
