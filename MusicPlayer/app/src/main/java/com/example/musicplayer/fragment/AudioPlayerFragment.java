package com.example.musicplayer.fragment;

import static android.content.Context.BIND_AUTO_CREATE;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.broadcast.MusicCallImp;
import com.example.musicplayer.broadcast.MusicReceiver;
import com.example.musicplayer.entity.Music;
import com.example.musicplayer.service.MusicService;
import com.example.musicplayer.utils.DataUtil;
import com.example.musicplayer.viewmodel.MusicViewModel;

import java.util.List;

public class AudioPlayerFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private View rootView;
    private MusicViewModel sharedViewModel;
    private ObjectAnimator animator;
    private static SeekBar seekBar;
    private static TextView tv_progress, tv_total;
    private ImageView img_avatar, img_pointer;
    private TextView tv_title, tv_author;
    private MusicService.MusicControl musicControl;
    private MyServiceConn conn;
    private Button btn_play;
    private ImageButton btn_pre, btn_next;
    private Intent intent;
    private List<Music> list;
    private int position = 0;
    private boolean isPlay = false;
    private boolean isUnbind = false;
    private boolean interrupt = false;
    private MusicReceiver musicReceiver;

    public AudioPlayerFragment() {
    }

    public AudioPlayerFragment(Context context) {
        this.mContext = context;
    }

    public static AudioPlayerFragment newInstance(Context context) {
        AudioPlayerFragment fragment = new AudioPlayerFragment(context);
        return fragment;
    }

    public static AudioPlayerFragment newInstance() {
        AudioPlayerFragment fragment = new AudioPlayerFragment();
        Bundle args = new Bundle();
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
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_audio_player, container, false);
        }
        intent = new Intent(mContext, MusicService.class);
        conn = new MyServiceConn();
        mContext.bindService(intent, conn, BIND_AUTO_CREATE);
        initView();
        list = DataUtil.getMusicList(mContext);
        tv_title.setText(list.get(position).getName());
        tv_author.setText(list.get(position).getAuthor().equals("<unknown>") ? "未知歌手" : list.get(position).getAuthor());
        sharedViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        sharedViewModel.getSelectedMusic().observe(getViewLifecycleOwner(), new Observer<Music>() {
            @Override
            public void onChanged(Music m) {
                if (m != null) {
                    startPlay(m);
                }
            }
        });

        MusicReceiver.addCallBack(new MusicCallImp() {
            @Override
            public void audioFocusOccupy() {
                if (!interrupt) {
                    interrupt = true;
                    stopPlay();
                }
            }

            @Override
            public void audioFocusFree() {
                if (interrupt) {
                    interrupt = false;
                    isPlay = true;
                    btn_play.setSelected(true);
                    img_pointer.setRotation(0f);
                    musicControl.continuePlay();
                    animator.resume();
                }
            }
        });
        musicReceiver = new MusicReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        mContext.registerReceiver(musicReceiver, intentFilter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView() {
        tv_progress = rootView.findViewById(R.id.tv_progress);
        tv_total = rootView.findViewById(R.id.tv_total);
        seekBar = rootView.findViewById(R.id.sb_progress);
        btn_play = rootView.findViewById(R.id.btn_play);
        btn_play.setOnClickListener(this);
        btn_pre = rootView.findViewById(R.id.btn_pre);
        btn_pre.setOnClickListener(this);
        btn_next = rootView.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        tv_author = rootView.findViewById(R.id.fragment_musicPlayer_tv_author);
        tv_title = rootView.findViewById(R.id.fragment_musicPlayer_tv_title);
        img_avatar = rootView.findViewById(R.id.image_music_player_avatar);
        img_pointer = rootView.findViewById(R.id.img_music_play_disk_show);
        img_pointer.setRotation(-30f);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == seekBar.getMax()) {
                    animator.pause();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                musicControl.seekTo(progress);
            }
        });
        animator = ObjectAnimator.ofFloat(img_avatar, "rotation", 0f, 360.0f);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play) {
            if (!isPlay) {
                if (list.size() == 0) {
                    Toast.makeText(mContext, "请添加音乐后再播放", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!musicControl.havePlay()) {
                    startPlay(list.get(position));
                } else {
                    isPlay = true;
                    btn_play.setSelected(true);
                    img_pointer.setRotation(0f);
                    musicControl.continuePlay();
                    animator.resume();
                }
            } else {
                stopPlay();
            }
        } else if (v.getId() == R.id.btn_pre) {
            position = position == 0 ? list.size() - 1 : position - 1;
            startPlay(list.get(position));
        } else if (v.getId() == R.id.btn_next) {
            position = (position + 1) % list.size();
            startPlay(list.get(position));
        }
    }

    private void startPlay(Music music) {
        isPlay = true;
        btn_play.setSelected(true);
        img_pointer.setRotation(0f);
        tv_title.setText(music.getName());
        tv_author.setText(music.getAuthor().equals("<unknown>") ? "未知歌手" : music.getAuthor());
        musicControl.play(Uri.parse(music.getPath()));
        animator.start();
    }


    private void stopPlay() {
        isPlay = false;
        btn_play.setSelected(false);
        img_pointer.setRotation(-30f);
        musicControl.pausePlay();
        animator.pause();
    }

    class MyServiceConn implements ServiceConnection { //用于实现连接服务
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (MusicService.MusicControl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private void unbind(boolean isUnbind) {
        if (!isUnbind) {
            musicControl.pausePlay();
            mContext.unbindService(conn);
            mContext.stopService(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind(isUnbind);
        if (musicReceiver != null) {
            mContext.unregisterReceiver(musicReceiver);
        }
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData(); //获取从子线程发送过来的音乐播放进度
            int duration = bundle.getInt("duration");                  //音乐的总时长
            int currentPosition = bundle.getInt("currentPosition");//音乐当前进度
            seekBar.setMax(duration);                //设置SeekBar的最大值为音乐总时长
            seekBar.setProgress(currentPosition);//设置SeekBar当前的进度位置
            //音乐的总时长
            int minute = duration / 1000 / 60;
            int second = duration / 1000 % 60;
            String strMinute = null;
            String strSecond = null;
            if (minute < 10) {              //如果音乐的时间中的分钟小于10
                strMinute = "0" + minute; //在分钟的前面加一个0
            } else {
                strMinute = minute + "";
            }
            if (second < 10) {             //如果音乐的时间中的秒钟小于10
                strSecond = "0" + second;//在秒钟前面加一个0
            } else {
                strSecond = second + "";
            }
            tv_total.setText(strMinute + ":" + strSecond);
            //音乐当前播放时长
            minute = currentPosition / 1000 / 60;
            second = currentPosition / 1000 % 60;
            if (minute < 10) {             //如果音乐的时间中的分钟小于10
                strMinute = "0" + minute;//在分钟的前面加一个0
            } else {
                strMinute = minute + "";
            }
            if (second < 10) {               //如果音乐的时间中的秒钟小于10
                strSecond = "0" + second;  //在秒钟前面加一个0
            } else {
                strSecond = second + "";
            }
            tv_progress.setText(strMinute + ":" + strSecond);
        }
    };
}