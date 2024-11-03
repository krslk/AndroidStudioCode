package com.example.qq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class Index extends AppCompatActivity {
    private TextView tv;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        tv = findViewById(R.id.tv);
        Bundle userInfo = getIntent().getExtras();
        assert userInfo != null;
        tv.setText("欢迎登录,用户：\n" + userInfo.getString("username"));
    }

}