package com.example.musicplayer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicplayer.R;
import com.example.musicplayer.dbHelper.MusicPlayerDBHelper;
import com.example.musicplayer.entity.User;
import com.example.musicplayer.utils.StringUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class OwnInfoActivity extends BaseActivity {
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private ImageView imageView;
    private EditText et_username, et_email;
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private Uri photoUri;
    MusicPlayerDBHelper musicPlayerDBHelper;
    private User userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_own_info);
        musicPlayerDBHelper = MusicPlayerDBHelper.getInstance(this);
        musicPlayerDBHelper.openReadLink();
        musicPlayerDBHelper.openWriteLink();
        userinfo = musicPlayerDBHelper.selectUserByAccount(findFromSP("account"));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == RESULT_OK) {
                    if (o.getData() != null) {
                        Toast.makeText(OwnInfoActivity.this, "功能待完善", Toast.LENGTH_SHORT).show();
                    } else {
                        if (photoUri != null) {
                            String path = photoUri.getPath();
                            userinfo.setAvatar("/avatars" + path.substring(path.lastIndexOf("/")));
                            imageView.setImageBitmap(BitmapFactory.decodeFile(getFilesDir() + path));
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        imageView = findViewById(R.id.ownInfo_activity_avatar);
        et_username = findViewById(R.id.ownInfo_activity_username);
        et_email = findViewById(R.id.ownInfo_activity_email);
        et_username.setText(userinfo.getUsername());
        et_email.setText(userinfo.getEmail());
        if (!StringUtil.isEmpty(userinfo.getAvatar())) {
            Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + userinfo.getAvatar());
            imageView.setImageBitmap(bitmap);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
    }

    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择上传方式")
                .setItems(new String[]{"拍照上传", "从相册选择"}, (dialog, which) -> {
                    if (which == 0) {
                        upLoadByCamera();
                    } else {
                        upLoadByAlbum();
                    }
                }).show();
    }

    private void upLoadByAlbum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openAlbum();//打开相册
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_ALBUM);
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_ALBUM);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openAlbum();//打开相册
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ALBUM);
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ALBUM);
            }
        }
    }

    //权限获取方式参考自官方文档https://developer.android.google.cn/training/permissions/requesting?hl=zh-cn
    private void upLoadByCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();//打开相机
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        } else {
            // You can directly ask for the permission.
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        }
    }

    private File createavatarFile() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss_" + new Random().nextInt(10000), Locale.getDefault()).format(new Date());
        String imageName = "IMG_CAMERA_" + timestamp;
        File dir = new File(getFilesDir() + "/avatars");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return new File(dir, imageName + ".jpg");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    openCamera();
                    break;
                case REQUEST_CODE_ALBUM:
                    openAlbum();
                    break;
            }
        } else {
            Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File avatarFile = createavatarFile();
            if (avatarFile != null) {
                photoUri = FileProvider.getUriForFile(this, "com.example.musicplayer.file_provider", avatarFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                intentActivityResultLauncher.launch(intent);
            }
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        if (intent.resolveActivity(getPackageManager()) != null) {
            File avatarFile = createavatarFile();
            if (avatarFile != null) {
                photoUri = FileProvider.getUriForFile(this, "com.example.musicplayer.file_provider", avatarFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                intentActivityResultLauncher.launch(intent);
            }
        }
    }

    public void toBack(View view) {
        finish();
    }

    public void commitInfo(View view) {
        String username = et_username.getText().toString();
        String email = et_email.getText().toString();
        if (!StringUtil.isEmpty(username) && !username.equals(userinfo.getUsername())) {
            userinfo.setUsername(username);
        }
        if (!StringUtil.isEmpty(email) && !email.equals(userinfo.getEmail())) {
            userinfo.setEmail(email);
        }
        musicPlayerDBHelper.updateUserByUser(userinfo);
        finish();
    }
}