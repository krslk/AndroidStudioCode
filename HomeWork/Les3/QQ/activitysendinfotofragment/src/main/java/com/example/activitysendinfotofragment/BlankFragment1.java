package com.example.activitysendinfotofragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 观察者，发布订阅
 * 其他方案：eventBus,LiveData...
 */
public class BlankFragment1 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private IFragmentCallback fragmentCallback;
    private View root;

    public BlankFragment1() {
    }

    public void setFragmentCallback(IFragmentCallback iFragmentCallback) {
        fragmentCallback = iFragmentCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("Fragment", "onCreate");
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w("Fragment", "onCreateView");
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_blank1, container, false);
            Button btn_send = root.findViewById(R.id.btn_send);
            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentCallback.sendMsgToActivity("hello,I am fragment.");
                    String msg = fragmentCallback.getMsgFromActivity(null);
                    Toast.makeText(BlankFragment1.this.getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.w("Fragment", "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.w("Fragment", "onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w("Fragment", "onDestroy");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w("Fragment", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w("Fragment", "onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w("Fragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w("Fragment", "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.w("Fragment", "onDestroyView");
    }
}