package com.example.dailynews.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dailynews.R;
import com.example.dailynews.adapter.ViewPagerAdapterForBottomBar;
import com.example.dailynews.entity.Userinfo;
import com.example.dailynews.fragment.AddNewsViewPagerFragment;
import com.example.dailynews.fragment.HomeViewPagerFragment;
import com.example.dailynews.fragment.MineViewPagerFragment;

import java.util.ArrayList;

public class IndexActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager2 viewPager2;
    private LinearLayout llHome, llCollection, llMine;
    private ImageView ivHome, ivCollection, ivMine, ivCurrent;
    private TextView tv_title;
    private Userinfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        userinfo = getIntent().getParcelableExtra("userinfo");
        initView();
        initViewPager();
    }

    private void initViewPager() {
        viewPager2 = findViewById(R.id.vp_index);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(HomeViewPagerFragment.newInstance());
        fragmentArrayList.add(AddNewsViewPagerFragment.newInstance("发布"));
        fragmentArrayList.add(MineViewPagerFragment.newInstance(userinfo));
        ViewPagerAdapterForBottomBar mainFragmentPagerAdapter = new ViewPagerAdapterForBottomBar(getSupportFragmentManager(), getLifecycle(), fragmentArrayList);
        viewPager2.setAdapter(mainFragmentPagerAdapter);
        viewPager2.setOffscreenPageLimit(fragmentArrayList.size());
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滚动的动画效果
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
                ivHome.setSelected(true);
                ivCurrent = ivHome;
                break;
            case 1:
                ivCollection.setSelected(true);
                ivCurrent = ivCollection;
                break;
            case 2:
                ivMine.setSelected(true);
                ivCurrent = ivMine;
                break;
        }
    }

    private void initView() {
        llHome = findViewById(R.id.table_home);
        llHome.setOnClickListener(this);
        llCollection = findViewById(R.id.table_collection);
        llCollection.setOnClickListener(this);
        llMine = findViewById(R.id.table_mine);
        llMine.setOnClickListener(this);
        ivHome = findViewById(R.id.tab_iv_home);
        ivCollection = findViewById(R.id.tab_iv_collection);
        ivMine = findViewById(R.id.tab_iv_mine);
        ivHome.setSelected(true);
        ivCurrent = ivHome;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.table_home) {
            changeTab(0);
        } else if (v.getId() == R.id.table_collection) {
            changeTab(1);
        } else if (v.getId() == R.id.table_mine) {
            changeTab(2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}