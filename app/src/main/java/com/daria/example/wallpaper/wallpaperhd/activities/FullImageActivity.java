package com.daria.example.wallpaper.wallpaperhd.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daria.example.wallpaper.wallpaperhd.R;

public class FullImageActivity extends AppCompatActivity {

    private ImageView fullImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        String image = getIntent().getStringExtra("image");
        if (image == null || TextUtils.isEmpty(image))
            finish();

        fullImageView = (ImageView) findViewById(R.id.full_image);
        loadImage(image);
    }

    private void loadImage(String image) {
        Glide.with(this).load(image)
                .thumbnail(1f)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(fullImageView);
    }

}
