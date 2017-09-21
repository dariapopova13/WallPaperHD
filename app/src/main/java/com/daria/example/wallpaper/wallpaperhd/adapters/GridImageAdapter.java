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
import com.daria.example.wallpaper.wallpaperhd.activities.ImageActivity;

import java.util.List;

/**
 * Created by Daria Popova on 20.09.17.
 */

public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.GridImageViewHolder>
        implements View.OnClickListener {

    private List<String> mockImagesUrl;
    private Context mContext;


    public GridImageAdapter(List<String> imageUrl, Context mContext) {
        this.mockImagesUrl = imageUrl;
        this.mContext = mContext;
    }

    @Override
    public GridImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_grid_image, parent, false);
        view.setOnClickListener(this);
        return new GridImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridImageViewHolder holder, int position) {
        if (mockImagesUrl.get(position) == null) return;
        holder.imageUrl.setText(mockImagesUrl.get(position));
        loadPicture(holder, mockImagesUrl.get(position));
    }

    private void loadPicture(final GridImageViewHolder holder, String image) {
        if (!TextUtils.isEmpty(image)) {
            Glide.with(mContext).load(image)
                    .thumbnail(1f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.newImage);

        }
    }

    @Override
    public int getItemCount() {
        return mockImagesUrl.size();
    }

    @Override
    public void onClick(View view) {
        TextView image = view.findViewById(R.id.image_grid_url);
        if (image == null || TextUtils.isEmpty(image.getText().toString())) return;
        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra("image", image.getText().toString());
        mContext.startActivity(intent);
    }

    public class GridImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView newImage;
        public TextView imageUrl;

        public GridImageViewHolder(View view) {
            super(view);
            newImage = (ImageView) view.findViewById(R.id.single_grid_image);
            imageUrl = (TextView) view.findViewById(R.id.image_grid_url);
        }
    }


}
