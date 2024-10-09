package com.example.loginpra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_resetpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
    }

    public void back(View v) {
        Intent intent = new Intent();
        intent.setClass(activity_resetpassword.this, MainActivity.class);
        startActivity(intent);
    }
}