package com.example.loginpra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_verifyIdentity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyidentity);
        Button button=findViewById(R.id.verifyIdentity_btn_verify);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(activity_verifyIdentity.this,activity_resetpassword.class);
                startActivity(intent);
            }
        });
    }
}