package com.example.musicplayer.entity;

public class Music {
    private int id;
    private String name;
    private String author;
    private String size;
    private String time;
    private String path;

    public Music() {
    }

    public Music(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Music(int id, String name, String author, String size, String time, String path) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.size = size;
        this.time = time;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", size='" + size + '\'' +
                ", time='" + time + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
