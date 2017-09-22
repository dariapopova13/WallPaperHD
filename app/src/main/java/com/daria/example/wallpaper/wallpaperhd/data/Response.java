package com.daria.example.wallpaper.wallpaperhd.data;

import java.util.List;

/**
 * Created by Daria Popova on 22.09.17.
 */

public class Response {

    private int total;
    private int totalHits;
    private List<Hit> hits;

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

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
}
