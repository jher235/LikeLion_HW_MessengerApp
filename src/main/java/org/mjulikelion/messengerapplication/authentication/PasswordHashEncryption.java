package org.mjulikelion.messengerapplication.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class PasswordHashEncryption {
    private static final String PBKDF2_WITH_SHA1 = "PBKDF2WithHmacSHA1";
    private final String salt;
    private final int iterationCount;
    private final int keyLength;

    public PasswordHashEncryption(@Value("${encryption.pbkdf2.salt}") String salt,
                                  @Value("${encryption.pbkdf2.iteration-count}") int iterationCount,
                                  @Value("${encryption.pbkdf2.key-length}") int keyLength) {
        this.salt = salt;
        this.iterationCount = iterationCount;
        this.keyLength = keyLength;
    }

    public String encrypt(final String plainPassword){
        try {
            KeySpec keySpec = new PBEKeySpec(plainPassword.toCharArray(), salt.getBytes(), iterationCount, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2_WITH_SHA1);
            byte[] encodedPassword = keyFactory
                    .generateSecret(keySpec)
                    .getEncoded();
            return Base64.getEncoder()
                            .withoutPadding()
                            .encodeToString(encodedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("화원가입에 실패했습니다. : "+e);
        }
    }

    public boolean matches(final String plainPassword, final String hashedPassword){
        return encrypt(plainPassword).equals(hashedPassword);
    }
}
