package com.example.wechat;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wechat.entity.Account;
import com.example.wechat.utils.AccountUtil;

import java.io.File;
import java.io.IOException;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private boolean hidePassword = true;
    private EditText et_username;
    private EditText et_password;
    private Button login;
    private CheckBox cb_saveAccount;
    private ImageButton btn_showPassword;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        login.setOnClickListener(this);
        btn_showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hidePassword) {
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btn_showPassword.setSelected(true);
                } else {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btn_showPassword.setSelected(false);
                }
                hidePassword = !hidePassword;
            }
        });
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Bundle bundle = result.getData().getExtras();
                        if (bundle != null) {
                            Toast.makeText(Login.this, bundle.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        btn_showPassword.setSelected(false);
        super.onStart();
        try {
            Account account = AccountUtil.findAccountFromFile(openFileInput("passwd.txt"));
            if (account != null) {
                et_username.setText(account.getUsername());
                et_password.setText(account.getPassword());
                cb_saveAccount.setChecked(true);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cb_saveAccount.setChecked(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (saveAccountCBIsChecked()) {
            try {
                AccountUtil.saveAccountToFile(openFileOutput("passwd.txt", Context.MODE_PRIVATE), new Account(et_username.getText().toString(), et_password.getText().toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                if (AccountUtil.findAccountFromFile(openFileInput("passwd.txt")) != null) {
                    AccountUtil.deleteAccountFromFile(openFileOutput("passwd.txt", Context.MODE_PRIVATE));
                }
            } catch (IOException e) {
                e.printStackTrace();
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
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("userinfo", new Account(et_username.getText().toString(), et_password.getText().toString()));
                intent.putExtras(bundle);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                register.launch(intent);
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