package com.example.fragmentandviewpage2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fragmentandviewpage2.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager2 viewPager2;
    private LinearLayout llChat, llContact, llFind, llMine;
    private ImageView ivWeixin, ivContact, ivFind, ivMine, ivCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();
        initTableView();
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
    }

    private void initPager() {
        viewPager2 = findViewById(R.id.id_viewpager);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(BlankFragment.newInstance("微信聊天"));
        fragmentArrayList.add(BlankFragment.newInstance("通讯录"));
        fragmentArrayList.add(BlankFragment.newInstance("发现"));
        fragmentArrayList.add(BlankFragment.newInstance("我"));
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentArrayList);
        viewPager2.setAdapter(myFragmentPagerAdapter);
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

    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        viewPager2.setCurrentItem(position);
        switch (position) {
            case 0:
                ivWeixin.setSelected(true);
                ivCurrent = ivWeixin;
                break;
            case 1:
                ivContact.setSelected(true);
                ivCurrent = ivContact;
                break;
            case 2:
                ivFind.setSelected(true);
                ivCurrent = ivFind;
                break;
            case 3:
                ivMine.setSelected(true);
                ivCurrent = ivMine;
                break;
        }
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
}