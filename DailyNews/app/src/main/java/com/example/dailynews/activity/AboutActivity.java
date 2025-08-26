package com.example.dailynews.activity;

import android.os.Bundle;
import android.view.View;
import com.example.dailynews.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void back(View view) {
        finish();
    }
}
