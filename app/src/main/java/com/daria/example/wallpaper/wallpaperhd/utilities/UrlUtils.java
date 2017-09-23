package com.daria.example.wallpaper.wallpaperhd.utilities;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.daria.example.wallpaper.wallpaperhd.data.enums.ImageTypeEnum;
import com.daria.example.wallpaper.wallpaperhd.data.enums.OrderEnum;
import com.daria.example.wallpaper.wallpaperhd.data.enums.OrientationEnum;

/**
 * Created by Daria Popova on 22.09.17.
 */

public final class UrlUtils {

    public static final String BASE_URL = "https://pixabay.com/api/";
    private static final String apiKey = "6383525-6a19fe9d7f7d0199e2b219e64";
    private static final String QUERY_PARAM = "q";
    private static final String KEY_PARAM = "key";
    private static final String ID_PARAM = "id";
    private static final String IMAGE_TYPE_PARAM = "image_type";
    private static final String ORIENTATION_PARAM = "orientation";
    private static final String CATEGORY_PARAM = "category";
    private static final String ORDER_PARAM = "order";
    private static final String PAGE_PARAM = "page";
    private static final String PER_PAGE_PARAM = "per_page";
    private static final String PRETTY_PARAM = "pretty";
    private static final String MIN_WIDTH_PARAM = "min_width";
    private static final String MIN_HEIGHT_PARAM = "min_height";
    private Uri uri;

    public Uri getURI() {
        return uri;
    }


    public UrlUtils(Builder builder) {
        uri = builder.uri;
    }


    public static class Builder {

        private Uri uri;

        public Builder addCategory(String category) {
            appendChanges(CATEGORY_PARAM, category);
            return this;
        }

        public Builder addQuery(String... queries) {
            String query = "";
            for (int i = 0; i < queries.length; i++) {
                query += queries[i];
            }
            appendChanges(QUERY_PARAM, query);
            return this;
        }

        public Builder addOrder(OrderEnum order) {
            appendChanges(ORDER_PARAM, order.getName());
            return this;
        }

        public Builder addImageType(ImageTypeEnum imageType) {
            appendChanges(IMAGE_TYPE_PARAM, imageType.getName());
            return this;
        }

        public Builder addPage(int page) {
            appendChanges(PAGE_PARAM, String.valueOf(page));
            return this;
        }

        public Builder addPerPage(int perPage) {
            appendChanges(PER_PAGE_PARAM, String.valueOf(perPage));
            return this;
        }

        public Builder addID(long id) {
            appendChanges(ID_PARAM, String.valueOf(id));
            return this;
        }

        public Builder addOrientation(OrientationEnum orientation) {
            appendChanges(ORIENTATION_PARAM, orientation.getName());
            return this;
        }

//        public UrlUtils create() {
//            appendChanges(KEY_PARAM, apiKey);
//            appendChanges(PRETTY_PARAM, String.valueOf(true));
//            return new UrlUtils(this);
//        }

        public UrlUtils create(Context context) {
            appendChanges(KEY_PARAM, apiKey);
            appendChanges(PRETTY_PARAM, String.valueOf(true));
            appendChanges(MIN_HEIGHT_PARAM, String.valueOf(AppUtils.getDeviceHeight(context)));
            appendChanges(MIN_WIDTH_PARAM, String.valueOf(AppUtils.getDeviceWidth(context)));

            return new UrlUtils(this);
        }

        private void appendChanges(String param, String value) {
            if (uri == null)
                uri = Uri.parse(BASE_URL);
            if (!TextUtils.isEmpty(value))
                uri = uri.buildUpon().appendQueryParameter(param, value).build();
        }
    }
}
