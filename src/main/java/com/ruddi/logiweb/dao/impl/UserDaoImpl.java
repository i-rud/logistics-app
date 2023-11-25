package com.ruddi.logiweb.dao.impl;

import com.ruddi.logiweb.dao.api.UserDao;
import com.ruddi.logiweb.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    @Override
    public User findUserByUsername(String username) {
        return (User) entityManager.createQuery(
                "select a from User a where a.username=?1").
                setParameter(1, username).setMaxResults(1).getSingleResult();
    }
}
