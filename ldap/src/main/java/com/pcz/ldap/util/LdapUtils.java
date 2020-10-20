package com.pcz.ldap.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author picongzhi
 */
public class LdapUtils {
    /**
     * 校验密码
     *
     * @param ldapPassword  ldap加密密码
     * @param inputPassword 用户输入密码
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static boolean verify(String ldapPassword, String inputPassword) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        if (ldapPassword.startsWith("{SSHA}")) {
            ldapPassword = ldapPassword.substring(6);
        } else if (ldapPassword.startsWith("{SHA}")) {
            ldapPassword = ldapPassword.substring(5);
        }

        byte[] ldapPasswordByte = Base64.decode(ldapPassword);
        byte[] shaCode;
        byte[] salt;

        if (ldapPasswordByte.length <= 20) {
            shaCode = ldapPasswordByte;
            salt = new byte[0];
        } else {
            shaCode = new byte[20];
            salt = new byte[ldapPasswordByte.length - 20];
            System.arraycopy(ldapPasswordByte, 0, shaCode, 0, 20);
            System.arraycopy(ldapPasswordByte, 20, salt, 0, salt.length);
        }

        messageDigest.update(inputPassword.getBytes());
        messageDigest.update(salt);
        byte[] inputPasswordByte = messageDigest.digest();

        return MessageDigest.isEqual(shaCode, inputPasswordByte);
    }

    /**
     * Ascii转字符串
     *
     * @param value Ascii
     * @return 字符串
     */
    public static String asciiToString(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] chars = value.split(",");
        for (String aChar : chars) {
            stringBuilder.append((char) Integer.parseInt(aChar));
        }

        return stringBuilder.toString();
    }
}
