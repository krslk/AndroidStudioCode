package com.example.module_c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.module_c.utils.AddressInfo;

import java.util.ArrayList;

public class ContentMeans extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_means);
        Spinner spiProvince = findViewById(R.id.conMeans_spi_province);
//        AddressInfo addressInfo = new AddressInfo("DataOrigin.txt");
//        ArrayList<String> provinces = addressInfo.getProvinces();
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.activity_content_means, R.array.province);
//        spiProvince.setAdapter(arrayAdapter);
    }

    public void preStepReturnWE(View v) {
        finish();
    }

    public void nextStepFinish(View v) {
        Log.w("Activity_ContentMeans", "点击了完成");
    }
}








