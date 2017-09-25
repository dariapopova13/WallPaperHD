package com.daria.example.wallpaper.wallpaperhd.data.enums;

/**
 * Created by Daria Popova on 24.09.17.
 */

public enum ImageMimeTypeEnum {

    GIF("image/gif"),
    JPEG("image/jpeg"),
    PNG("image/png");

    private String type;

    public String getType() {
        return type;
    }

    private ImageMimeTypeEnum(String type) {

        this.type = type;
    }
}
