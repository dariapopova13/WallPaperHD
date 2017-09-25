package com.daria.example.wallpaper.wallpaperhd.activities;

import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.data.Image;
import com.daria.example.wallpaper.wallpaperhd.data.enums.ImageTypeEnum;
import com.daria.example.wallpaper.wallpaperhd.data.enums.OrderEnum;
import com.daria.example.wallpaper.wallpaperhd.fragments.GridImagesFragment;
import com.daria.example.wallpaper.wallpaperhd.utilities.AppUtils;
import com.daria.example.wallpaper.wallpaperhd.utilities.UrlUtils;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Fragment gridFragment;
    private String title;
    private List<Image> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        title = getIntent().getStringExtra("title");
        if (title == null || TextUtils.isEmpty(title))
            finish();

        createToolbar();
        createGridFragment();
    }


    private void createGridFragment() {
        String uri = new UrlUtils.Builder()
                .addOrder(OrderEnum.LATEST)
                .addPerPage(20)
                .addCategory(title)
                .addImageType(ImageTypeEnum.PHOTO)
                .create(this).getURI().toString();

        gridFragment = GridImagesFragment.newInstance(uri);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.single_category_images_fragment, gridFragment)
                .commit();

    }

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.single_category_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView titleTextView = (TextView) findViewById(R.id.single_category_title);

        titleTextView.setText(AppUtils.capitalize(title));

    }
}
