package com.example.dailynews.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class VideoDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "daily_news.db";
    private static final String TABLE_NAME = "video";
    private static final int DB_VERSION = 1;
    private static VideoDBHelper videoDBHelper = null;
    private SQLiteDatabase sqLiteDatabaseRead;
    private SQLiteDatabase sqLiteDatabaseWrite;

    public static VideoDBHelper getInstance(Context mContext) {
        if (videoDBHelper == null) {
            videoDBHelper = new VideoDBHelper(mContext, DB_NAME, null, DB_VERSION);
        }
        return videoDBHelper;
    }

    private VideoDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddlSql = "";
        db.execSQL(ddlSql);
        String defaultValue = "";
        db.execSQL(defaultValue);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




    public SQLiteDatabase openReadLink() {
        if (sqLiteDatabaseRead == null || !sqLiteDatabaseRead.isOpen()) {
            sqLiteDatabaseRead = videoDBHelper.getReadableDatabase();
        }
        return sqLiteDatabaseRead;
    }

    public SQLiteDatabase openWriteLink() {
        if (sqLiteDatabaseWrite == null || !sqLiteDatabaseWrite.isOpen()) {
            sqLiteDatabaseWrite = videoDBHelper.getReadableDatabase();
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
