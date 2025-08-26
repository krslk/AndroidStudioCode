package com.example.dailynews.Api;

public class PathApi {
    private static final String rootPath = "/data/user/0/com.example.dailynews/files";

    public static String getPath(String path) {
        return rootPath + path;
    }
}
