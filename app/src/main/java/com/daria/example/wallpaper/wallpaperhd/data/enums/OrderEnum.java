package com.daria.example.wallpaper.wallpaperhd.data.enums;

/**
 * Created by Daria Popova on 23.09.17.
 */

public enum OrderEnum {

    LATEST("latest"),
    POPULAR("popular");

    private String name;

    public String getName() {
        return name;
    }

    private OrderEnum(String name) {
        this.name = name;
    }
}
