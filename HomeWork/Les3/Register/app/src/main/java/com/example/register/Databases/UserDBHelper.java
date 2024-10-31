package com.example.register.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.register.entity.User;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";
    private static final int DB_VERSION = 1;
    private static UserDBHelper userDBHelper = null;
    private SQLiteDatabase sqLiteDatabaseRead;
    private SQLiteDatabase sqLiteDatabaseWrite;

    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static UserDBHelper getInstance(Context context) {
        if (userDBHelper == null) {
            userDBHelper = new UserDBHelper(context);
        }
        return userDBHelper;
    }

    public SQLiteDatabase openReadLink() {
        if (sqLiteDatabaseRead == null || !sqLiteDatabaseRead.isOpen()) {
            sqLiteDatabaseRead = userDBHelper.getReadableDatabase();
        }
        return sqLiteDatabaseRead;
    }

    public SQLiteDatabase openWriteLink() {
        if (sqLiteDatabaseWrite == null || !sqLiteDatabaseWrite.isOpen()) {
            sqLiteDatabaseWrite = userDBHelper.getWritableDatabase();
        }
        return sqLiteDatabaseWrite;
    }

    public void closeLink() {
        if (sqLiteDatabaseRead != null && sqLiteDatabaseRead.isOpen()) {
            sqLiteDatabaseRead.close();
            sqLiteDatabaseRead = null;
        }
        if (sqLiteDatabaseWrite != null && sqLiteDatabaseWrite.isOpen()) {
            sqLiteDatabaseWrite.close();
            sqLiteDatabaseWrite = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = "CREATE TABLE if not exists user_info\n" +
                "(\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    username    TEXT UNIQUE,\n" +
                "    password    TEXT,\n" +
                "    phone       TEXT,\n" +
                "    status      INTEGER,\n" +
                "    create_time TEXT,\n" +
                "    modify_time TEXT\n" +
                ");";
        db.execSQL(createSql);
        String sql = "create table if not exists remember_login_info\n" +
                "(\n" +
                "    id       INTEGER primary key autoincrement not null,\n" +
                "    username TEXT not null ,\n" +
                "    password TEXT not null\n" +
                ");";
        db.execSQL(sql);
        sql = "insert into user_info\n" +
                "values (null, '2230090210', '123456', '15095574816', 1, '20241029','20241029'),\n" +
                "       (null, 'li_kai', '123456', '15095574816', 1, '20241029','20241029'),\n" +
                "       (null, '李凯', '123456', '15095574816', 1, '20241029','20241029')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 增加数据
     *
     * @param user
     * @return
     */
    public Long insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("phone", user.getPhone());
        contentValues.put("status", user.getStatus());
        contentValues.put("create_time", user.getCreateTime());
        contentValues.put("modify_time", user.getModifyTime());
        return sqLiteDatabaseWrite.insert(TABLE_NAME, null, contentValues);
    }

    /**
     * 删除数据
     *
     * @param username
     * @return
     */
    public int deleteUserByUsername(String username) {
        return sqLiteDatabaseWrite.delete(TABLE_NAME, "name=?", new String[]{username});
    }

    public int updateUserByUsername(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("phone", user.getPhone());
        contentValues.put("status", user.getStatus());
        contentValues.put("create_time", user.getCreateTime());
        contentValues.put("modify_time", user.getModifyTime());
        return sqLiteDatabaseWrite.delete(TABLE_NAME, "name=?", new String[]{user.getUsername()});
    }

    public User selectUserByUsernameAndPassword(User user) {
        Cursor cursor = sqLiteDatabaseRead.query(true, TABLE_NAME, null, "username=? and password = ?", new String[]{user.getUsername(), user.getPassword()}, null, null, null, null, null);
        User resUser = new User();
        while (cursor.moveToNext()) {
            resUser.setId(cursor.getInt(0));
            resUser.setUsername(cursor.getString(1));
            resUser.setPassword(cursor.getString(2));
            resUser.setPhone(cursor.getString(3));
            resUser.setStatus(cursor.getInt(4));
            resUser.setCreateTime(cursor.getString(5));
            resUser.setModifyTime(cursor.getString(6));
        }
        return resUser;
    }

    public User selectRememberInfo() {
        User user = new User();
        Cursor cursor = sqLiteDatabaseRead.query(true, "remember_login_info", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
        }
        return user;
    }

    public void insertRememberInfo(User user) {
        sqLiteDatabaseWrite.delete("remember_login_info", "1==1", null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        sqLiteDatabaseWrite.insert("remember_login_info", null, contentValues);
    }
}
