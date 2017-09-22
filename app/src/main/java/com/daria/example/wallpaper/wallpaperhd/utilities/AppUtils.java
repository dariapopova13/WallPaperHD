package com.daria.example.wallpaper.wallpaperhd.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;

/**
 * Created by Daria Popova on 20.09.17.
 */

public final class AppUtils {

    public static int getNewRandomColor(Context context, int previousColor) {
        int returnColor = Color.CYAN;
        int arrayId = context.getResources().getIdentifier("colors", "array", context.getPackageName());
        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            do {
                int index = (int) (Math.random() * colors.length());
                returnColor = colors.getColor(index, Color.CYAN);
            } while (returnColor == previousColor);
            colors.recycle();
        }
        return returnColor;
    }

    public static int getRandomColorId(Context context) {
        int returnColor = Color.CYAN;
        int arrayId = context.getResources().getIdentifier("colors", "array", context.getPackageName());
        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.CYAN);
            colors.recycle();
        }
        return returnColor;
    }

    public static String capitalize(String string) {
        if (string != null && !TextUtils.isEmpty(string) && string.length() >= 1) {
            string = string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
        }
        return string;
    }
}
