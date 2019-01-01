package com.example.zayn.sampleapp;

public class Item {

    String mTitle;
    String mDescription;

    public Item(String title, String description){
        this.mTitle = title;
        this.mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
