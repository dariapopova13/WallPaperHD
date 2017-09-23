package com.daria.example.wallpaper.wallpaperhd.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daria Popova on 20.09.17.
 */

public final class AppUtils {

    public static List<String> seperateTags(String s) {
        List<String> tags = new ArrayList<>();
        TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
        splitter.setString(s);
        Iterator<String> iterator = splitter.iterator();
        while (iterator.hasNext()) {
            tags.add(iterator.next());
        }
        Collections.sort(tags, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                if (TextUtils.isEmpty(s) || TextUtils.isEmpty(t1))
                    return 0;
                return Character.compare(s.charAt(0), t1.charAt(0));
            }
        });
        return tags;
    }

    public static int getDeviceWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getDeviceHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

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
            string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        }
        return string;
    }

    public static String createQuery(String tags) {
        String result = "";
        TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
        splitter.setString(tags);
        for (String s : splitter) {
            result += s + "+";
        }
        result = result.toLowerCase().substring(0, result.length() - 1);
        return result;
    }
}
