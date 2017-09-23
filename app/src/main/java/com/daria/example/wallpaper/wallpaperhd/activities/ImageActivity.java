package com.daria.example.wallpaper.wallpaperhd.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.adapters.GridImageAdapter;
import com.daria.example.wallpaper.wallpaperhd.data.Image;
import com.daria.example.wallpaper.wallpaperhd.fragments.GridImageFragment;
import com.daria.example.wallpaper.wallpaperhd.fragments.ImageFragment;
import com.daria.example.wallpaper.wallpaperhd.utilities.MockUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.daria.example.wallpaper.wallpaperhd.R.drawable.ic_arrow_back_white_24dp;

public class ImageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private List<Image> images;
    private ImageStatePagerAdapter adapter;
    private Menu menu;
    private String currentImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        currentImageUrl = getIntent().getStringExtra("image");
        if (currentImageUrl == null || TextUtils.isEmpty(currentImageUrl))
            finish();


        createToolbar();
        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.images_view_pager);
        adapter = new ImageStatePagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        List<String> imagesUrl = new ArrayList<>();
        for (Image image : images) {
            imagesUrl.add(image.getWebformatURL());
        }
        int current = imagesUrl.indexOf(currentImageUrl);
        viewPager.setCurrentItem(current);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_image_menu, menu);
        this.menu = menu;
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
        if (item.getIcon().getConstantState()
                .equals(getDrawable(R.drawable.ic_star_white_24dp).getConstantState())) {
            item.setIcon(getDrawable(R.drawable.ic_star_border_white_24dp));

        } else {
            item.setIcon(getDrawable(R.drawable.ic_star_white_24dp));
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (menu == null)
            return;
        Image image = images.get(position);
//        if (MockUtils.isFavourite(image)) {
//            MenuItem item = menu.findItem(R.id.favourite);
//            if (item != null) {
//                item.setIcon(getDrawable(R.drawable.ic_star_white_24dp));
//            }
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class ImageStatePagerAdapter extends FragmentStatePagerAdapter {

        public int NUM_ITEMS;
        private Context mContext;

        public ImageStatePagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
            NUM_ITEMS = images.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(position, mContext, images.get(position));
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
