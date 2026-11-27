package br.com.mvc.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int LOG_ROUNDS = 12;

    private PasswordUtil() {
    }

    public static String hash(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia para hashing.");
        }
        return BCrypt.hashpw(plainText, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean verificar(String plainText, String hashed) {
        if (plainText == null || hashed == null) {
            return false;
        }

        if (isHashed(hashed)) {
            try {
                return BCrypt.checkpw(plainText, hashed);
            } catch (Exception e) {
                return false;
            }
        }

        return plainText.equals(hashed);
    }

    public static boolean isHashed(String senha) {
        if (senha == null) {
            return false;
        }
        return senha.startsWith("$2a$") || senha.startsWith("$2b$") || senha.startsWith("$2y$");
    }
}
