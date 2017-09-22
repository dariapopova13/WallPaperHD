package com.daria.example.wallpaper.wallpaperhd.utilities;

import android.content.Context;

import com.daria.example.wallpaper.wallpaperhd.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daria Popova on 21.09.17.
 */

public final class MockUtils {

    private static List<String> favourite = new ArrayList<>();
    private static List<String> downloaded = new ArrayList<>();

    static {
        favourite.add("http://hdwallpaper.info/wp-content/uploads/2016/10/nature-wallpap.jpg");
        favourite.add("https://images.pexels.com/photos/33109/fall-autumn-red-season.jpg");


        downloaded.add("https://images.pexels.com/photos/578853/pexels-photo-578853.jpeg");
        downloaded.add("https://images.pexels.com/photos/87646/horsehead-nebula-dark-nebula-constellation-orion-87646.jpeg");
    }

    public static List<String> getMockUrl(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.mock_new_images));
    }

    public static boolean isFavourite(String image) {
        return favourite.contains(image);
    }

    public static boolean isDownloaded(String image) {
        return downloaded.contains(image);
    }
}
