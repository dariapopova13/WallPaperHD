package com.daria.example.wallpaper.wallpaperhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.activities.FullImageActivity;
import com.daria.example.wallpaper.wallpaperhd.adapters.GridImageAdapter;
import com.daria.example.wallpaper.wallpaperhd.adapters.TagsAdapter;
import com.daria.example.wallpaper.wallpaperhd.data.Image;
import com.daria.example.wallpaper.wallpaperhd.data.ImageResponse;
import com.daria.example.wallpaper.wallpaperhd.data.enums.ImageTypeEnum;
import com.daria.example.wallpaper.wallpaperhd.data.enums.OrderEnum;
import com.daria.example.wallpaper.wallpaperhd.network.ApiClient;
import com.daria.example.wallpaper.wallpaperhd.network.ApiInterface;
import com.daria.example.wallpaper.wallpaperhd.utilities.AppUtils;
import com.daria.example.wallpaper.wallpaperhd.utilities.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daria Popova on 22.09.17.
 */
@SuppressWarnings("all")
public class ImageFragment extends Fragment implements View.OnClickListener {

    private Image image;
    private ImageView imageView;
    private RecyclerView tagsRecycleView;
    private TagsAdapter tagsAdapter;
    private RecyclerView similarImagesRecycleView;
    private GridImageAdapter similarImagesAdapter;
    private GridImageAdapter gridImageAdapter;
    private Context mContext;
    private FloatingActionButton fab;
    private int num;

    public ImageFragment(Image image, Context mContext) {
        this.mContext = mContext;
        this.image = image;
    }

    public static ImageFragment newInstance(int num, Context mContext, Image image) {
        Bundle args = new Bundle();
        args.putInt("num", num);
        ImageFragment fragment = new ImageFragment(image, mContext);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.image_save_fab: {
                Drawable.ConstantState current = fab.getDrawable().getConstantState();
                if (current.equals(mContext.getDrawable(R.drawable.ic_arrow_downward_white_24dp).getConstantState())) {
                    saveImage();
                } else if (current.equals(mContext.getDrawable(R.drawable.ic_get_app_white_24dp).getConstantState())) {
                    applyImage();
                }
                break;
            }
            case R.id.single_image: {
                Intent fullImageIntent = new Intent(getContext(), FullImageActivity.class);
                fullImageIntent.putExtra("image", image.getWebformatURL());
                startActivity(fullImageIntent);
            }
        }
    }

    private void applyImage() {
        fab.setImageDrawable(mContext.getDrawable(R.drawable.ic_check_white_24dp));
        fab.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.colorCheckFab));
    }

    private void saveImage() {
        fab.setImageDrawable(mContext.getDrawable(R.drawable.ic_get_app_white_24dp));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        view.setBackgroundColor(AppUtils.getRandomColorId(mContext));
        initViews(view);
        createRecycleView();
        loadImage();
        createFabCircle();
        return view;
    }

    private void createFabCircle() {
        fab.setOnClickListener(this);
        getFabCurrentState();
    }

    private void getFabCurrentState() {
//        if (MockUtils.isDownloaded(imageUrl))
//            fab.setImageDrawable(mContext.getDrawable(R.drawable.ic_get_app_white_24dp));
    }

    private void initViews(View view) {
        tagsRecycleView = (RecyclerView) view.findViewById(R.id.image_tags_recycle_view);
        similarImagesRecycleView = (RecyclerView) view.findViewById(R.id.similar_images_recycle_view);
        imageView = (ImageView) view.findViewById(R.id.single_image);
        imageView.setOnClickListener(this);
        fab = (FloatingActionButton) view.findViewById(R.id.image_save_fab);
    }

    private void createRecycleView() {
        tagsAdapter = new TagsAdapter(mContext, image.getTags());
        RecyclerView.LayoutManager tagLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);

        tagsRecycleView.setAdapter(tagsAdapter);
        tagsRecycleView.setLayoutManager(tagLayoutManager);

        RecyclerView.LayoutManager similarImagesLayoutManager = new GridLayoutManager(mContext, 2);

        similarImagesRecycleView.setLayoutManager(similarImagesLayoutManager);
        similarImagesRecycleView.setNestedScrollingEnabled(false);
        loadSimilarImages();
    }

    private void loadImage() {
        if (image.getWebformatURL() == null || TextUtils.isEmpty(image.getWebformatURL())) return;
        Glide.with(this).load(image.getWebformatURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(1f)
                .into(imageView);
    }

    private void loadSimilarImages() {

        String uri = new UrlUtils.Builder()
                .addImageType(ImageTypeEnum.PHOTO)
                .addQuery(AppUtils.createQuery(image.getTags()))
                .addPerPage(20)
                .addOrder(OrderEnum.POPULAR)
                .create(mContext).getURI().toString();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ImageResponse> call = apiService.getImages(uri);
        call.enqueue(new Callback<ImageResponse>() {

            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response == null || response.body() == null || response.body().getImages() == null)
                    return;
                List<Image> similarImages = new ArrayList<>();
                for (Image image : response.body().getImages()) {
                    similarImages.add(image);
                }
                if (similarImagesAdapter == null) {
                    similarImagesAdapter = new GridImageAdapter(similarImages, mContext);
                    similarImagesRecycleView.setAdapter(similarImagesAdapter);
                } else similarImagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });
    }
}
