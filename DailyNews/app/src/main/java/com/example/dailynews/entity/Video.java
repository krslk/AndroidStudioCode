package com.example.dailynews.entity;

import java.io.Serializable;

public class Video implements Serializable {
    private int id;
    private String title;
    private String author;
    private String headUrl;
    private String coverUrl;
    private int commentCount;
    private int collectionCount;
    private int likeCount;

    public Video() {
    }

    public Video(String title, String author, int commentCount, int collectionCount, int likeCount) {
        this.title = title;
        this.author = author;
        this.commentCount = commentCount;
        this.collectionCount = collectionCount;
        this.likeCount = likeCount;
    }

    public Video(int id, String title, String author, String headUrl, String coverUrl, int commentCount, int collectionCount, int likeCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.headUrl = headUrl;
        this.coverUrl = coverUrl;
        this.commentCount = commentCount;
        this.collectionCount = collectionCount;
        this.likeCount = likeCount;
    }

    public Video(int id, String title, String author, int commentCount, int collectionCount, int likeCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.commentCount = commentCount;
        this.collectionCount = collectionCount;
        this.likeCount = likeCount;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", commentCount=" + commentCount +
                ", collectionCount=" + collectionCount +
                ", likeCount=" + likeCount +
                '}';
    }
}