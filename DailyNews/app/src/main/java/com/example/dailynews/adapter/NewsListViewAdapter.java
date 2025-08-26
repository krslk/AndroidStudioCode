package com.example.dailynews.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dailynews.Api.PathApi;
import com.example.dailynews.R;
import com.example.dailynews.entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsListViewAdapter extends BaseAdapter {
    private final Context mContext;
    List<NewsItem> newsList = new ArrayList<>();

    public NewsListViewAdapter(Context mContext, List<NewsItem> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_news_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.initView(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.img_user.setImageBitmap(BitmapFactory.decodeFile(PathApi.getPath(newsList.get(position).getAuthor().getAvatarPath())));
        viewHolder.tv_author.setText(newsList.get(position).getAuthor().getUsername());
        viewHolder.tv_title.setText(newsList.get(position).getTitle());
        viewHolder.textView.setText(newsList.get(position).getContent());
        viewHolder.tv_commentCount.setText(String.valueOf(newsList.get(position).getCommentCount()));
        viewHolder.tv_collectionCount.setText(String.valueOf(newsList.get(position).getCollectionCount()));
        viewHolder.tv_likeCount.setText(String.valueOf(newsList.get(position).getLikeCount()));
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_title, tv_author, tv_likeCount, tv_commentCount, tv_collectionCount;
        ImageView img_user;
        TextView textView;

        public void initView(View itemView) {
            textView = itemView.findViewById(R.id.img_item_news_content);
            img_user = itemView.findViewById(R.id.img_item_news_user_img);
            tv_author = itemView.findViewById(R.id.tv_item_news_author);
            tv_title = itemView.findViewById(R.id.tv_item_news_title);
            tv_collectionCount = itemView.findViewById(R.id.tv_item_news_collect);
            tv_likeCount = itemView.findViewById(R.id.tv_item_news_like);
            tv_commentCount = itemView.findViewById(R.id.tv_item_news_comments);
        }
    }
}
