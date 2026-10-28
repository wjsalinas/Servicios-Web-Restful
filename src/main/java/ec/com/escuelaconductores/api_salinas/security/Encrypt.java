package ec.com.escuelaconductores.api_salinas.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Encrypt {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }


    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));

            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al inicializar SHA-256", e);
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedHash, String storedSalt) {
        String newHash = hashPassword(inputPassword, storedSalt);
        return newHash.equals(storedHash);
    }

}
