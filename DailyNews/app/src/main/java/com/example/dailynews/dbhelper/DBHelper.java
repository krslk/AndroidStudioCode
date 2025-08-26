package com.example.dailynews.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dailynews.entity.NewsItem;
import com.example.dailynews.entity.Userinfo;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "daily_news.db";
    private static final String USER_TABLE = "userinfo";
    private static final String NEWS_TABLE = "news";
    private static final String IMG_PATH_TABLE = "images";
    private static final int DB_VERSION = 1;
    private static DBHelper dBHelper = null;
    private SQLiteDatabase sqLiteDatabaseRead;
    private SQLiteDatabase sqLiteDatabaseWrite;

    public static DBHelper getInstance(Context mContext) {
        if (dBHelper == null) {
            dBHelper = new DBHelper(mContext, DB_NAME, null, DB_VERSION);
        }
        return dBHelper;
    }

    private DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserinfoSql = "create table if not exists userinfo\n" +
                "(\n" +
                "    id          int auto_increment,\n" +
                "    account     varchar(15) not null,\n" +
                "    username    char(15)    null,\n" +
                "    avatar_path char(255)   null,\n" +
                "    password    varchar(15) not null,\n" +
                "    salt        char(50)    null,\n" +
                "    mail        char(32)    null,\n" +
                "    modify_time datetime    null,\n" +
                "    create_time datetime    null,\n" +
                "    constraint userinfo_account_uindex\n" +
                "        unique (account),\n" +
                "    constraint userinfo_id_uindex\n" +
                "        unique (id)\n" +
                ")";
        db.execSQL(createUserinfoSql);
        String defaultInfoSql = "INSERT INTO userinfo (id, account, username, avatar_path, password, salt, mail,modify_time, create_time) VALUES (0, '123', '用户123', '/img/微信头像.jpg', '760FA7606F90C6D2D8E9BD97E8741A44', 'FC2BF653-E59C-41F8-B8E3-2CBD4DF9B18D','lj531967491@163.com' ,null, '2024-12-05 23:53:32')";
        db.execSQL(defaultInfoSql);
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Userinfo selectUserByAccount(String account) {
        Cursor cursor = sqLiteDatabaseRead.query(true, USER_TABLE, null, "account=?", new String[]{account}, null, null, null, null, null);
        Userinfo resUser = new Userinfo();
        while (cursor.moveToNext()) {
            resUser.setId(cursor.getInt(0));
            resUser.setAccount(cursor.getString(1));
            resUser.setUsername(cursor.getString(2));
            resUser.setAvatarPath(cursor.getString(3));
            resUser.setPassword(cursor.getString(4));
            resUser.setSalt(cursor.getString(5));
            resUser.setMail(cursor.getString(6));
            resUser.setModifyTime(cursor.getString(7));
            resUser.setCreateTime(cursor.getString(8));
        }
        return resUser;
    }

    public boolean selectCountByAccount(String account) {
        Cursor cursor = sqLiteDatabaseRead.query(true, USER_TABLE, null, "account=?", new String[]{account}, null, null, null, null, null);
        return cursor.moveToNext();
    }

    public boolean selectCountByMail(String account) {
        Cursor cursor = sqLiteDatabaseRead.query(true, USER_TABLE, null, "mail=?", new String[]{account}, null, null, null, null, null);
        return cursor.moveToNext();
    }

    public Integer updataInfoByUserinfo(Userinfo userinfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", userinfo.getAccount());
        contentValues.put("username", userinfo.getUsername());
        contentValues.put("avatar_path", userinfo.getAvatarPath());
        contentValues.put("password", userinfo.getPassword());
        contentValues.put("salt", userinfo.getSalt());
        contentValues.put("mail", userinfo.getMail());
        contentValues.put("create_time", userinfo.getCreateTime());
        contentValues.put("modify_time", userinfo.getModifyTime());
        return sqLiteDatabaseWrite.update(USER_TABLE, contentValues, "account=?", new String[]{userinfo.getAccount()});
    }

    public Long insertUserinfo(Userinfo userinfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", userinfo.getAccount());
        contentValues.put("username", userinfo.getUsername());
        contentValues.put("avatar_path", userinfo.getAvatarPath());
        contentValues.put("password", userinfo.getPassword());
        contentValues.put("salt", userinfo.getSalt());
        contentValues.put("mail", userinfo.getMail());
        contentValues.put("create_time", userinfo.getCreateTime());
        contentValues.put("modify_time", userinfo.getModifyTime());
        return sqLiteDatabaseWrite.insert(USER_TABLE, null, contentValues);
    }

    public SQLiteDatabase openReadLink() {
        if (sqLiteDatabaseRead == null || !sqLiteDatabaseRead.isOpen()) {
            sqLiteDatabaseRead = dBHelper.getReadableDatabase();
        }
        return sqLiteDatabaseRead;
    }

    public SQLiteDatabase openWriteLink() {
        if (sqLiteDatabaseWrite == null || !sqLiteDatabaseWrite.isOpen()) {
            sqLiteDatabaseWrite = dBHelper.getReadableDatabase();
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

    public void insertNewsByNewsItem(NewsItem newsItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", newsItem.getTitle());
        contentValues.put("author_account", newsItem.getAuthor().getAccount());
        contentValues.put("content", newsItem.getContent());
        contentValues.put("type", newsItem.getType());
        contentValues.put("comment_count", 0);
        contentValues.put("collection_count", 0);
        contentValues.put("like_count", 0);
        sqLiteDatabaseWrite.insert(NEWS_TABLE, null, contentValues);
    }

    public List<NewsItem> selectNewsItemByType(String type) {
        List<NewsItem> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabaseRead.query(NEWS_TABLE, null, "type=?", new String[]{type}, null, null, null);
        while (cursor.moveToNext()) {
            NewsItem newsItem = new NewsItem();
            newsItem.setId(cursor.getInt(0));
            newsItem.setTitle(cursor.getString(1));
            newsItem.setAuthor(selectUserByAccount(cursor.getString(2)));
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


}
