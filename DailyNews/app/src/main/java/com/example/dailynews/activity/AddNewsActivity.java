package com.example.dailynews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.dailynews.R;
import com.example.dailynews.dbhelper.DBHelper;
import com.example.dailynews.entity.News;
import com.example.dailynews.entity.NewsItem;
import com.example.dailynews.entity.Userinfo;
import com.example.dailynews.util.StringUtils;

public class AddNewsActivity extends BaseActivity {
    private EditText et_title, et_content, et_type;
    private DBHelper dbHelper;
    private Userinfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        dbHelper = DBHelper.getInstance(this);
        dbHelper.openReadLink();
        dbHelper.openWriteLink();
        et_title = findViewById(R.id.et_addNews_title);
        et_content = findViewById(R.id.et_addNews_content);
        et_type = findViewById(R.id.et_addNews_type);
        userinfo = dbHelper.selectUserByAccount(selectStringFromSp("current"));
    }

    public void toAddNews(View view) {
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();
        String type = et_type.getText().toString();
        if (StringUtils.isEmpty(title)) {
            showToast("标题不能为空");
            return;
        }
        NewsItem n = new NewsItem();
        n.setTitle(title);
        n.setContent(content);
        n.setType(StringUtils.isEmpty(type) ? "其它" : type);
        n.setAuthor(userinfo);
        n.setImgPaths(null);
        dbHelper.insertNewsByNewsItem(n);
        showToast("发布成功");
        finish();
    }

    public void addVideo(View view) {
        showToast("该功能正在开发中...");
    }

    public void addPhoto(View view) {
        showToast("该功能正在开发中...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeAllLink();
    }
}