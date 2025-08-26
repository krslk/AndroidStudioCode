package com.example.musicplayer.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.ListViewAdapterForIndex;
import com.example.musicplayer.entity.Music;
import com.example.musicplayer.utils.DataUtil;
import com.example.musicplayer.viewmodel.MusicViewModel;

import java.util.ArrayList;
import java.util.List;

public class IndexViewPagerFragment extends BaseFragment {
    private Context mContext;
    private View rootView;
    private List<Music> musicList = new ArrayList<>();
    private ListView listView;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private MusicViewModel sharedViewModel;

    public IndexViewPagerFragment() {
    }

    public IndexViewPagerFragment(Context context) {
        this.mContext = context;
    }

    public static IndexViewPagerFragment newInstance(Context context, String mParam1) {
        IndexViewPagerFragment fragment = new IndexViewPagerFragment(context);
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
            rootView = inflater.inflate(R.layout.fragment_index_view_pager, container, false);
        }
        sharedViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        listView = rootView.findViewById(R.id.lv_musicList);
        ListViewAdapterForIndex adapter = new ListViewAdapterForIndex(mContext, musicList);
        listView.setAdapter(adapter);
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                musicList = DataUtil.getMusicList(mContext);
                adapter.setMusicList(musicList);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        });

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            musicList = DataUtil.getMusicList(mContext);
            adapter.setMusicList(musicList);
            listView.setAdapter(adapter);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_AUDIO);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sharedViewModel.selectMusic(musicList.get(position));
            }
        });
        return rootView;
    }
}