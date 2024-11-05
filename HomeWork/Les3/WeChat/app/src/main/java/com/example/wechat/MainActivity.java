package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechat.adapter.MainFragmentPagerAdapter;
import com.example.wechat.entity.Account;
import com.example.wechat.fragment.OwnInfoFragment;
import com.example.wechat.utils.AccountUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Account account;
    private ViewPager2 viewPager2;
    private LinearLayout llChat, llContact, llFind, llMine;
    private View llTopBar;
    private ImageView ivWeixin, ivContact, ivFind, ivMine, ivCurrent;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            account = bundle.getParcelable("userinfo");
            if (account != null) {
                String msg = "用户 " + account.getUsername() + " 登录成功";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        } else {
            bundle = new Bundle();
        }
        bundle.putString("message", "退出登录");
        setResult(Activity.RESULT_OK, new Intent().putExtras(bundle));
        initTableView();
        initPager();
    }

    private void initTableView() {
        llChat = findViewById(R.id.table_weixin);
        llChat.setOnClickListener(this);
        llContact = findViewById(R.id.table_contact);
        llContact.setOnClickListener(this);
        llFind = findViewById(R.id.table_find);
        llFind.setOnClickListener(this);
        llMine = findViewById(R.id.table_mine);
        llMine.setOnClickListener(this);
        ivWeixin = findViewById(R.id.tab_iv_weixin);
        ivContact = findViewById(R.id.tab_iv_contact);
        ivFind = findViewById(R.id.tab_iv_find);
        ivMine = findViewById(R.id.tab_iv_mine);
        ivWeixin.setSelected(true);
        ivCurrent = ivWeixin;
        tv_title = findViewById(R.id.main_activity_tv_top_title);
        llTopBar = findViewById(R.id.main_activity_top_bar);
    }

    private void initPager() {
        viewPager2 = findViewById(R.id.viewPager_Index);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(Fragment_Index.newInstance("微信聊天"));
        fragmentArrayList.add(Fragment_Index.newInstance("通讯录"));
        fragmentArrayList.add(Fragment_Index.newInstance("发现"));
        fragmentArrayList.add(OwnInfoFragment.newInstance(account));
        MainFragmentPagerAdapter mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentArrayList);
        viewPager2.setAdapter(mainFragmentPagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //可以在此添加正在滚动的动画效果
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.table_weixin) {
            changeTab(0);
        } else if (v.getId() == R.id.table_contact) {
            changeTab(1);
        } else if (v.getId() == R.id.table_find) {
            changeTab(2);
        } else if (v.getId() == R.id.table_mine) {
            changeTab(3);
        }
    }

    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        viewPager2.setCurrentItem(position);
        switch (position) {
            case 0:
                ivWeixin.setSelected(true);
                ivCurrent = ivWeixin;
                llTopBar.setVisibility(View.VISIBLE);
                tv_title.setText("微信");
                break;
            case 1:
                ivContact.setSelected(true);
                ivCurrent = ivContact;
                llTopBar.setVisibility(View.VISIBLE);
                tv_title.setText("通讯录");
                break;
            case 2:
                ivFind.setSelected(true);
                tv_title.setText("发现");
                llTopBar.setVisibility(View.VISIBLE);
                ivCurrent = ivFind;
                break;
            case 3:
                ivMine.setSelected(true);
                tv_title.setText("我");
                llTopBar.setVisibility(View.GONE);
                ivCurrent = ivMine;
                break;
        }
    }
}