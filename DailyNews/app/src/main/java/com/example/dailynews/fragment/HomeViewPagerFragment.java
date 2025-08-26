package com.example.dailynews.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dailynews.R;
import com.example.dailynews.adapter.ViewPagerAdapterForHome;
import com.example.dailynews.entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class HomeViewPagerFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private List<Fragment> fragmentList;
    private ViewPagerAdapterForHome viewPagerAdapter;
    private HomePage homePage = new HomePage();
    private ViewPager2 viewPager2;
    private List<NewsItem> itemList;

    public static HomeViewPagerFragment newInstance() {
        HomeViewPagerFragment fragment = new HomeViewPagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_view_pager, container, false);
        }
        viewPager2 = rootView.findViewById(R.id.vp_home);
        initView();
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeCenterFragment.newInstance("关注"));
        fragmentList.add(HomeCenterFragment.newInstance("推荐"));
        fragmentList.add(HomeCenterFragment.newInstance("新闻"));
        fragmentList.add(HomeCenterFragment.newInstance("游戏"));
        fragmentList.add(HomeCenterFragment.newInstance("娱乐"));
        fragmentList.add(HomeCenterFragment.newInstance("其它"));
        viewPagerAdapter = new ViewPagerAdapterForHome(getChildFragmentManager(), getLifecycle(), fragmentList);
        viewPager2.setOffscreenPageLimit(fragmentList.size());
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changerTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        return rootView;
    }

    private void initView() {
        homePage.img_search = rootView.findViewById(R.id.img_home_search);
        homePage.et_search = rootView.findViewById(R.id.et_home_search);
        homePage.ll_concerns = rootView.findViewById(R.id.ll_home_topBar_concerns);
        homePage.tv_concerns = rootView.findViewById(R.id.tv_home_topBar_concerns);
        homePage.line_concerns = rootView.findViewById(R.id.line_home_topBar_concerns);
        homePage.ll_recommendations = rootView.findViewById(R.id.ll_home_topBar_recommendations);
        homePage.tv_recommendations = rootView.findViewById(R.id.tv_home_topBar_recommendations);
        homePage.line_recommendations = rootView.findViewById(R.id.line_home_topBar_recommendations);
        homePage.ll_news = rootView.findViewById(R.id.ll_home_topBar_news);
        homePage.tv_news = rootView.findViewById(R.id.tv_home_topBar_news);
        homePage.line_news = rootView.findViewById(R.id.line_home_topBar_news);
        homePage.ll_games = rootView.findViewById(R.id.ll_home_topBar_games);
        homePage.tv_games = rootView.findViewById(R.id.tv_home_topBar_games);
        homePage.line_games = rootView.findViewById(R.id.line_home_topBar_games);
        homePage.ll_entertainments = rootView.findViewById(R.id.ll_home_topBar_entertainments);
        homePage.tv_entertainments = rootView.findViewById(R.id.tv_home_topBar_entertainments);
        homePage.line_entertainments = rootView.findViewById(R.id.line_home_topBar_entertainments);
        homePage.ll_other = rootView.findViewById(R.id.ll_home_topBar_others);
        homePage.tv_other = rootView.findViewById(R.id.tv_home_topBar_others);
        homePage.line_other = rootView.findViewById(R.id.line_home_topBar_others);
        homePage.ll_concerns.setOnClickListener(this);
        homePage.ll_entertainments.setOnClickListener(this);
        homePage.ll_recommendations.setOnClickListener(this);
        homePage.ll_news.setOnClickListener(this);
        homePage.ll_games.setOnClickListener(this);
        homePage.ll_other.setOnClickListener(this);
        homePage.tv_current = homePage.tv_concerns;
        homePage.line_current = homePage.line_concerns;
        homePage.tv_current.setTextColor(getContext().getColor(R.color.orange));
        homePage.line_current.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_home_topBar_concerns) {
            changerTab(0);
        } else if (v.getId() == R.id.ll_home_topBar_recommendations) {
            changerTab(1);
        } else if (v.getId() == R.id.ll_home_topBar_news) {
            changerTab(2);
        } else if (v.getId() == R.id.ll_home_topBar_games) {
            changerTab(3);
        } else if (v.getId() == R.id.ll_home_topBar_entertainments) {
            changerTab(4);
        } else if (v.getId() == R.id.ll_home_topBar_others) {
            changerTab(5);
        }
    }

    void changerTab(int position) {
        homePage.tv_current.setTextColor(getContext().getColor(R.color.white));
        homePage.line_current.setSelected(false);
        switch (position) {
            case 0:
                homePage.tv_current = homePage.tv_concerns;
                homePage.line_current = homePage.line_concerns;
                break;
            case 1:
                homePage.tv_current = homePage.tv_recommendations;
                homePage.line_current = homePage.line_recommendations;
                break;
            case 2:
                homePage.tv_current = homePage.tv_news;
                homePage.line_current = homePage.line_news;
                break;
            case 3:
                homePage.tv_current = homePage.tv_games;
                homePage.line_current = homePage.line_games;
                break;
            case 4:
                homePage.tv_current = homePage.tv_entertainments;
                homePage.line_current = homePage.line_entertainments;
                break;
            case 5:
                homePage.tv_current = homePage.tv_other;
                homePage.line_current = homePage.line_other;
                break;
        }
        homePage.tv_current.setTextColor(getContext().getColor(R.color.orange));
        homePage.line_current.setSelected(true);
        viewPager2.setCurrentItem(position);
    }

    private static class HomePage {
        View root;
        private EditText et_search;
        private ImageView img_search;
        private TextView tv_concerns, tv_recommendations, tv_news, tv_games, tv_entertainments, tv_other, tv_current;
        private View line_concerns, line_recommendations, line_news, line_games, line_entertainments, line_other, line_current;
        private LinearLayout ll_concerns, ll_recommendations, ll_news, ll_games, ll_entertainments, ll_other;
    }
}