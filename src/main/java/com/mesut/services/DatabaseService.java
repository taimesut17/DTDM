package com.mesut.services;

import com.mesut.pojo.DatabaseUser;

import java.util.List;

public interface DatabaseService {
    DatabaseUser createDatabase(DatabaseUser db);
    DatabaseUser getDatabaseById(int id);
    List<DatabaseUser> getDatabaseByUsername(String username);
    void deleteDatabaseByUsernameAndName(String username, String name);
    boolean isEmptyDatabase(String username, String name);

}
