package com.example.dailynews.util;

import android.util.Patterns;

import com.example.dailynews.entity.Userinfo;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class UserUtil {
    public static boolean checkPassword(Userinfo ou, Userinfo eu) {
        if (ou != null && eu != null) {

            return ou.getAccount().equals(eu.getAccount()) && ou.getPassword().equals(eu.getPassword());
        }
        return false;
    }

    public static boolean isValidMail(String email) {
        if (!StringUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    public static String getEmailCode() {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            str.append(new Random().nextInt(10));
        }
        return str.toString();
    }

    public static boolean checkPassword(Userinfo u, String password) {
        if (u != null && !StringUtils.isEmpty(password)) {
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

    public static String getFormatTime() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        return format.format(now);
    }
}
