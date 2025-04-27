package com.mesut.repositories;

import com.mesut.pojo.User;

public interface UserRepository {
    User getUserByUsername(String username);
    User createUser(User u);
    boolean authenticate(String username, String password);
}
