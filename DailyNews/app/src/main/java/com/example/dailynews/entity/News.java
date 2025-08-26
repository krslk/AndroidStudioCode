package com.example.dailynews.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class News implements Parcelable {
    private int id;
    private String title;
    private String authorAccount;
    private String avatarPath;
    private String content;
    private String type;
    private List<String> imgPaths = new ArrayList<>();
    private int commentCount;
    private int collectionCount;
    private int likeCount;

    public News() {
    }

    public News(String title, String authorAccount, String content, List<String> imgPaths) {
        this.title = title;
        this.authorAccount = authorAccount;
        this.content = content;
        this.imgPaths = imgPaths;
    }

    public News(String title, String authorAccount, String content, List<String> imgPaths, int commentCount, int collectionCount, int likeCount) {
        this.title = title;
        this.authorAccount = authorAccount;
        this.content = content;
        this.imgPaths = imgPaths;
        this.commentCount = commentCount;
        this.collectionCount = collectionCount;
        this.likeCount = likeCount;
    }

    protected News(Parcel in) {
        id = in.readInt();
        title = in.readString();
        authorAccount = in.readString();
        content = in.readString();
        imgPaths = in.createStringArrayList();
        commentCount = in.readInt();
        collectionCount = in.readInt();
        likeCount = in.readInt();
        type = in.readString();
        avatarPath = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorAccount() {
        return authorAccount;
    }

    public void setAuthorAccount(String authorAccount) {
        this.authorAccount = authorAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(List<String> imgPaths) {
        this.imgPaths = imgPaths;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorAccount='" + authorAccount + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", imgPaths=" + imgPaths +
                ", commentCount=" + commentCount +
                ", collectionCount=" + collectionCount +
                ", likeCount=" + likeCount +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(authorAccount);
        dest.writeString(content);
        dest.writeStringList(imgPaths);
        dest.writeInt(commentCount);
        dest.writeInt(collectionCount);
        dest.writeInt(likeCount);
        dest.writeString(type);
        dest.writeString(avatarPath);
    }
}
