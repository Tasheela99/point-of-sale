package com.my_shop.pos.util;

import org.mindrot.BCrypt;

public class PasswordManager {
    public static String encryptPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(10));
    }

    public static boolean decryptPassword(String plainText, String hash) {
        return BCrypt.checkpw(plainText, hash);
    }
}
