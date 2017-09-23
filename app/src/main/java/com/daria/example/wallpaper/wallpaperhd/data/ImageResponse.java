package com.daria.example.wallpaper.wallpaperhd.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Daria Popova on 22.09.17.
 */
public class ImageResponse {

    private int total;
    private int totalHits;
    @SerializedName("hits")
    private List<Image> images;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
