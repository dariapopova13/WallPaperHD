package com.daria.example.wallpaper.wallpaperhd.network;

import com.daria.example.wallpaper.wallpaperhd.data.ImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Daria Popova on 19.09.17.
 */

public interface ApiInterface {

    @GET
    Call<ImageResponse> getImages(@Url String url);

    @GET
    Call<ImageResponse> getPerPageImages(@Query("per_page") String perPage);
}
