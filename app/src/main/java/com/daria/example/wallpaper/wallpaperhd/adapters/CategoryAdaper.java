package com.daria.example.wallpaper.wallpaperhd.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.activities.CategoryActivity;
import com.daria.example.wallpaper.wallpaperhd.data.Category;
import com.yayandroid.parallaxrecyclerview.ParallaxImageView;
import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daria Popova on 19.09.17.
 */

public class CategoryAdaper extends RecyclerView.Adapter<CategoryAdaper.CategoryViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<Category> categories;


    public CategoryAdaper(Context mContext) {
        this.mContext = mContext;
        categories = new ArrayList<>();
        String[] titles = mContext.getResources().getStringArray(R.array.categories);
        Category category;
        String[] mockImages = mContext.getResources().getStringArray(R.array.mock_new_images);
        for (int i = 0; i < titles.length; i++) {
            category = new Category(mockImages[i], titles[i]);
            categories.add(category);
        }
    }

    public CategoryAdaper(Context mContext, List<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_category_row, parent, false);
        view.setOnClickListener(this);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.categoryTitle.setText(category.getTitle());
        loadCategoryImage(holder.getBackgroundImage(),category.getImage());
//        holder.getBackgroundImage().reuse();
    }

    private void loadCategoryImage(ImageView categoryImage, final String image) {
        if (!TextUtils.isEmpty(image)) {
            Glide.with(mContext).load(image)
                    .thumbnail(1f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(categoryImage);
        }
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onClick(View view) {
        TextView textView = view.findViewById(R.id.category_title);
        if (textView != null && !TextUtils.isEmpty(textView.getText().toString())) {
            openSingleCategoryIntent(textView.getText().toString());
        }
    }

    private void openSingleCategoryIntent(String title) {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("title", title);
        mContext.startActivity(intent);
    }

    public class CategoryViewHolder extends ParallaxViewHolder{

        private ParallaxImageView categoryImage;
        private TextView categoryTitle;

        @Override
        public int getParallaxImageId() {
            return R.id.category_image;
        }

        public CategoryViewHolder(View view) {
            super(view);

            categoryImage = (ParallaxImageView) view.findViewById(R.id.category_image);
            categoryTitle = (TextView) view.findViewById(R.id.category_title);
        }
    }
}
