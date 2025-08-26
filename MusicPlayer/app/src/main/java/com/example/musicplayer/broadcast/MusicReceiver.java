package com.example.musicplayer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class MusicReceiver extends BroadcastReceiver {
    private static MusicCallImp RingingCallback;

    public static void addCallBack(MusicCallImp callback) {
        RingingCallback = callback;
    }

    public MusicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            RingingCallback.audioFocusOccupy();
        } else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            RingingCallback.audioFocusOccupy();
        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            RingingCallback.audioFocusFree();
        }
    }
}

