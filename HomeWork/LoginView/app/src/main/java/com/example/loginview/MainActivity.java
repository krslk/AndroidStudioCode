package com.example.loginview;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_username;
    private EditText et_passwd;
    private Button btn_login;
    private CheckBox cb_savePassword;
    private ActivityResultLauncher<Intent> register;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //只能注册一次，并且在OnCreate注册
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        String time = result.getData().getStringExtra("loginTime");
                        if (time != null) {
                            Toast.makeText(MainActivity.this, time, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] str = new String[0];
        try {
            str = findAccountInfoByFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (str != null && str.length == 2) {
            et_username.setText(str[0]);
            et_passwd.setText(str[1]);
            cb_savePassword.setChecked(true);
        }
    }

    @Override
    protected void onStop() {
        if (cb_savePassword.isChecked()) {
            try {
                saveAccountInfoByFile(username, password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                clearAccountInfoByFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        super.onStop();
    }

    private void initView() {
        et_username = findViewById(R.id.et_username);
        et_passwd = findViewById(R.id.et_password);
        cb_savePassword = findViewById(R.id.cb_save_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);//设置按键监听
    }

    @Override
    public void onClick(View v) {
        username = et_username.getText().toString();
        password = et_passwd.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            if (username.isEmpty()) {
                Toast.makeText(this, "用户名不为空", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "密码不为空", Toast.LENGTH_SHORT).show();
            }
        } else {
            String myUsername = "123456";
            String myPasswd = "123456";
            if (myUsername.equals(username) && myPasswd.equals(password)) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                //账号密码正确，登录
                Intent intent = new Intent(this, Index.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("passwd", password);
                intent.putExtras(bundle);
                register.launch(intent);
            } else {
                // 登录失败就提示用户名或密码错误
                Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearAccountInfoByFile() throws IOException {
        FileOutputStream fileOutputStream = openFileOutput("passwd.txt", Context.MODE_PRIVATE);
        fileOutputStream.write("".getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private void saveAccountInfoByFile(String username, String password) throws IOException {
        FileOutputStream fileOutputStream = openFileOutput("passwd.txt", Context.MODE_PRIVATE);
        String str = username + ":" + password;
        fileOutputStream.write(str.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private String[] findAccountInfoByFile() throws IOException {
        FileInputStream fileInputStream = openFileInput("passwd.txt");
        byte[] buffer = new byte[fileInputStream.available()];
        int res = fileInputStream.read(buffer);
        if (res == -1) {
            return null;
        }
        String str = new String(buffer);
        return str.split(":");
    }
}