package com.example.donthrow.models;

import static java.util.UUID.randomUUID;

import java.util.UUID;

public class Store {

    private String name;
    private String address;
    private String id;
    private float rating;

    public Store(String name, String address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.rating = 0f;
    }

    public String getID() { return id; }
    public String getName() {
        return name;
    }
    public float getRating() { return rating; }
    public void setRating(float rating) {this.rating = rating; }

    public String getAddress() {
        return address;
    }
}
