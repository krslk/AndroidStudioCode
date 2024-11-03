package com.example.fragmentandviewpage;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {
    private final List<String> titles = new ArrayList<>();
    private final List<String> colors = new ArrayList<>();

    public ViewPagerAdapter() {
        titles.add("Hello i am 1");
        titles.add("Hello i am 2");
        titles.add("Hello i am 3");
        titles.add("Hello i am 4");
        titles.add("Hello i am 5");
        titles.add("Hello i am 6");
        titles.add("Hello i am 7");
        titles.add("Hello i am 8");
        titles.add("Hello i am 9");
        titles.add("Hello i am 10");
        colors.add(String.valueOf(R.color.white));
        colors.add(String.valueOf(R.color.blue));
        colors.add(String.valueOf(R.color.green));
        colors.add(String.valueOf(R.color.red));
        colors.add(String.valueOf(R.color.yellow));
        colors.add(String.valueOf(R.color.green));
        colors.add(String.valueOf(R.color.red));
        colors.add(String.valueOf(R.color.white));
        colors.add(String.valueOf(R.color.blue));
        colors.add(String.valueOf(R.color.yellow));
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPagerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        holder.mTv.setText(titles.get(position));
        holder.mContainer.setBackgroundResource(Integer.parseInt(colors.get(position)));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        TextView mTv;
        RelativeLayout mContainer;

        @RequiresApi(api = Build.VERSION_CODES.P) // Alt+Enter
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            mContainer = itemView.requireViewById(R.id.container);
            mTv = itemView.findViewById(R.id.tvTitle);
        }
    }
}
