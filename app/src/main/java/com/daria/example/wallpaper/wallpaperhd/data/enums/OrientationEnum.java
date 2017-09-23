package com.daria.example.wallpaper.wallpaperhd.data.enums;

/**
 * Created by Daria Popova on 23.09.17.
 */

public enum OrientationEnum {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    ALL("all");

    private String name;

    public String getName() {
        return name;
    }

    private OrientationEnum(String name) {
        this.name = name;
    }
}
