package com.example.datatransmission;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText age;
    private EditText says;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.et_name);
        age = findViewById(R.id.et_age);
        says = findViewById(R.id.et_says);
        ActivityResultLauncher<Intent> register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "收到！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        (findViewById(R.id.btn_go)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                Bundle bundle = new Bundle();
                String name = MainActivity.this.name.getText().toString();
                String age = MainActivity.this.age.getText().toString();
                String says = MainActivity.this.says.getText().toString();
                Log.w("test", says);
                if (name.isEmpty() || age.isEmpty() || says.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "请输入完整的信息", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
                    toast.show();
                } else if (Integer.parseInt(age) >= 120) {
                    Toast toast = Toast.makeText(getApplicationContext(), "你的年龄太大了，适宜去养老院凉快", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
                    toast.show();
                } else {
                    bundle.putString("name", name);
                    bundle.putString("age", age);
                    bundle.putString("says", says);
                    intent.putExtras(bundle);
                    register.launch(intent);
                }
            }
        });
    }
}