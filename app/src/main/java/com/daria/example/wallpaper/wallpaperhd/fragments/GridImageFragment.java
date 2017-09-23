package com.daria.example.wallpaper.wallpaperhd.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.adapters.GridImageAdapter;
import com.daria.example.wallpaper.wallpaperhd.data.Image;
import com.daria.example.wallpaper.wallpaperhd.data.ImageResponse;
import com.daria.example.wallpaper.wallpaperhd.network.ApiClient;
import com.daria.example.wallpaper.wallpaperhd.network.ApiInterface;
import com.daria.example.wallpaper.wallpaperhd.utilities.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daria Popova on 20.09.17.
 */

public class GridImageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<Image> images;
    private RecyclerView gridRecycleView;
    private GridImageAdapter gridImageAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;
    private String currentUri;

    protected GridImageFragment() {

    }

    public GridImageFragment(String currentUri) {
        this.currentUri = currentUri;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getImages();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_images, container, false);

        gridRecycleView = (RecyclerView) view.findViewById(R.id.grid_image_recycleview);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);

        gridRecycleView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.grid_images_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getImages();
        swipeRefreshLayout.setRefreshing(false);
    }

    protected void getImages() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ImageResponse> call = apiService.getImages(currentUri);
        call.enqueue(new ImageCallback());
    }

    protected class ImageCallback implements Callback<ImageResponse> {

        public ImageCallback() {

        }

        @Override
        public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
            if (images == null)
                images = new ArrayList<>(response.body().getImages().size());
            else images.clear();
            for (Image image : response.body().getImages()) {
                images.add(image);
            }
            gridImageAdapter = new GridImageAdapter(images, getContext());
            gridRecycleView.setAdapter(gridImageAdapter);
        }

        @Override
        public void onFailure(Call<ImageResponse> call, Throwable t) {
            t.printStackTrace();
        }
    }

}
