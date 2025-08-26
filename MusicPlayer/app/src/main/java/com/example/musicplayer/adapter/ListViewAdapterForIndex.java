package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.entity.Music;

import java.util.List;

public class ListViewAdapterForIndex extends BaseAdapter {
    private List<Music> musicList;
    private Context mContext;

    public ListViewAdapterForIndex(Context mContext, List<Music> musicList) {
        this.musicList = musicList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_music_list, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.item_music_name);
            viewHolder.author = convertView.findViewById(R.id.item_music_author);
            viewHolder.id = convertView.findViewById(R.id.item_music_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(musicList.get(position).getName());
        viewHolder.author.setText(musicList.get(position).getAuthor());
        viewHolder.id.setText(String.valueOf(position + 1));
        return convertView;
    }

    public static final class ViewHolder {
        public TextView name, author, id;
    }

}
