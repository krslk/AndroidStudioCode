package com.example.dailynews.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Userinfo implements Parcelable {
    protected int id;
    protected String account;
    protected String username;
    protected String avatarPath;
    protected String password;
    protected String salt;
    protected String mail;
    protected String createTime;
    protected String modifyTime;

    public Userinfo() {
    }

    public Userinfo(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public Userinfo(String account, String password, String mail) {
        this.account = account;
        this.password = password;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    protected Userinfo(Parcel in) {
        id = in.readInt();
        account = in.readString();
        password = in.readString();
        avatarPath = in.readString();
        username = in.readString();
        mail = in.readString();
        salt = in.readString();
        createTime = in.readString();
        modifyTime = in.readString();
    }

    @Override
    public String toString() {
        return "Userinfo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", mail='" + mail + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }

    public static final Creator<Userinfo> CREATOR = new Creator<Userinfo>() {
        @Override
        public Userinfo createFromParcel(Parcel in) {
            return new Userinfo(in);
        }

        @Override
        public Userinfo[] newArray(int size) {
            return new Userinfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(account);
        dest.writeString(password);
        dest.writeString(avatarPath);
        dest.writeString(username);
        dest.writeString(mail);
        dest.writeString(salt);
        dest.writeString(createTime);
        dest.writeString(modifyTime);
    }
}
