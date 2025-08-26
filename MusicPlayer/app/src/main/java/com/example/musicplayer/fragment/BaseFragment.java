package com.example.musicplayer.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    protected void saveToSp(String key, String value) {
        SharedPreferences sp = getActivity().getSharedPreferences("music_player", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected String findFromSP(String key) {
        return getActivity().getSharedPreferences("music_player", MODE_PRIVATE).getString(key, "");
    }
}