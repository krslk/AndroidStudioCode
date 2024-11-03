package com.example.qq.utils;

import android.util.Log;

import com.example.qq.entity.Account;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class SaveAccount {
    public static void saveAccountToFile(FileOutputStream fileOutputStream, Account account) throws IOException {
        fileOutputStream.write((account.getUsername() + ":" + account.getPassword()).getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static Account findAccountFromFile(FileInputStream fileInputStream) throws IOException {
        byte[] buffer = new byte[fileInputStream.available()];
        int res = fileInputStream.read(buffer);
        if (res != -1) {
            String[] str = new String(buffer).split(":");
            if (str.length == 2) {
                Log.w("SaveAccount", str[0]);
                Log.w("SaveAccount", str[1]);
                return new Account(str[0], str[1]);
            }
        }
        return null;
    }
}
