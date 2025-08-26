package com.example.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.dbHelper.MusicPlayerDBHelper;
import com.example.musicplayer.entity.User;
import com.example.musicplayer.utils.StringUtil;

public class LoginActivity extends BaseActivity {
    private EditText et_account, et_passwd;
    private TextView tv_toRegister;
    CheckBox checkBox;
    private MusicPlayerDBHelper musicPlayerDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        musicPlayerDBHelper = MusicPlayerDBHelper.getInstance(this);
        musicPlayerDBHelper.openReadLink();
        if (findFromSP("save_passwd").equals("true")) {
            checkBox.setChecked(true);
            String account = findFromSP("account");
            String passwd = findFromSP("passwd");
            if (!StringUtil.isEmpty(account)) {
                et_account.setText(account);
            }
            if (!StringUtil.isEmpty(passwd)) {
                et_passwd.setText(passwd);
            }
        }
    }

    private void initView() {
        et_account = findViewById(R.id.et_login_account);
        et_passwd = findViewById(R.id.et_login_passwd);
        checkBox = findViewById(R.id.cb_login_rememberPasswd);
        tv_toRegister = findViewById(R.id.tv_login_registerAccount);
        tv_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void login(View view) {
        String account = et_account.getText().toString();
        String passwd = et_passwd.getText().toString();
        if (StringUtil.isEmpty(account)) {
            showShortToast("账号不能为空");
            return;
        }
        if (StringUtil.isEmpty(passwd)) {
            showShortToast("密码不能为空");
            return;
        }
        if (checkAuthority(account, passwd)) {
            if (checkBox.isChecked()) {
                saveToSp("save_passwd", "true");
            } else {
                saveToSp("save_passwd", "false");
            }
            saveToSp("account", account);
            saveToSp("passwd", passwd);
            saveToSp("auto_login", "true");
            Intent intent = new Intent(this, IndexActivity.class);
            this.finish();
            startActivity(intent);
        } else {
            showShortToast("用户名或密码错误");
        }
    }

    private boolean checkAuthority(String account, String passwd) {
        User user = musicPlayerDBHelper.selectUserByAccount(account);
        if (user.getAccount() != null && StringUtil.getMd5ByPasswordAndSalt(passwd, user.getSalt()).equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}