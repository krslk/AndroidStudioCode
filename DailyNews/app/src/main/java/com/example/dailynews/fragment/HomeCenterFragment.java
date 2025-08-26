package com.example.dailynews.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dailynews.R;
import com.example.dailynews.adapter.NewsListViewAdapter;
import com.example.dailynews.dbhelper.DBHelper;
import com.example.dailynews.entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class HomeCenterFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private View rootView;
    private ListView listView;
    private List<NewsItem> newsLists = new ArrayList<>();
    private DBHelper dbHelper;
    private NewsListViewAdapter listViewAdapter;

    public HomeCenterFragment() {
    }

    public static HomeCenterFragment newInstance(String param1) {
        HomeCenterFragment fragment = new HomeCenterFragment();
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
            rootView = inflater.inflate(R.layout.fragment_home_center, container, false);
        }
        listView = rootView.findViewById(R.id.lv_item_video);
        dbHelper = DBHelper.getInstance(getContext());
        dbHelper.openReadLink();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getNewsList();
        listViewAdapter = new NewsListViewAdapter(getContext(), newsLists);
        listView.setAdapter(listViewAdapter);
    }

    private void getNewsList() {
        newsLists = dbHelper.selectNewsItemByType(mParam1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dbHelper.closeAllLink();
    }
}