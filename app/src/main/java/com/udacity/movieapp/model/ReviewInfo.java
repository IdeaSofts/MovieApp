package com.udacity.movieapp.model;

public class ReviewInfo{
    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewInfo(
            String posterPath,
            String author,
            String content,
            String url
    ) {
        this.id = posterPath;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    /** GETTERS */

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

}