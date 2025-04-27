package com.mesut.services;

import com.mesut.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
    User createUser(User u);
    boolean authenticate(String username, String password);
}
