package com.example.musicplayer.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.activity.AboutOurActivity;
import com.example.musicplayer.activity.OwnInfoActivity;
import com.example.musicplayer.activity.SettingActivity;
import com.example.musicplayer.dbHelper.MusicPlayerDBHelper;
import com.example.musicplayer.entity.User;
import com.example.musicplayer.utils.StringUtil;

public class MineViewPagerFragment extends BaseFragment {
    private LinearLayout ll_about, ll_setting, ll_own;
    private ImageView img_avatar;
    private TextView tv_account, tv_username;
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private Context mContext;
    private View rootView;
    private MusicPlayerDBHelper musicPlayerDBHelper;
    private BroadcastReceiver dataUpdateReceiver;

    public MineViewPagerFragment() {
    }

    public MineViewPagerFragment(Context context) {
        this.mContext = context;
    }

    public static MineViewPagerFragment newInstance(Context context, String mParam1) {
        MineViewPagerFragment fragment = new MineViewPagerFragment(context);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mParam1);
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
            rootView = inflater.inflate(R.layout.fragment_mine_view_pager, container, false);
        }
        initView();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_default_avatar);
        Bitmap circleBitmap = getCroppedBitmap(bitmap, 20);
        mContext = getContext();
        img_avatar.setImageBitmap(circleBitmap);
        musicPlayerDBHelper = MusicPlayerDBHelper.getInstance(getContext());
        musicPlayerDBHelper.openReadLink();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        img_avatar = rootView.findViewById(R.id.fragment_mine_avatar);
        tv_account = rootView.findViewById(R.id.fragment_mine_account);
        tv_username = rootView.findViewById(R.id.fragment_mine_username);
        ll_setting = rootView.findViewById(R.id.fragment_mine_ll_setting);
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SettingActivity.class);
                startActivity(intent);
            }
        });
        ll_about = rootView.findViewById(R.id.fragment_mine_ll_about);
        ll_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AboutOurActivity.class);
                startActivity(intent);
            }
        });
        ll_own = rootView.findViewById(R.id.fragment_mine_ll_own);
        ll_own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OwnInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    void updateUIData(User user) {
        if (!StringUtil.isEmpty(user.getAvatar())) {
            Bitmap bitmap = BitmapFactory.decodeFile(mContext.getFilesDir() + user.getAvatar());
            Bitmap circleBitmap = getCroppedBitmap(bitmap, 20);
            img_avatar.setImageBitmap(circleBitmap);
        }
        if (!user.getAccount().equals(tv_account.getText().toString())) {
            tv_account.setText(user.getAccount());
        }
        if (!StringUtil.isEmpty(user.getUsername()) && !user.getUsername().equals(tv_username.getText().toString())) {
            tv_username.setText(user.getUsername());
        }
    }

    private Bitmap getCroppedBitmap(Bitmap bitmap, int borderWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = Math.min(width, height);

        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, size, size);
        final RectF rectF = new RectF(rect);

        // 创建透明背景的圆形图形
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);  // 设置圆形颜色
        canvas.drawARGB(0, 0, 0, 0);  // 清除画布背景
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);

        // 剪切图片为圆形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, new Rect(0, 0, width, height), rect, paint);

        // 添加边框
        paint.setXfermode(null);
        paint.setStrokeWidth(borderWidth);  // 边框宽度
        paint.setColor(Color.WHITE);  // 边框颜色
        paint.setStyle(Paint.Style.STROKE);  // 设置为描边模式
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - borderWidth / 2f, paint);
        return output;
    }

    @Override
    public void onStart() {
        super.onStart();
        User user = musicPlayerDBHelper.selectUserByAccount(findFromSP("account"));
        updateUIData(user);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        musicPlayerDBHelper.closeAllLink();
    }
}