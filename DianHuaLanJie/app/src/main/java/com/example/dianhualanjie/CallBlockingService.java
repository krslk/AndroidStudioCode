package com.example.dianhualanjie;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_PHONE_CALL;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class CallBlockingService extends Service {

    private TelephonyManager telephonyManager;
    private CustomPhoneStateListener phoneStateListener;

    @Override
    public void onCreate() {
        super.onCreate();
        startForegroundService();
        startCallListener();
    }

    @SuppressLint("ForegroundServiceType")
    private void startForegroundService() {
        // 创建通知渠道（Android 8.0 及以上需要）
        String channelId = "call_blocking_service_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Call Blocking Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        // 创建通知
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Call Blocking Service")
                .setContentText("Service is running in the background")
                .setSmallIcon(R.drawable.ic_notification) // 替换为你的通知图标
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 及以上版本需要指定 foregroundServiceType
            startForeground(1, notification, FOREGROUND_SERVICE_TYPE_PHONE_CALL);
        } else {
            // Android 10 以下版本不需要指定 foregroundServiceType
            startForeground(1, notification);
        }
    }

    private void startCallListener() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new CustomPhoneStateListener(this);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}