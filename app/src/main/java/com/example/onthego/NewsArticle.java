package com.example.onthego;
public class NewsArticle {
    private String title;
    private String description;
    private String link;
    private String imageLink;

    public NewsArticle(String title, String description, String link, String imageLink) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getImageLink() {
        return imageLink;
    }
}
