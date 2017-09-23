package com.daria.example.wallpaper.wallpaperhd.data;

/**
 * Created by Daria Popova on 19.09.17.
 */

public class Category {

    private String image;
    private String title;

    public Category(String image, String title) {
        this.image = image;
        this.title = title;
    }

    public Category() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
