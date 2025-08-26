package com.example.dailynews.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dailynews.R;
import com.example.dailynews.activity.AddNewsActivity;

public class AddNewsViewPagerFragment extends Fragment {
    private View rootView;
    private static final String ARG_PARAM1 = "param1";
    private Button button;
    private String mParam1;

    public AddNewsViewPagerFragment() {
    }

    public static AddNewsViewPagerFragment newInstance(String param1) {
        AddNewsViewPagerFragment fragment = new AddNewsViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_collection_view_pager, container, false);
        }
        button = rootView.findViewById(R.id.btn_fragment_addNews);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewsActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
