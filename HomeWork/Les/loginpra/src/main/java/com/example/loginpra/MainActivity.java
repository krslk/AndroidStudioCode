package com.example.loginpra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toRegister(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, activity_register.class);
        startActivity(intent);
    }

    public void toVerifyIdentity(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, activity_verifyIdentity.class);
        startActivity(intent);
    }
}