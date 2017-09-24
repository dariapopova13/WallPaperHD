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
import com.daria.example.wallpaper.wallpaperhd.activities.SingleImageActivity;
import com.daria.example.wallpaper.wallpaperhd.data.Image;
import com.daria.example.wallpaper.wallpaperhd.utilities.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daria Popova on 20.09.17.
 */

public class GridImagesAdapter extends RecyclerView.Adapter<GridImagesAdapter.GridImageViewHolder>
        implements View.OnClickListener {

    private List<Image> images;
    private Context mContext;


    public GridImagesAdapter(List<Image> images, Context mContext) {
        this.images = images;
        this.mContext = mContext;
    }

    @Override
    public GridImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_grid_image_instance, parent, false);
        view.setOnClickListener(this);
        return new GridImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridImageViewHolder holder, int position) {
        if (images.get(position) == null) return;
        Image image = images.get(position);
        holder.imageUrl.setText(image.getWebformatURL());
        loadPicture(holder, image.getWebformatURL());
    }

    private void loadPicture(final GridImageViewHolder holder, String image) {
        if (image != null && !TextUtils.isEmpty(image)) {
            Glide.with(mContext).load(AppUtils.getSmallImage(image))
                    .centerCrop()
                    .crossFade()
//                    .asBitmap()
//                    .toBytes(Bitmap.CompressFormat.JPEG,100)
                    .thumbnail(1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.newImage);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onClick(View view) {
        TextView image = view.findViewById(R.id.image_grid_url);
        if (image == null || TextUtils.isEmpty(image.getText().toString())) return;
        Intent intent = new Intent(mContext, SingleImageActivity.class);
        intent.putExtra("image", image.getText().toString());
        intent.putParcelableArrayListExtra("images", (ArrayList) images);
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
