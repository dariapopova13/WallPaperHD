package com.daria.example.wallpaper.wallpaperhd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.adapters.CategoryAdaper;
import com.daria.example.wallpaper.wallpaperhd.data.Category;
import com.daria.example.wallpaper.wallpaperhd.data.Image;
import com.daria.example.wallpaper.wallpaperhd.data.ImageResponse;
import com.daria.example.wallpaper.wallpaperhd.data.enums.ImageTypeEnum;
import com.daria.example.wallpaper.wallpaperhd.data.enums.OrderEnum;
import com.daria.example.wallpaper.wallpaperhd.data.enums.OrientationEnum;
import com.daria.example.wallpaper.wallpaperhd.network.ApiClient;
import com.daria.example.wallpaper.wallpaperhd.network.ApiInterface;
import com.daria.example.wallpaper.wallpaperhd.utilities.UrlUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daria Popova on 19.09.17.
 */

public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView categoryRecycleView;
    private CategoryAdaper categoryAdaper;
    private List<Category> categories = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        categoryRecycleView = (RecyclerView) view.findViewById(R.id.category_recycleview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.categories_swipe_refresh);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        categoryRecycleView.setLayoutManager(layoutManager);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        initSwipeRefresh();
        return view;
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
        initCategories();
    }

    private void initCategories() {
        String[] titles = getContext().getResources().getStringArray(R.array.categories);
        Arrays.sort(titles);

        for (int i = 0; i < titles.length; i++) {
            Category category = new Category();
            category.setTitle(titles[i]);
            categories.add(category);
            loadImage(category);
        }
        categoryAdaper = new CategoryAdaper(getContext(), categories);
        categoryRecycleView.setAdapter(categoryAdaper);
    }

    private void loadImage(Category category) {
        String uri = new UrlUtils.Builder()
                .addCategory(category.getTitle())
                .addImageType(ImageTypeEnum.PHOTO)
                .addOrder(OrderEnum.POPULAR)
                .addOrientation(OrientationEnum.HORIZONTAL)
                .create(getContext()).getURI().toString();

        Call<ImageResponse> call = apiService.getImages(uri);
        call.enqueue(new CategoryImageCallBack(category));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
//        refreshCategories();
    }

    private void refreshCategories() {
        swipeRefreshLayout.setRefreshing(true);
        for (Category category : categories) {
            loadImage(category);
        }
    }

    private class CategoryImageCallBack implements Callback<ImageResponse> {

        private Category category;

        public CategoryImageCallBack(Category category) {
            this.category = category;
        }

        @Override
        public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
            if (response == null || response.body() == null || response.body().getImages() == null)
                loadImage(category);
            Image image = response.body().getImages().get(0);
            if (image != null) {
                category.setImage(image.getWebformatURL());
                categoryAdaper.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onFailure(Call<ImageResponse> call, Throwable t) {
            t.printStackTrace();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
