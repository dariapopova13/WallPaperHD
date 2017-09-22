package com.daria.example.wallpaper.wallpaperhd.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.daria.example.wallpaper.wallpaperhd.fragments.ImageFragment;

/**
 * Created by Daria Popova on 22.09.17.
 */

public class ImageStatePagerAdapter extends FragmentStatePagerAdapter {

    public static int NUM_ITEMS;
    private Context mContext;

    public ImageStatePagerAdapter(FragmentManager fm, int count, Context mContext) {
        super(fm);
        this.mContext = mContext;
        NUM_ITEMS = count;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(position, mContext);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
