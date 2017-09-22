package com.daria.example.wallpaper.wallpaperhd.fragments;

import android.content.Context;
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
import com.daria.example.wallpaper.wallpaperhd.activities.ImageActivity;
import com.daria.example.wallpaper.wallpaperhd.adapters.GridImageAdapter;
import com.daria.example.wallpaper.wallpaperhd.adapters.TagsAdapter;
import com.daria.example.wallpaper.wallpaperhd.utilities.AppUtils;
import com.daria.example.wallpaper.wallpaperhd.utilities.MockUtils;

/**
 * Created by Daria Popova on 22.09.17.
 */
@SuppressWarnings("all")
public class ImageFragment extends Fragment implements View.OnClickListener {

    public final String imageUrl;
    private ImageView imageView;
    private RecyclerView tagsRecycleView;
    private TagsAdapter tagsAdapter;
    private RecyclerView similarImagesRecycleView;
    private GridImageAdapter gridImageAdapter;
    private Context mContext;
    private FloatingActionButton fab;
    private int num;

    public ImageFragment(String imageUrl, Context mContext) {
        this.mContext = mContext;
        this.imageUrl = imageUrl;
    }

    public static ImageFragment newInstance(int num, Context mContext) {
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putString("image", ImageActivity.mockUrl.get(num));
        ImageFragment fragment = new ImageFragment(ImageActivity.mockUrl.get(num), mContext);
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
        if (MockUtils.isDownloaded(imageUrl))
            fab.setImageDrawable(mContext.getDrawable(R.drawable.ic_get_app_white_24dp));
    }

    private void initViews(View view) {
        tagsRecycleView = (RecyclerView) view.findViewById(R.id.image_tags_recycle_view);
        similarImagesRecycleView = (RecyclerView) view.findViewById(R.id.similar_images_recycle_view);
        imageView = (ImageView) view.findViewById(R.id.single_image);
        fab = (FloatingActionButton) view.findViewById(R.id.image_save_fab);
    }

    private void createRecycleView() {
        tagsAdapter = new TagsAdapter(mContext);
        RecyclerView.LayoutManager tagLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);

        tagsRecycleView.setAdapter(tagsAdapter);
        tagsRecycleView.setLayoutManager(tagLayoutManager);

        RecyclerView.LayoutManager similarImagesLayoutManager = new GridLayoutManager(mContext, 2);
        gridImageAdapter = new GridImageAdapter(MockUtils.getMockUrl(mContext), mContext);
        similarImagesRecycleView.setLayoutManager(similarImagesLayoutManager);
        similarImagesRecycleView.setAdapter(gridImageAdapter);
        similarImagesRecycleView.setNestedScrollingEnabled(false);
    }

    private void loadImage() {
        if (imageUrl == null || TextUtils.isEmpty(imageUrl)) return;
        Glide.with(this).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(1f)
                .centerCrop()
                .into(imageView);
    }
}
