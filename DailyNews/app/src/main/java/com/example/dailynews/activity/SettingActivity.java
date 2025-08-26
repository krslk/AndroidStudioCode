package com.example.dailynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.dailynews.R;
import com.example.dailynews.entity.Userinfo;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_alterOwnInfo, ll_alterPasswd, ll_exitLogin;
    private Userinfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        userinfo = getIntent().getParcelableExtra("userinfo");
        initView();
    }

    private void initView() {
        ll_alterOwnInfo = findViewById(R.id.fragment_mine_ll_ownInfo);
        ll_alterOwnInfo.setOnClickListener(this);
        ll_alterPasswd = findViewById(R.id.fragment_mine_ll_alterPasswd);
        ll_alterPasswd.setOnClickListener(this);
        ll_exitLogin = findViewById(R.id.fragment_mine_ll_exitLogin);
        ll_exitLogin.setOnClickListener(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        Bundle extras = new Bundle();
        extras.putParcelable("userinfo", userinfo);
        if (v.getId() == R.id.fragment_mine_ll_ownInfo) {
            Intent intent = new Intent(this, AlterOwnInfoActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (v.getId() == R.id.fragment_mine_ll_alterPasswd) {
            Intent intent = new Intent(this, AlterPasswdActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (v.getId() == R.id.fragment_mine_ll_exitLogin) {
            showToast("退出登录");
        }
    }
}
