package com.ruddi.logiweb.service.api;

import com.ruddi.logiweb.dto.UserDto;
import com.ruddi.logiweb.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends GenericService<User, UserDto>, UserDetailsService {
    UserDto findByUsername(String username);
}
