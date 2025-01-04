package com.example.donthrow.models;

public class Article {

    private String articleId;
    private String title;
    private String description;
    private String imageUrl;

    // Default constructor required for Firebase
    public Article() {}

    // Constructor with parameters
    public Article(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Article(String title, String description, String imageUrl, String articleId) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.articleId = articleId;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticleId() {
        return articleId;
    }
}
