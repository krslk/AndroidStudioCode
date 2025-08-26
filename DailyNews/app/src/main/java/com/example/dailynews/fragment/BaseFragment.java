package com.example.dailynews.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Looper;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected void saveStringToSp(String key, String val) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("daily_news", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    protected String getStringFromSp(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("daily_news", MODE_PRIVATE);
        return sp.getString(key, "123");
    }

    protected void showToast(String msg) {
        Looper.prepare();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
