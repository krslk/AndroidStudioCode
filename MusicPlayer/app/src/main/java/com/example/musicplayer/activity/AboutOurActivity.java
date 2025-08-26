package com.example.musicplayer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicplayer.R;

public class AboutOurActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_contact_us, ll_website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_our);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ll_website = findViewById(R.id.ll_about_us_website);
        ll_website.setOnClickListener(this);
        ll_contact_us = findViewById(R.id.ll_about_us_contact_phone);
        ll_contact_us.setOnClickListener(this);
    }

    public void toBack(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_about_us_website) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tjnu.edu.cn"));
                startActivity(intent);
        } else if (v.getId() == R.id.ll_about_us_contact_phone) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "16622372672"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
