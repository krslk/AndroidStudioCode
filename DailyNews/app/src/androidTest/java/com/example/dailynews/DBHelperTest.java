package com.example.dailynews;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.dailynews.dbhelper.DBHelper;
import com.example.dailynews.entity.Userinfo;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DBHelperTest {
    @Test
    public void insertUserByUserInfo() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        DBHelper userDBHelper = DBHelper.getInstance(context);
        userDBHelper.openWriteLink();
        Userinfo userinfo = new Userinfo();
        userinfo.setAccount("111");
        userinfo.setMail("222");
        userinfo.setPassword("123");
        Long res = userDBHelper.insertUserinfo(userinfo);
        System.out.println("res=" + res);
    }
}
