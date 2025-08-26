package com.example.dailynews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.dailynews.R;
import com.example.dailynews.dbhelper.DBHelper;
import com.example.dailynews.entity.Userinfo;
import com.example.dailynews.util.StringUtils;
import com.example.dailynews.util.UserUtil;

public class AlterPasswdActivity extends BaseActivity {
    private EditText et_oldPasswd, et_newPasswd, et_reNewPasswd;
    private DBHelper userDBHelper;
    private Userinfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_passwd);
        userinfo = getIntent().getParcelableExtra("userinfo");
        et_oldPasswd = findViewById(R.id.et_setting_oldPassword);
        et_newPasswd = findViewById(R.id.et_setting_newPassword);
        et_reNewPasswd = findViewById(R.id.et_setting_reNewPassword);
        userDBHelper = DBHelper.getInstance(this);
        userDBHelper.openWriteLink();
        userDBHelper.openWriteLink();
    }

    public void back(View view) {
        finish();
    }

    public void alterPasswd(View view) {
        String oldPasswd = et_oldPasswd.getText().toString();
        String newPasswd = et_newPasswd.getText().toString();
        if (StringUtils.isEmpty(oldPasswd) || StringUtils.isEmpty(newPasswd) || StringUtils.isEmpty(et_reNewPasswd.getText().toString())) {
            showToast("密码不能为空");
            return;
        }
        if (!newPasswd.equals(et_reNewPasswd.getText().toString())) {
            showToast("输入的新密码不一致");
            return;
        }
        if (!UserUtil.getMd5ByPasswordAndSalt(oldPasswd, userinfo.getSalt()).equals(userinfo.getPassword())) {
            showToast("原密码错误");
            return;
        }
        updataPasswd(newPasswd);
    }

    private void updataPasswd(String newPasswd) {
        String passwd = UserUtil.getMd5ByPasswordAndSalt(newPasswd, userinfo.getSalt());
        if (passwd.equals(userinfo.getPassword())) {
            showToast("新密码与原密码相同");
            return;
        }
        userinfo.setPassword(passwd);
        userinfo.setModifyTime(UserUtil.getFormatTime());
        if (userDBHelper.updataInfoByUserinfo(userinfo) > 0) {
            showToast("更新成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDBHelper.closeAllLink();
    }
}