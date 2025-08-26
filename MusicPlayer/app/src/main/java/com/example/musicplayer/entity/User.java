package com.example.musicplayer.entity;

public class User {
    private int id;
    private String avatar;
    private String username;
    private String account;
    private String password;
    private String sex;
    private String salt;
    private String email;
    private String createTime;

    public User() {
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public User(String account, String password, String createTime) {
        this.account = account;
        this.password = password;
        this.createTime = createTime;
    }

    public User(String account, String password, String salt, String createTime) {
        this.account = account;
        this.password = password;
        this.salt = salt;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", avatar=" + avatar +
                ", username='" + username + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
