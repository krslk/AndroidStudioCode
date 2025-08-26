package com.example.musicplayer.utils;

import com.example.musicplayer.entity.User;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils;

public class StringUtil {
    public static boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean checkPassword(User u, String password) {
        if (u != null && !StringUtil.isEmpty(password)) {
            String s = getMd5ByPasswordAndSalt(password, u.getSalt());
            return s.equals(u.getPassword());
        }
        return false;
    }

    public static String getMd5ByPasswordAndSalt(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            String str = (salt + password + salt).toUpperCase();
            password = DigestUtils.md5Hex(str).toUpperCase();
        }
        return password.toUpperCase();
    }
}
