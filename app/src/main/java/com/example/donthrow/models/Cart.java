package com.example.donthrow.models;

public class Cart {

    private String productId;
    private String name;
    private double price;
    private String condition;
    private String image;    // Change image to String to store image URL

    private int quantity;

    // No-argument constructor (required by Firebase)
    public Cart() {
    }

    // Parameterized constructor
    public Cart(String productId, String name, double price, String condition, String image, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.condition = condition;
        this.image = image;
        this.quantity = quantity;
    }

    // Getters

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public String getImage() {
        return image;  // Getter for image URL
    }

    public int getQuantity() { return quantity;}
}