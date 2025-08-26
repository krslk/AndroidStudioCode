package com.example.musicplayer.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.ViewPagerAdapterForBottomBar;
import com.example.musicplayer.entity.Music;
import com.example.musicplayer.entity.User;
import com.example.musicplayer.fragment.AudioPlayerFragment;
import com.example.musicplayer.fragment.IndexViewPagerFragment;
import com.example.musicplayer.fragment.MineViewPagerFragment;
import com.example.musicplayer.utils.StringUtil;
import com.example.musicplayer.viewmodel.MusicViewModel;

import java.util.ArrayList;

public class IndexActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager2 viewPager2;
    private LinearLayout llHome, llPlayer, llMine;
    private ImageView ivHome, ivPlayer, ivMine, ivCurrent;
    private TextView tv_title;
    private User userinfo;
    MusicViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        String autoLogin = findFromSP("auto_login");
        if (StringUtil.isEmpty(autoLogin) || !autoLogin.equals("true")) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
        initView();
        initViewPager();
        sharedViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != getPackageManager().PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            Toast.makeText(this, "来电将自动暂停音乐播放", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViewPager() {
        viewPager2 = findViewById(R.id.vp_index);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(IndexViewPagerFragment.newInstance(this, "主页"));
        fragmentArrayList.add(AudioPlayerFragment.newInstance(this));
        fragmentArrayList.add(MineViewPagerFragment.newInstance(this, "我的"));
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
        changeTab(1);
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
                ivPlayer.setSelected(true);
                ivCurrent = ivPlayer;
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
        llPlayer = findViewById(R.id.table_audio_player);
        llPlayer.setOnClickListener(this);
        llMine = findViewById(R.id.table_mine);
        llMine.setOnClickListener(this);
        ivHome = findViewById(R.id.tab_iv_home);
        ivPlayer = findViewById(R.id.tab_iv_audio_player);
        ivMine = findViewById(R.id.tab_iv_mine);
        ivPlayer.setSelected(true);
        ivCurrent = ivPlayer;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.table_home) {
            changeTab(0);
        } else if (v.getId() == R.id.table_audio_player) {
            changeTab(1);
        } else if (v.getId() == R.id.table_mine) {
            changeTab(2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "来电将自动暂停音乐播放", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未获取到访问电话权限,来点将不会提醒", Toast.LENGTH_SHORT).show();
        }
    }
}
