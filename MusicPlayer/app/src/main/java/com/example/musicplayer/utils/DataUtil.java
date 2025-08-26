package com.example.musicplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musicplayer.entity.Music;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataUtil {
    public static String getFormatTime() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        return format.format(now);
    }

    public static List<Music> getMusicList(Context mContext) {
        List<Music> list = new ArrayList<>();
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int authorIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int sizeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int pathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int timeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            if (pathIndex == -1) {
                continue;
            }
            Music music = new Music(1, "未知音乐", "");
            if (titleIndex != -1) {
                music.setName(cursor.getString(titleIndex));
            }
            if (authorIndex != -1) {
                music.setAuthor(cursor.getString(authorIndex));
            }
            if (sizeIndex != -1) {
                String s = cursor.getString(sizeIndex);
                //Double d = Double.parseDouble(s) / 1024 / 1024;
                //music.setSize(String.format("%.2f", d) + "MB");
            }
            if (timeIndex != -1) {
                music.setTime(String.valueOf(cursor.getInt(timeIndex) / 1000));
            }
            music.setPath(cursor.getString(pathIndex));
            list.add(music);
        }
        if (cursor != null)
            cursor.close();
        return list;
    }
}
