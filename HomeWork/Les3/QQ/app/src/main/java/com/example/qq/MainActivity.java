package com.example.qq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowInsetsAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qq.entity.Account;
import com.example.qq.utils.SaveAccount;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean hidePassword = true;
    private EditText et_username;
    private EditText et_password;
    private Button login;
    private CheckBox cb_saveAccount;
    private Button btn_showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        login.setOnClickListener(this);
        btn_showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hidePassword) {
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                hidePassword = !hidePassword;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Account account = SaveAccount.findAccountFromFile(openFileInput("passwd.txt"));
            if (account != null) {
                et_username.setText(account.getUsername());
                et_password.setText(account.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (saveAccountCBIsChecked()) {
            try {
                SaveAccount.saveAccountToFile(openFileOutput("passwd.txt", Context.MODE_PRIVATE), new Account(et_username.getText().toString(), et_password.getText().toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initView() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        cb_saveAccount = findViewById(R.id.cb_save_password);
        btn_showPassword = findViewById(R.id.btn_show_password);
        login = findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {
        if (!et_username.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty()) {
            if (checkAccount(et_username.getText().toString(), et_password.getText().toString())) {
                Intent intent = new Intent(this, Index.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", et_username.getText().toString());
                bundle.putString("password", et_password.getText().toString());
                intent.putExtras(bundle);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "用户名或密码不为空", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkAccount(String username, String password) {
        return true;
    }

    private boolean saveAccountCBIsChecked() {
        return cb_saveAccount.isChecked();
    }
}