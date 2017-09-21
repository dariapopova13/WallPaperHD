package com.daria.example.wallpaper.wallpaperhd.activities;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.adapters.GridImageAdapter;
import com.daria.example.wallpaper.wallpaperhd.adapters.TagsAdapter;
import com.daria.example.wallpaper.wallpaperhd.fragments.GridImageFragment;
import com.daria.example.wallpaper.wallpaperhd.utilities.MockUtils;
import com.github.jorgecastilloprz.FABProgressCircle;

import static com.daria.example.wallpaper.wallpaperhd.R.drawable.ic_arrow_back_white_24dp;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Toolbar toolbar;
    private RecyclerView tagsRecycleView;
    private TagsAdapter tagsAdapter;
    private FloatingActionButton fab;
    private RecyclerView similarImagesRecycleView;
    private GridImageAdapter gridImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        createToolbar();
        createRecycleView();
        loadImage();
        createFabCircle();
    }


    private void createFabCircle() {
        fab = (FloatingActionButton) findViewById(R.id.image_save_fab);
        fab.setOnClickListener(this);
    }

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.single_image_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getDrawable(ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void createRecycleView() {
        tagsRecycleView = (RecyclerView) findViewById(R.id.image_tags_recycle_view);
        tagsAdapter = new TagsAdapter(this);
        RecyclerView.LayoutManager tagLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, true);

        tagsRecycleView.setAdapter(tagsAdapter);
        tagsRecycleView.setLayoutManager(tagLayoutManager);

        similarImagesRecycleView = (RecyclerView) findViewById(R.id.similar_images_recycle_view);
        RecyclerView.LayoutManager similarImagesLayoutManager = new GridLayoutManager(this, 2);
        gridImageAdapter = new GridImageAdapter(MockUtils.getMockUrl(this), this);
        similarImagesRecycleView.setLayoutManager(similarImagesLayoutManager);
        similarImagesRecycleView.setAdapter(gridImageAdapter);
    }

    private void loadImage() {
        imageView = (ImageView) findViewById(R.id.single_image);
        String imageUrl = getIntent().getStringExtra("image");
        if (imageUrl == null || TextUtils.isEmpty(imageUrl)) return;
        Glide.with(this).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(1f)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.favourite: {
                addToFavourite(item);
                return true;
            }
            case R.id.complaint: {
                Toast.makeText(this, "This is not working yet", Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addToFavourite(MenuItem item) {
        if (item.getIcon().equals(getDrawable(R.drawable.ic_star_border_white_24dp)))
            item.setIcon(R.drawable.ic_star_white_24dp);
        else item.setIcon(R.drawable.ic_star_border_white_24dp);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.image_save_fab: {
                saveImage();
            }
        }
    }

    private void saveImage() {
        fab.setImageDrawable(getDrawable(R.drawable.ic_get_app_white_24dp));
    }
}
