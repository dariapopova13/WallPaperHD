package com.daria.example.wallpaper.wallpaperhd.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.daria.example.wallpaper.wallpaperhd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daria Popova on 19.09.17.
 */

public class ViewPagerAdaper extends FragmentPagerAdapter {

    private final List<Fragment> fragments;
    private final List<String> titles;

    public ViewPagerAdaper(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    public View getTabView(Context context, int position) {
        View tab = LayoutInflater.from(context)
                .inflate(R.layout.custom_tab_view, null);
        TextView tv = (TextView) tab.findViewById(R.id.tab_title);
        tv.setText(titles.get(position));
        return tab;
    }
}
