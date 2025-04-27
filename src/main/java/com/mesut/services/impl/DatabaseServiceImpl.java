package com.mesut.services.impl;

import com.mesut.pojo.DatabaseUser;
import com.mesut.repositories.DatabaseRepository;
import com.mesut.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private DatabaseRepository dbRepo;

    @Override
    public DatabaseUser createDatabase(DatabaseUser db) {
        return this.dbRepo.createDatabase(db);
    }

    @Override
    public DatabaseUser getDatabaseById(int id) {
        return this.dbRepo.getDatabaseById(id);
    }

    @Override
    public List<DatabaseUser> getDatabaseByUsername(String username) {
        return this.dbRepo.getDatabaseByUsername(username);
    }

    @Override
    public void deleteDatabaseByUsernameAndName(String username, String name) {
        this.dbRepo.deleteDatabaseByUsernameAndName(username, name);
    }

    @Override
    public boolean isEmptyDatabase(String username, String name) {
        return this.dbRepo.isEmptyDatabase(username,name);
    }
}
