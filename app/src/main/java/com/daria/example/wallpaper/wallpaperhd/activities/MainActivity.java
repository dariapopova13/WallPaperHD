package com.daria.example.wallpaper.wallpaperhd.activities;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.adapters.ViewPagerAdaper;
import com.daria.example.wallpaper.wallpaperhd.data.enums.ImageTypeEnum;
import com.daria.example.wallpaper.wallpaperhd.data.enums.OrderEnum;
import com.daria.example.wallpaper.wallpaperhd.fragments.CategoriesListFragment;
import com.daria.example.wallpaper.wallpaperhd.fragments.GridImagesFragment;
import com.daria.example.wallpaper.wallpaperhd.utilities.AppUtils;
import com.daria.example.wallpaper.wallpaperhd.utilities.UrlUtils;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppUtils.verifyStoragePermissions(this);

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        createAppBar();
        createViewPager();
    }

    private void createViewPager() {
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

        ViewPagerAdaper adaper = new ViewPagerAdaper(getSupportFragmentManager());
        adaper.addFragment(new CategoriesListFragment(), getString(R.string.category_title));
        adaper.addFragment(GridImagesFragment.newInstance(createUri(OrderEnum.LATEST)),
                getString(R.string.new_images_title));
        adaper.addFragment(GridImagesFragment.newInstance(createUri(OrderEnum.POPULAR)),
                getString(R.string.top_title));


        viewPager.setAdapter(adaper);
        applyCustomTabView(adaper);
    }


    private String createUri(OrderEnum order) {
        Uri uri = new UrlUtils.Builder()
                .addOrder(order)
                .addImageType(ImageTypeEnum.PHOTO)
                .addPerPage(20)
                .create(this).getURI();
        return uri.toString();
    }

    private void createAppBar() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_person_outline_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "There will be a new intent", Toast.LENGTH_SHORT).show();
            }
        });

        TextView title = (TextView) findViewById(R.id.app_title);
        Typeface titleFont = Typeface.createFromAsset(getAssets(), "fonts/Architects Daughter.ttf");
        title.setTypeface(titleFont);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.history: {
                Toast.makeText(this, "There will be a history intent", Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void applyCustomTabView(ViewPagerAdaper adaper) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null)
                tab.setCustomView(adaper.getTabView(this, i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabLayout.setSelectedTabIndicatorColor(AppUtils.getRandomColorId(this));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
