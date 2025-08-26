package com.example.dailynews.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NewsItem implements Parcelable {
    private int id;
    private String title;
    private Userinfo author;
    private String content;
    private String type;
    private List<String> imgPaths = new ArrayList<>();
    private int commentCount;
    private int collectionCount;
    private int likeCount;

    public NewsItem() {
    }

    protected NewsItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readParcelable(Userinfo.class.getClassLoader());
        content = in.readString();
        imgPaths = in.createStringArrayList();
        commentCount = in.readInt();
        collectionCount = in.readInt();
        likeCount = in.readInt();
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };

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

    public Userinfo getAuthor() {
        return author;
    }

    public void setAuthor(Userinfo author) {
        this.author = author;
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
        return "NewsItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", content='" + content + '\'' +
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
        dest.writeParcelable(author, flags);
        dest.writeString(content);
        dest.writeStringList(imgPaths);
        dest.writeInt(commentCount);
        dest.writeInt(collectionCount);
        dest.writeInt(likeCount);
    }
}
