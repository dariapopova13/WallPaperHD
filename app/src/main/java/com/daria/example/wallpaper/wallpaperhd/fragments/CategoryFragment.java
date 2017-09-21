package com.daria.example.wallpaper.wallpaperhd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.adapters.CategoryAdaper;

/**
 * Created by Daria Popova on 19.09.17.
 */

public class CategoryFragment extends Fragment {

    private RecyclerView categoryRecycleView;
    private CategoryAdaper categoryAdaper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        categoryRecycleView = view.findViewById(R.id.category_recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        categoryAdaper = new CategoryAdaper(view.getContext());
        categoryRecycleView.setAdapter(categoryAdaper);
        categoryRecycleView.setLayoutManager(layoutManager);

        return view;
    }
}
