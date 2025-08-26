package com.example.musicplayer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.musicplayer.R;
import com.example.musicplayer.dbHelper.MusicPlayerDBHelper;
import com.example.musicplayer.entity.User;
import com.example.musicplayer.utils.DataUtil;
import com.example.musicplayer.utils.StringUtil;

import java.util.UUID;

public class RegisterActivity extends BaseActivity {
    private EditText et_account, et_passwd, et_rePasswd;
    private MusicPlayerDBHelper musicPlayerDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        musicPlayerDBHelper = MusicPlayerDBHelper.getInstance(this);
        musicPlayerDBHelper.openReadLink();
        musicPlayerDBHelper.openWriteLink();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        et_account = findViewById(R.id.et_register_account);
        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    User user = musicPlayerDBHelper.selectUserByAccount(s.toString());
                    if (user.getAccount() != null) {
                        showShortToast("用户名已经被注册");
                        et_account.setTextColor(Color.RED);
                    } else {
                        et_account.setTextColor(Color.BLACK);
                    }
                }
            }
        });
        et_passwd = findViewById(R.id.et_register_passwd);
        et_rePasswd = findViewById(R.id.et_register_rePasswd);
    }

    public void register(View view) {
        String account = et_account.getText().toString();
        String passwd = et_passwd.getText().toString();
        String rePasswd = et_rePasswd.getText().toString();
        if (StringUtil.isEmpty(account) || account.length() < 6) {
            showShortToast("账号长度为6~15个字符");
            return;
        }
        if (StringUtil.isEmpty(passwd) || passwd.length() < 6) {
            showShortToast("密码长度介于6~20个字符之间");
            return;
        }
        if (StringUtil.isEmpty(rePasswd) || !passwd.equals(rePasswd)) {
            showShortToast("两次输入的密码不一致");
            return;
        }
        if (musicPlayerDBHelper.selectUserByAccount(account).getAccount() != null) {
            showShortToast("用户名已经被注册");
            return;
        }
        String salt = String.valueOf(UUID.randomUUID());
        musicPlayerDBHelper.insertUserByUser(new User(account, StringUtil.getMd5ByPasswordAndSalt(passwd, salt), salt, DataUtil.getFormatTime()));
        saveToSp("account", account);
        saveToSp("passwd", passwd);
        finish();
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