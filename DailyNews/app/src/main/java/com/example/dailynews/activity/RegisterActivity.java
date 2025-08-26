package com.example.dailynews.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dailynews.Api.UserApi;
import com.example.dailynews.dbhelper.DBHelper;
import com.example.dailynews.entity.Userinfo;
import com.example.dailynews.util.StringUtils;
import com.example.dailynews.R;
import com.example.dailynews.util.UserUtil;

import java.util.HashMap;
import java.util.UUID;

public class RegisterActivity extends BaseActivity {
    private EditText et_account, et_password, et_rePassword, et_email, et_mailCode;
    private Button btn_register;
    private DBHelper userDBHelper;
    private HashMap<String, String> hm = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDBHelper = DBHelper.getInstance(mContext);
        userDBHelper.openReadLink();
        userDBHelper.openWriteLink();
        initView();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                String rePassword = et_rePassword.getText().toString();
                if (StringUtils.isEmpty(account)) {
                    showToast("账号不能为空");
                    return;
                }
                if (StringUtils.isEmpty(password)) {
                    showToast("密码不能为空");
                    return;
                }
                if (StringUtils.isEmpty(password)) {
                    showToast("密码不能为空");
                    return;
                }
                if (StringUtils.isEmpty(rePassword)) {
                    showToast("必须再次输入以确认密码");
                    return;
                }
                if (!password.equals(rePassword)) {
                    showToast("密码不一致");
                    return;
                }
                register(new Userinfo(account, password, et_email.getText().toString()));
            }
        });
        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userDBHelper.selectCountByAccount(s.toString())) {
                    showToast("用户名已经被注册");
                    et_account.setTextColor(Color.RED);
                } else {
                    et_account.setTextColor(Color.BLACK);
                }
            }
        });
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userDBHelper.selectCountByMail(s.toString())) {
                    showToast("该邮箱已经被注册");
                    et_email.setTextColor(Color.RED);
                } else {
                    et_email.setTextColor(Color.BLACK);
                }
            }
        });
    }

    private void register(Userinfo u) {
        String code = et_mailCode.getText().toString();
        if (StringUtils.isEmpty(code)) {
            showToast("验证码不能为空");
            return;
        }
        if (hm.get("email").equals(et_email.getText().toString()) && hm.get("code").equals(code)) {
            String salt = UUID.randomUUID().toString().toUpperCase();
            u.setUsername("User" + u.getAccount());
            u.setSalt(salt);
            u.setPassword(UserUtil.getMd5ByPasswordAndSalt(u.getPassword(), salt));
            u.setCreateTime(UserUtil.getFormatTime());
            u.setModifyTime(UserUtil.getFormatTime());
            long res = userDBHelper.insertUserinfo(u);
            if (res > 0) {
                showToast("注册成功");
            }
            finish();
        } else {
            showToast("验证码错误");
        }
    }

    public void getMailCode(View view) {
        String email = et_email.getText().toString();
        if (StringUtils.isEmpty(email)) {
            showToast("请输入合法的邮箱");
            return;
        }
        hm.put("email", email);
        hm.put("code", UserUtil.getEmailCode());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!UserApi.sendEmailCode(hm.get("email"), hm.get("code"))) {
                    showToast("发送失败");
                    hm.remove("email");
                    hm.remove("code");
                    return;
                } else {
                    showToast("发送成功");
                }
            }
        }).start();
    }

    private void initView() {
        et_account = findViewById(R.id.et_register_account);
        et_password = findViewById(R.id.et_register_password);
        et_rePassword = findViewById(R.id.et_register_repassword);
        et_email = findViewById(R.id.et_register_mail);
        et_mailCode = findViewById(R.id.et_register_mailCode);
        btn_register = findViewById(R.id.btn_login_toRegister);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDBHelper.closeAllLink();
    }
}