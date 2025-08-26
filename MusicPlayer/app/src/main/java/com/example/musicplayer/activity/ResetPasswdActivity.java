package com.example.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.dbHelper.MusicPlayerDBHelper;
import com.example.musicplayer.entity.User;
import com.example.musicplayer.utils.StringUtil;

public class ResetPasswdActivity extends BaseActivity {
    private EditText et_oldPasswd, et_newPasswd, et_reNewPasswd;
    private MusicPlayerDBHelper musicPlayerDBHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passwd);
        et_oldPasswd = findViewById(R.id.activity_reset_passwd_old);
        et_newPasswd = findViewById(R.id.activity_reset_passwd_new);
        et_reNewPasswd = findViewById(R.id.activity_reset_passwd_re_new);
    }

    @Override
    protected void onStart() {
        super.onStart();
        musicPlayerDBHelper = MusicPlayerDBHelper.getInstance(this);
        musicPlayerDBHelper.openWriteLink();
        musicPlayerDBHelper.openReadLink();
        user = musicPlayerDBHelper.selectUserByAccount(findFromSP("account"));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void toBack(View view) {
        finish();
    }

    public void updatePasswd(View view) {
        String oldPasswd = et_oldPasswd.getText().toString();
        String newPasswd = et_newPasswd.getText().toString();
        String reNewPasswd = et_reNewPasswd.getText().toString();
        if (StringUtil.isEmpty(oldPasswd) || StringUtil.isEmpty(newPasswd) || StringUtil.isEmpty(reNewPasswd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (oldPasswd.length() < 6 || newPasswd.length() < 6 || reNewPasswd.length() < 6) {
            Toast.makeText(this, "密码长度不能小于6位~", Toast.LENGTH_SHORT).show();
            return;
        } else if (!newPasswd.equals(reNewPasswd)) {
            Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
            return;
        } else if (oldPasswd.equals(newPasswd)) {
            Toast.makeText(this, "原密码与新密码不能相同", Toast.LENGTH_SHORT).show();
            return;
        } else if (!StringUtil.checkPassword(user, oldPasswd)) {
            Toast.makeText(this, "旧密码不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        user.setPassword(StringUtil.getMd5ByPasswordAndSalt(newPasswd, user.getSalt()));
        musicPlayerDBHelper.updateUserByUser(user);
        Toast.makeText(this, "修改成功,请重新登录", Toast.LENGTH_SHORT).show();
        saveToSp("passwd", "");
        saveToSp("auto_login", "false");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}