package com.example.wechat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Fragment_Index extends Fragment {
    private static final String ARG_TEXT = "param1";
    private String mTextString;
    private View rootView;

    public Fragment_Index() {
    }

    public static Fragment_Index newInstance(String param1) {
        Fragment_Index fragment = new Fragment_Index();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTextString = getArguments().getString(ARG_TEXT); //这步只能在onCreate方法里？？？？？
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_index, container, false);
        }
        initView();
        return rootView;
    }

    private void initView() {
        TextView textView = rootView.findViewById(R.id.tvTitle);
        textView.setText(mTextString);
    }
}