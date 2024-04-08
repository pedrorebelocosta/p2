package org.nolhtaced.core.utilities;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;

public class PasswordUtil {
    private static final Integer SALT_ROUNDS = 12;
    private static final SecureRandom secureRandom = new SecureRandom();

    private static String generateSalt() {
        return BCrypt.gensalt(SALT_ROUNDS, secureRandom);
    }

    public static String generateHash(String password) {
        return BCrypt.hashpw(password, generateSalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}