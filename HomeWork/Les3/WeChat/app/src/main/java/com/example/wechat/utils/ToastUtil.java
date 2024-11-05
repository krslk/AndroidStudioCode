package com.example.wechat.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.wechat.R;

public class ToastUtil {
    public static void showTips(Context context, String msg, int duration, View view) {
        Toast toast = Toast.makeText(context, msg, duration);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40);
        toast.setView(view);
        toast.show();
    }
}
