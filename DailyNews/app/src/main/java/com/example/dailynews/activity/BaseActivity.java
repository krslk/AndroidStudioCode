package com.example.dailynews.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase = null;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (sqLiteDatabase == null) {
            String str = getFilesDir() + "./daily_news.db";
            sqLiteDatabase = openOrCreateDatabase(str, Context.MODE_PRIVATE, null);
        }
    }

    protected void saveStringToSp(String key, String val) {
        SharedPreferences sharedPreferences = getSharedPreferences("daily_news", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    protected String selectStringFromSp(String key) {
        SharedPreferences sp = mContext.getSharedPreferences("daily_news", MODE_PRIVATE);
        return sp.getString(key, "");
    }

    protected void showToast(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean permissionIsGet(String permission) {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void permissionGet(String permission) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            showToast("权限已获取");
            return;
        } else {
            showToast("正在获取权限");
            requestPermissions(new String[]{permission}, 1);
        }
        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showToast("允许已获取");
        } else {
//            showToast("权限被拒绝");
        }
    }
}
