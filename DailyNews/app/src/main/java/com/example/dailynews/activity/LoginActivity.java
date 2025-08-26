package com.example.dailynews.activity;

import com.example.dailynews.dbhelper.DBHelper;
import com.example.dailynews.entity.Userinfo;
import com.example.dailynews.util.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.dailynews.R;
import com.example.dailynews.util.UserUtil;

public class LoginActivity extends BaseActivity {
    DBHelper dBHelper = null;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dBHelper = DBHelper.getInstance(this);
        dBHelper.openReadLink();
        initView();
        if (!StringUtils.isEmpty(selectStringFromSp("account")) && !StringUtils.isEmpty(selectStringFromSp("passwd"))) {
            et_username.setText(selectStringFromSp("account"));
            et_password.setText(selectStringFromSp("passwd"));
            cb.setChecked(true);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if (StringUtils.isEmpty(username)) {
                    showToast("账号不能为空");
                    return;
                }
                if (StringUtils.isEmpty(password)) {
                    showToast("账号不能为空");
                    return;
                }
                login(username, password);
            }
        });
    }

    private void login(String account, String password) {
        Userinfo userinfo = dBHelper.selectUserByAccount(account);
        if (userinfo == null || StringUtils.isEmpty(userinfo.getAccount())) {
            showToast("账号不存在");
            return;
        }
        if (UserUtil.checkPassword(userinfo, password)) {
            if (cb.isChecked()) {
                saveStringToSp("account", account);
                saveStringToSp("passwd", password);
            } else {
                saveStringToSp("account", "");
                saveStringToSp("passwd", "");
            }
            Intent intent = new Intent(this, IndexActivity.class);
            Bundle bundle = new Bundle();
            saveStringToSp("current", userinfo.getAccount());
            bundle.putParcelable("userinfo", userinfo);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            showToast("账号或密码错误");
        }
    }

    private void initView() {
        et_username = findViewById(R.id.et_login_username);
        et_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.btn_login_toLogin);
        cb = findViewById(R.id.cb_savePasswd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dBHelper.closeAllLink();
    }
}