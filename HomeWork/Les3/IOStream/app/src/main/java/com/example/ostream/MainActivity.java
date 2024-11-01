package com.example.ostream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private Button login;
    private String username;
    private String password;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String s = findQQFromSP();
        if (!s.isEmpty() && s.length() >= 2) {
            String[] str = s.split(",");
            etUsername.setText(str[0]);
            etPassword.setText(str[1]);
        }
    }

    private void initView() {
        sp = getSharedPreferences("data", MODE_PRIVATE);
        editor = sp.edit();
        etUsername = findViewById(R.id.btn_username);
        etPassword = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "账号不为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不为空", Toast.LENGTH_SHORT).show();
            return;
        }
        saveQQFromSP(username, password);
    }

    private String findQQByFile() {
        try {
            FileInputStream fileInputStream = openFileInput("data.txt");
            byte[] b = new byte[fileInputStream.available()];
            fileInputStream.read(b);
            String str = new String(b);
            Log.w("test", str);
            fileInputStream.close();
            return str;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveQQFromFile(String username, String password) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("data.txt", Context.MODE_PRIVATE);
            String str = username + "," + password;
            fileOutputStream.write(str.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveQQFromSP(String username, String password) {
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    private String findQQFromSP() {
        return sp.getString("username", "") + "," + sp.getString("password", "");
    }

}