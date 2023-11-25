package com.ruddi.logiweb.dao.api;

import com.ruddi.logiweb.model.User;

public interface UserDao extends GenericDao<User> {
    User findUserByUsername(String username);
}
