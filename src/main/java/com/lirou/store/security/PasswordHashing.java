package com.lirou.store.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashing {
    public static String hash (String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static Boolean checkHash (String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
