package com.example.dailynews.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dailynews.entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "daily_news.db";
    private static final String NEWS_TABLE = "news";
    private static final int DB_VERSION = 1;
    private static NewsDBHelper newsDBHelper = null;
    private SQLiteDatabase sqLiteDatabaseRead = null;
    private SQLiteDatabase sqLiteDatabaseWrite = null;

    public static NewsDBHelper getInstance(Context mContext) {
        if (newsDBHelper == null) {
            newsDBHelper = new NewsDBHelper(mContext, DB_NAME, null, DB_VERSION);
        }
        return newsDBHelper;
    }

    private NewsDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddlSql = "create table news \n" +
                "(\n" +
                "    id               int auto_increment\n" +
                "        primary key,\n" +
                "    title            varchar(32) not null,\n" +
                "    author_account   char(15)    not null\n" +
                "        references userinfo (id)\n" +
                "            on update cascade,\n" +
                "    content          TEXT,\n" +
                "    type             varchar(16),\n" +
                "    comment_count    INT default 0,\n" +
                "    collection_count INT default 0,\n" +
                "    like_count       INT default 0\n" +
                ");\n" +
                "\n" +
                "create unique index news_author_account_uindex\n" +
                "    on news (author_account)";
        db.execSQL(ddlSql);
        ddlSql = "create table images\n" +
                "(\n" +
                "    id      int auto_increment\n" +
                "        primary key,\n" +
                "    news_id INT         not null\n" +
                "        references news(id)\n" +
                "            on update cascade,\n" +
                "    path    varchar(32) not null\n" +
                ");\n" +
                "\n" +
                "create unique index images_news_id_uindex\n" +
                "    on images (news_id)";
        db.execSQL(ddlSql);
        db.execSQL(SqlString.str1);
    }

    public List<NewsItem> selectNewsItemByType(DBHelper userDBHelper, String type) {
        List<NewsItem> list = new ArrayList<>();
        userDBHelper.openReadLink();
        Cursor cursor = sqLiteDatabaseRead.query(NEWS_TABLE, null, "type=?", new String[]{type}, null, null, null);
        while (cursor.moveToNext()) {
            NewsItem newsItem = new NewsItem();
            newsItem.setId(cursor.getInt(0));
            newsItem.setTitle(cursor.getString(1));
            newsItem.setAuthor(userDBHelper.selectUserByAccount(cursor.getString(2)));
            newsItem.setContent(cursor.getString(3));
            newsItem.setType(cursor.getString(4));
            newsItem.setCommentCount(cursor.getInt(5));
            newsItem.setCommentCount(cursor.getInt(6));
            newsItem.setLikeCount(cursor.getInt(7));
            newsItem.setImgPaths(selectPathById(newsItem.getId()));
            list.add(newsItem);
        }
        return list;
    }

    private List<String> selectPathById(int id) {
        List<String> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabaseRead.query("images", null, "news_id=?", new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(2));
        }
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public SQLiteDatabase openReadLink() {
        if (sqLiteDatabaseRead == null || !sqLiteDatabaseRead.isOpen()) {
            sqLiteDatabaseRead = newsDBHelper.getReadableDatabase();
        }
        return sqLiteDatabaseRead;
    }

    public SQLiteDatabase openWriteLink() {
        if (sqLiteDatabaseWrite == null || !sqLiteDatabaseWrite.isOpen()) {
            sqLiteDatabaseWrite = newsDBHelper.getReadableDatabase();
        }
        return sqLiteDatabaseWrite;
    }

    public void closeAllLink() {
        if (sqLiteDatabaseRead != null && sqLiteDatabaseRead.isOpen()) {
            sqLiteDatabaseRead.close();
            sqLiteDatabaseRead = null;
        }
        if (sqLiteDatabaseWrite != null && sqLiteDatabaseWrite.isOpen()) {
            sqLiteDatabaseWrite.close();
            sqLiteDatabaseWrite = null;
        }
    }
}

