package com.example.datvexe.domain.model;

public class News {
    private int imageResId;
    private String title;
    private String description;
    private String date;

    public News(int imageResId, String title, String description, String date) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public int getImageResId() {
        return imageResId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
} 