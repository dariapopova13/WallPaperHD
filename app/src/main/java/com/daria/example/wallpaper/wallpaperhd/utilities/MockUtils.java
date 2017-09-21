package com.daria.example.wallpaper.wallpaperhd.utilities;

import android.content.Context;

import com.daria.example.wallpaper.wallpaperhd.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Daria Popova on 21.09.17.
 */

public class MockUtils {

    public static List<String> getMockUrl(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.mock_new_images));
    }
}
