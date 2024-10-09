package com.example.module_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BasicInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information);
    }

    public void nextStepToWE(View v) {
        Intent intent = new Intent();
        intent.setClass(BasicInformation.this, WorkExperience.class);
        startActivity(intent);
    }
}