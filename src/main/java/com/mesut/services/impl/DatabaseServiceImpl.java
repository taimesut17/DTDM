package com.mesut.services.impl;

import com.mesut.pojo.DatabaseUser;
import com.mesut.repositories.DatabaseRepository;
import com.mesut.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DatabaseRepository dbRepo;

    @Override
    public DatabaseUser createDatabase(String username) {
        DatabaseUser db = this.dbRepo.createDatabase(username);
        createDatabase_(db.getName());
        createUserForDatabase(db.getUsername_db(),db.getPassword_db(),db.getName());
        return db;
    }

    public void createDatabase_(String dbName) {
        String sql = String.format("CREATE DATABASE IF NOT EXISTS `%s` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;", dbName);
        jdbcTemplate.execute(sql);
    }

    public void createUserForDatabase(String username,String password, String databaseName) {
        String createUserSql = String.format(
                "CREATE USER '%s'@'%%' IDENTIFIED BY '%s';", username, password);
        jdbcTemplate.execute(createUserSql);

        String grantSql = String.format(
                "GRANT ALL PRIVILEGES ON %s.* TO '%s'@'%%';", databaseName, username);
        jdbcTemplate.execute(grantSql);

        jdbcTemplate.execute("FLUSH PRIVILEGES;");
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

        DatabaseUser db = getDatabaseByUsername(username).stream()
                .filter(dbUser -> name.equalsIgnoreCase(dbUser.getName()))
                .findFirst()
                .orElse(null);

        // Thu hồi tất cả quyền của user trước khi xóa
        assert db != null;
        String revokeSql = String.format("REVOKE ALL PRIVILEGES, GRANT OPTION FROM `%s`@'%%';", db.getUsername_db());
        jdbcTemplate.execute(revokeSql);

        // Xóa user (nếu tồn tại)
        String dropUserSql = String.format("DROP USER IF EXISTS `%s`@'%%';", username);
        jdbcTemplate.execute(dropUserSql);

        // Xóa database (nếu tồn tại)
        String dropDbSql = String.format("DROP DATABASE IF EXISTS `%s`;", name);
        jdbcTemplate.execute(dropDbSql);

        // Cập nhật lại quyền
        jdbcTemplate.execute("FLUSH PRIVILEGES;");
        this.dbRepo.deleteDatabaseByUsernameAndName(username, name);
    }

    @Override
    public boolean isEmptyDatabase(String username, String name) {
        return this.dbRepo.isEmptyDatabase(username,name);
    }
}
