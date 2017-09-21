package com.daria.example.wallpaper.wallpaperhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.adapters.GridImageAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Daria Popova on 20.09.17.
 */

public class GridImageFragment extends Fragment {

    private RecyclerView gridRecycleView;
    private GridImageAdapter gridImageAdapter;
    private List<String> mockImagesUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_images, container, false);

        gridRecycleView = (RecyclerView) view.findViewById(R.id.grid_image_recycleview);

        loadData(view.getContext());
        gridImageAdapter = new GridImageAdapter(mockImagesUrl, view.getContext());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);

        gridRecycleView.setAdapter(gridImageAdapter);
        gridRecycleView.setLayoutManager(layoutManager);
        return view;
    }


    private void loadData(Context mContext) {
        mockImagesUrl = Arrays.asList(mContext.getResources().getStringArray(R.array.mock_new_images));
    }
}
