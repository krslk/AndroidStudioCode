package com.example.datatransmission;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private TextView tv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv = findViewById(R.id.tv_welcome);
        Bundle bundle = getIntent().getExtras();
        String str = String.format("你好，%s：\n欢迎你来这个世界的第%s年，你说：%s", bundle.get("name"), bundle.getString("age"), bundle.getString("says"));
        tv.setText(str);
        setResult(Activity.RESULT_OK, new Intent().putExtra("req", "收到"));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}