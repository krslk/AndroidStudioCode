package com.example.wechat.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.entity.Account;
import com.example.wechat.fragment.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class OwnInfoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private View view;
    private TextView tv_accountShow;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OwnInfoFragment() {
    }

    public static OwnInfoFragment newInstance(Account account) {
        OwnInfoFragment fragment = new OwnInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        if (account != null) {
            args.putParcelable("userinfo", account);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_own_info_list, container, false);
        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new OwnInfoitemRecyclerViewAdapter(PlaceholderContent.ITEMS));
        }
        initView();
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            Account account = getArguments().getParcelable("userinfo");
            if (account != null) {
                String str = "微信号：" + account.getUsername();
                tv_accountShow.setText(str);
            }
        }
        return view;
    }

    private void initView() {
        tv_accountShow = view.findViewById(R.id.tv_account);
    }
}