package com.example.dailynews.fragment;

import com.example.dailynews.Api.PathApi;
import com.example.dailynews.R;
import com.example.dailynews.activity.AboutActivity;
import com.example.dailynews.activity.HelpActivity;
import com.example.dailynews.activity.SettingActivity;
import com.example.dailynews.dbhelper.DBHelper;
import com.example.dailynews.entity.Userinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MineViewPagerFragment extends BaseFragment {
    private View rootView;
    private static final String ARG_PARAM1 = "userinfo";
    private Userinfo userinfo;
    private MinePage minePage = null;
    private DBHelper dbHelper;

    public MineViewPagerFragment() {
    }

    public static MineViewPagerFragment newInstance(Userinfo param1) {
        MineViewPagerFragment fragment = new MineViewPagerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = DBHelper.getInstance(getContext());
        dbHelper.openReadLink();
        userinfo = dbHelper.selectUserByAccount(getStringFromSp("current"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mine_view_pager, container, false);
        }
        minePage = new MinePage(rootView);
        minePage.ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("userinfo", userinfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        minePage.ll_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
        minePage.ll_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HelpActivity.class));
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        userinfo = dbHelper.selectUserByAccount(getStringFromSp("current"));
        Bitmap bitmap = BitmapFactory.decodeFile(PathApi.getPath(userinfo.getAvatarPath()));
        minePage.getAvatar().setImageBitmap(bitmap);
        minePage.getUsername().setText(userinfo.getUsername());
        minePage.getAccount().setText(userinfo.getAccount());
    }

    private static class MinePage {
        private View v;
        public ImageView avatar;
        public TextView username;
        public TextView account;
        public LinearLayout ll_setting, ll_about, ll_help;

        public MinePage() {
        }

        public MinePage(View v) {
            avatar = v.findViewById(R.id.img_mine_avatar);
            username = v.findViewById(R.id.img_mine_username);
            account = v.findViewById(R.id.img_mine_account);
            ll_setting = v.findViewById(R.id.fragment_mine_ll_setting);
            ll_help = v.findViewById(R.id.fragment_mine_ll_help);
            ll_about = v.findViewById(R.id.fragment_mine_ll_about);
        }

        public ImageView getAvatar() {
            return avatar;
        }

        public void setAvatar(ImageView avatar) {
            this.avatar = avatar;
        }

        public TextView getUsername() {
            return username;
        }

        public void setUsername(TextView username) {
            this.username = username;
        }

        public TextView getAccount() {
            return account;
        }

        public void setAccount(TextView account) {
            this.account = account;
        }

        @Override
        public String toString() {
            return "MinePage{" +
                    "v=" + v +
                    ", avatar=" + avatar +
                    ", username=" + username +
                    ", account=" + account +
                    '}';
        }
    }
}