package com.example.musicplayer.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicplayer.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1;
    private LinearLayout ll_loginOut, ll_updatePassword;
    private NotificationManager notificationManager;
    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ll_loginOut = findViewById(R.id.activity_setting_ll_updatePassword);
        ll_updatePassword = findViewById(R.id.activity_setting_ll_loginOut);
        ll_loginOut.setOnClickListener(this);
        ll_updatePassword.setOnClickListener(this);
        notificationManager = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);//获取系统通知管理器

        Intent intent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 80, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_MUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("valley", "通知", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
            notification = new Notification.Builder(this, "valley")
                    .setContentTitle("下线通知")
                    .setContentText("您的账号状态发生改变，请重新登录")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_app))
                    .setColor(Color.RED)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();

        }
    }

    public void toBack(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_setting_ll_updatePassword) {
            startActivity(new Intent(this, ResetPasswdActivity.class));
        } else {
            AlertDialog alert = null;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            alert = builder.setIcon(R.mipmap.icon_alter_dialog_login_out)
                    .setTitle("系统提示：")
                    .setMessage("您确定要退出账号吗?")
                    .setNegativeButton("狠心离开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showNotification();
                            saveToSp("auto_login", "false");
                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("我再想想", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(SettingActivity.this, "您点击了取消~", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
            alert.show();
        }
    }

    private void showNotification() {
        notificationManager.notify(10005, notification);
    }
}
