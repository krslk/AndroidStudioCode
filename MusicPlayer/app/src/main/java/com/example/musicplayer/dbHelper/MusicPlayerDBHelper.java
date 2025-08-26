package com.example.musicplayer.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.musicplayer.entity.User;

public class MusicPlayerDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "music_player.db";
    private static final String TABLE_NAME_USER = "userinfo";
    private static MusicPlayerDBHelper dBHelper = null;
    private static final int DB_VERSION = 1;
    private static SQLiteDatabase sqLiteDatabaseRead = null;
    private static SQLiteDatabase sqLiteDatabaseWrite = null;

    public static MusicPlayerDBHelper getInstance(Context context) {
        if (dBHelper == null) {
            dBHelper = new MusicPlayerDBHelper(context, DB_NAME, null, DB_VERSION);
        }
        return dBHelper;
    }

    private MusicPlayerDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists userinfo\n" +
                "(\n" +
                "    id          integer primary key autoincrement,\n" +
                "    avatar      char(32),\n" +
                "    username    varchar(8),\n" +
                "    account     char(15) not null,\n" +
                "    passwd      char(40) not null,\n" +
                "    sex         char(1) check ( sex in ('0', '1', '2')) default 2,\n" +
                "    salt        char(32),\n" +
                "    email       varchar(50),\n" +
                "    create_time datetime\n" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public User selectUserByAccount(String account) {
        Cursor cursor = sqLiteDatabaseRead.query(TABLE_NAME_USER, null, "account=? ", new String[]{account}, null, null, null);
        User user = new User();
        while (cursor.moveToNext()) {
            user.setId(cursor.getInt(0));
            user.setAvatar(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setAccount(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            user.setSex(cursor.getString(5));
            user.setSalt(cursor.getString(6));
            user.setEmail(cursor.getString(7));
            user.setCreateTime(cursor.getString(8));
        }
        return user;
    }

    public Long insertUserByUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("avatar", user.getAvatar());
        contentValues.put("account", user.getAccount());
        contentValues.put("username", user.getUsername());
        contentValues.put("passwd", user.getPassword());
        contentValues.put("salt", user.getSalt());
        contentValues.put("sex", user.getSex());
        contentValues.put("email", user.getEmail());
        contentValues.put("create_time", user.getCreateTime());
        return sqLiteDatabaseWrite.insert(TABLE_NAME_USER, null, contentValues);
    }

    public int updateUserByUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("avatar", user.getAvatar());
        contentValues.put("account", user.getAccount());
        contentValues.put("username", user.getUsername());
        contentValues.put("passwd", user.getPassword());
        contentValues.put("salt", user.getSalt());
        contentValues.put("sex", user.getSex());
        contentValues.put("email", user.getEmail());
        contentValues.put("create_time", user.getCreateTime());
        return sqLiteDatabaseWrite.update(TABLE_NAME_USER, contentValues, "account=?", new String[]{user.getAccount()});
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
}
