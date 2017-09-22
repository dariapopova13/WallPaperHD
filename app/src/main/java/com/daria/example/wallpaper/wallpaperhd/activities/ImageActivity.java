package com.daria.example.wallpaper.wallpaperhd.activities;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.adapters.ImageStatePagerAdapter;
import com.daria.example.wallpaper.wallpaperhd.utilities.MockUtils;

import java.util.List;

import static com.daria.example.wallpaper.wallpaperhd.R.drawable.ic_arrow_back_white_24dp;

public class ImageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    public static List<String> mockUrl;
    private ImageStatePagerAdapter adapter;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mockUrl = MockUtils.getMockUrl(this);
        createToolbar();
        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.images_view_pager);
        adapter = new ImageStatePagerAdapter(getSupportFragmentManager(), mockUrl.size(), this);
        viewPager.setAdapter(adapter);
        onPageScrollStateChanged(0);
        viewPager.addOnPageChangeListener(this);
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
        String image = mockUrl.get(position);
        if (MockUtils.isFavourite(image)) {
            MenuItem item = menu.findItem(R.id.favourite);
            if (item != null) {
                item.setIcon(getDrawable(R.drawable.ic_star_white_24dp));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
