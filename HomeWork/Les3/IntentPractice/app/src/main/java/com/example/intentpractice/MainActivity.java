package com.example.intentpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (findViewById(R.id.btn_openBrowser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        (findViewById(R.id.btn_callPho)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:16622372672");
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        (findViewById(R.id.btn_sendSMS)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("sms:Hello");
                Uri urito = Uri.parse("smsto:16622372672");
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(uri);
                intent.setData(urito);
                startActivity(intent);
            }
        });
    }
}