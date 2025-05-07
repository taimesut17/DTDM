package com.mesut.utils;
import java.util.UUID;
import java.security.SecureRandom;

public class GenerateUtils {

    public static String generateRandomUsername() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }
    public static String generateRandomDatabaseName() {
        return "db_" + UUID.randomUUID().toString().substring(0, 8);
    }
    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
