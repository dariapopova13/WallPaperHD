package com.daria.example.wallpaper.wallpaperhd.data.enums;

/**
 * Created by Daria Popova on 23.09.17.
 */

public enum ImageTypeEnum {

    ALL("all"),
    PHOTO("photo"),
    ILLUSTRATION("illustration"),
    VECTOR("vector");

    private String name;

    public String getName() {
        return name;
    }

    private ImageTypeEnum(String name) {
        this.name = name;
    }
}
