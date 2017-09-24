package com.daria.example.wallpaper.wallpaperhd.data.enums;

/**
 * Created by Daria Popova on 23.09.17.
 */

public enum FormatEnum {
    JPEG("jpeg"),
    JPG("jpg"),
    PNG("png");
    private String format;

    public String getFormat() {
        return format;
    }

    FormatEnum(String format) {

        this.format = format;
    }
}
