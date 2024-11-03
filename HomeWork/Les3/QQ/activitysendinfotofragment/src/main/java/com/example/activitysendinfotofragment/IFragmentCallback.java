package com.example.activitysendinfotofragment;

public interface IFragmentCallback {
    void sendMsgToActivity(String string);

    String getMsgFromActivity(String msg);
}