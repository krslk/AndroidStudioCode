package com.example.module_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WorkExperience extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_experience);
    }

    public void preStepReturnBI(View v) {
        finish();
    }

    public void nextStepToCM(View v) {
        Intent intent = new Intent();
        intent.setClass(WorkExperience.this, ContentMeans.class);
        startActivity(intent);
    }
}