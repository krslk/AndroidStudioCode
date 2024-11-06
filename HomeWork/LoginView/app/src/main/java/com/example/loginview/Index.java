package com.example.loginview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Index extends AppCompatActivity {
    private TextView tv_userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String username = bundle.getString("username");
            String password = bundle.getString("passwd");
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "数据丢失了~", Toast.LENGTH_SHORT).show();
            } else {
                String str = "用户名：" + username + "\n密码：" + password;
                tv_userInfo.setText(str);
            }
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatData = simpleDateFormat.format(date);
        Intent intent = new Intent();
        intent.putExtra("loginTime", formatData);
        setResult(Activity.RESULT_OK, intent);
    }

    private void initView() {
        tv_userInfo = findViewById(R.id.tv_showInfo);
    }
}