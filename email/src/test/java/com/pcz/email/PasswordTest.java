package com.pcz.email;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordTest extends SpringBootEmailApplicationTest {
    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void testGeneratePassword() {
        String password = "test";
        String encryptPassword = stringEncryptor.encrypt(password);
        String decryptPassword = stringEncryptor.decrypt(encryptPassword);

        System.out.println("password = " + password);
        System.out.println("encryptPassword = " + encryptPassword);
        System.out.println("decryptPassword = " + decryptPassword);
    }
}
