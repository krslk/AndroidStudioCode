package com.example.dailynews.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.example.dailynews.Api.PathApi;
import com.example.dailynews.R;
import com.example.dailynews.dbhelper.DBHelper;
import com.example.dailynews.entity.Userinfo;
import com.example.dailynews.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AlterOwnInfoActivity extends BaseActivity {
    private ImageView avatar;
    private Userinfo userinfo;
    private EditText et_username;
    private DBHelper dBHelper;
    private Uri photoUri;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_own_info);
        dBHelper = DBHelper.getInstance(this);
        dBHelper.openWriteLink();
        userinfo = getIntent().getParcelableExtra("userinfo");
        avatar = findViewById(R.id.iv_alter_info_avatar);
        et_username = findViewById(R.id.et_alter_info_username);
        avatar.setImageBitmap(BitmapFactory.decodeFile(PathApi.getPath(userinfo.getAvatarPath())));
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o != null) {
                    if (o.getData() != null && o.getData().getExtras() != null && o.getData().getExtras().get("data") != null) {
                        Bitmap bitmap = (Bitmap) o.getData().getExtras().get("data");
                        avatar.setImageBitmap(bitmap);
                        File file = new File(getFilesDir(), "/img");// 保存的目录
                        if (!file.exists()) {// 目录是否存在
                            file.mkdir(); // 如果不存在则创建该目录
                        }
                        String avatarPath = "/avatar_" + System.currentTimeMillis() + ".png";
                        File imageFile = new File(file.getAbsolutePath(), avatarPath);
                        FileOutputStream os = null;
                        try {
                            os = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 85, os);
                            os.flush();
                            os.close();
                        } catch (FileNotFoundException e) {
                            Log.w("AlterOwnInfoActivity", e.getMessage());
                        } catch (IOException e) {
                            Log.w("AlterOwnInfoActivity", e.getMessage());
                        }
                        userinfo.setAvatarPath("/img" + avatarPath);
                        dBHelper.updataInfoByUserinfo(userinfo);
                        showToast("修改成功");
                    }
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }

    public void takePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        register.launch(intent);
    }

    public void optFromAlbum(View view) {
        showToast("相册");
    }

    public void updataInfo(View view) {
        String str = et_username.getText().toString();
        if (StringUtils.isEmpty(str)) {
            showToast("用户名不能修改为空");
            return;
        }
        userinfo.setUsername(str);
        dBHelper.updataInfoByUserinfo(userinfo);
        showToast("修改成功");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }
}