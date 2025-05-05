package com.mesut.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MySqlUserServiceImpl {
    private static String HOST = "localhost";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createRestrictedUser(String username, String password) {
        String fullUser = "'" + username + "'@'" + HOST + "'";

        // 1. Tạo user
        String createUserSQL = "CREATE USER " + fullUser + " IDENTIFIED BY '" + password + "'";
        jdbcTemplate.execute(createUserSQL);

        // 2. Thu hồi toàn bộ quyền (trong trường hợp user đã có quyền từ trước)
        String revokeSQL = "REVOKE ALL PRIVILEGES, GRANT OPTION FROM " + fullUser;
        jdbcTemplate.execute(revokeSQL);

        // 3. Làm mới quyền (không bắt buộc nếu dùng JDBC chuẩn)
        String flushSQL = "FLUSH PRIVILEGES";
        jdbcTemplate.execute(flushSQL);
    }
}
