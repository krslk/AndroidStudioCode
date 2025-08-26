package com.example.musicplayer.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(getFilesDir() + "./music_player.db", Context.MODE_PRIVATE, null);
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> shortcuts = new ArrayList<>();

            shortcuts.add(new ShortcutInfo.Builder(this, "dynamic_page1")
                    .setShortLabel("播放音乐")
                    .setLongLabel("播放音乐")
                    .setIcon(Icon.createWithResource(this, R.mipmap.icon_bottom_bar_player_selected))
                    .setIntent(new Intent(this, IndexActivity.class).setAction(Intent.ACTION_VIEW))
                    .build());

            shortcuts.add(new ShortcutInfo.Builder(this, "dynamic_page2")
                    .setShortLabel("注册账号")
                    .setLongLabel("注册账号")
                    .setIcon(Icon.createWithResource(this, R.mipmap.icon_app))
                    .setIntent(new Intent(this, RegisterActivity.class).setAction(Intent.ACTION_VIEW))
                    .build());

            shortcutManager.setDynamicShortcuts(shortcuts);
        }

    }

    protected void saveToSp(String key, String value) {
        SharedPreferences sp = getSharedPreferences("music_player", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected String findFromSP(String key) {
        return getSharedPreferences("music_player", MODE_PRIVATE).getString(key, "");
    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
