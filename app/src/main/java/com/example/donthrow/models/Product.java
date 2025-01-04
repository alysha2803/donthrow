package com.example.donthrow.models;

public class Product {

    private String productId;
    private String name;
    private String category;
    private double price;
    private String condition;
    private String productLocation;
    private String postage;  // Keep postage as a String
    private String image;    // Change image to String to store image URL

    // No-argument constructor (required by Firebase)
    public Product() {
    }

    // Parameterized constructor
    public Product(String productId, String name, String category, double price, String condition, String productLocation, String postage, String image) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.condition = condition;
        this.productLocation = productLocation;
        this.postage = postage;
        this.image = image;
    }

    public Product(String name, double price, String condition, String image, String productId) {
        this.name = name;
        this.price = price;
        this.condition = condition;
        this.image = image;
        this.productId = productId;
    }

    // Getters

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public String getPostage() {
        return postage;
    }

    public String getImage() {
        return image;  // Getter for image URL
    }
}