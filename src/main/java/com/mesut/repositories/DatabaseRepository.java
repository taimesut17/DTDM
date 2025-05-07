package com.mesut.repositories;

import com.mesut.pojo.DatabaseUser;

import java.util.List;

public interface DatabaseRepository {
    DatabaseUser createDatabase(String username);
    DatabaseUser getDatabaseById(int id);
    List<DatabaseUser> getDatabaseByUsername(String name);
    void deleteDatabaseByUsernameAndName(String username, String name);
    boolean isEmptyDatabase(String username, String name);
}
