package com.example.dianhualanjie;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CustomPhoneStateListener extends PhoneStateListener {

    private Context context;

    public CustomPhoneStateListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("PhoneStateListener", "Incoming call from: " + phoneNumber);
                if (isUnknownNumber(phoneNumber)) {
                    // 拦截电话
                    endCall();
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("PhoneStateListener", "Call ended");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("PhoneStateListener", "Call answered");
                break;
        }
    }

    private boolean isUnknownNumber(String phoneNumber) {
        // 这里可以添加逻辑来判断是否是陌生号码
        // 例如，检查联系人列表或黑名单
        return true; // 假设所有号码都是陌生号码
    }

    private void endCall() {
        // 这里需要实现挂断电话的逻辑
        // 由于Android的限制，直接挂断电话可能需要使用反射或其他方法
        Log.d("PhoneStateListener", "Call blocked");
    }
}