package com.daria.example.wallpaper.wallpaperhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.activities.FullImageActivity;
import com.daria.example.wallpaper.wallpaperhd.adapters.GridImagesAdapter;
import com.daria.example.wallpaper.wallpaperhd.adapters.TagsAdapter;
import com.daria.example.wallpaper.wallpaperhd.data.Image;
import com.daria.example.wallpaper.wallpaperhd.data.ImageResponse;
import com.daria.example.wallpaper.wallpaperhd.data.enums.ImageTypeEnum;
import com.daria.example.wallpaper.wallpaperhd.data.enums.OrderEnum;
import com.daria.example.wallpaper.wallpaperhd.network.ApiClient;
import com.daria.example.wallpaper.wallpaperhd.network.ApiInterface;
import com.daria.example.wallpaper.wallpaperhd.utilities.AppUtils;
import com.daria.example.wallpaper.wallpaperhd.utilities.UrlUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daria Popova on 22.09.17.
 */
@SuppressWarnings("all")
public class SingleImageFragment extends Fragment implements View.OnClickListener {

    private Image image;
    private ImageView imageView;
    private RecyclerView tagsRecycleView;
    private TagsAdapter tagsAdapter;
    private RecyclerView similarImagesRecycleView;
    private GridImagesAdapter similarImagesAdapter;
    private GridImagesAdapter gridImageAdapter;
    private Context mContext;
    private FloatingActionButton fab;
    private int num;
    private TextView favCountTextView;
    private TextView likesCountTextView;
    private TextView downloadsCountTextView;
    private TextView loadedByTextView;


    public SingleImageFragment(Image image, Context mContext) {
        this.mContext = mContext;
        this.image = image;
    }

    public static SingleImageFragment newInstance(int num, Context mContext, Image image) {
        Bundle args = new Bundle();
        args.putInt("num", num);
        SingleImageFragment fragment = new SingleImageFragment(image, mContext);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.image_save_fab: {
                Drawable.ConstantState current = fab.getDrawable().getConstantState();
                if (current.equals(mContext.getDrawable(R.drawable.ic_arrow_downward_white_24dp).getConstantState())) {
                    saveImage();
                } else if (current.equals(mContext.getDrawable(R.drawable.ic_get_app_white_24dp).getConstantState())) {
                    applyImageAsBackground();
                }
                break;
            }
            case R.id.single_image: {
                Intent fullImageIntent = new Intent(getContext(), FullImageActivity.class);
                fullImageIntent.putExtra("image", image.getWebformatURL());
                startActivity(fullImageIntent);
            }
        }
    }

    private void applyImageAsBackground() {
        fab.setImageDrawable(mContext.getDrawable(R.drawable.ic_check_white_24dp));
        fab.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.colorCheckFab));
    }

    private void saveImage() {
        new SaveImageAsyncTask().execute();
    }


    private class SaveImageAsyncTask extends AsyncTask<Void, Void, Void> {

        private FileOutputStream out;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fab.setImageDrawable(mContext.getDrawable(R.drawable.ic_get_app_white_24dp));
        }

        private boolean createImageFile() {
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    AppUtils.DIRECTORY_NAME);

            boolean isCreated = false;

            try {
                if (!dir.exists())
                    dir.mkdir();
                File imageFile = new File(dir, AppUtils.createImageFileName(image));
                if (!imageFile.exists())
                    isCreated = imageFile.createNewFile();
                if (isCreated)
                    out = new FileOutputStream(imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return isCreated;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!createImageFile()) return null;
            try {
                Bitmap saveImage = Glide.with(mContext).load(image.getWebformatURL())
                        .asBitmap()
                        .into(-1, -1)
                        .get();
                saveImage.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                closeStream(out);
            }

            return null;
        }

        private void closeStream(FileOutputStream out) {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_image, container, false);
        view.setBackgroundColor(AppUtils.getRandomColorId(mContext));

        initViews(view);
        createRecycleView();
        loadImage();
        createFabCircle();
        setImageInfo();
        return view;
    }

    private void setImageInfo() {
        favCountTextView.setText(String.valueOf(image.getFavorites()));
        likesCountTextView.setText(String.valueOf(image.getLikes()));
        downloadsCountTextView.setText(String.valueOf(image.getDownloads()));

        loadedByTextView.setText(loadedByTextView.getText() + " " + image.getUser());
    }

    private void createFabCircle() {
        fab.setOnClickListener(this);
        getFabCurrentState();
    }

    private void getFabCurrentState() {
//        if (MockUtils.isDownloaded(imageUrl))
//            fab.setImageDrawable(mContext.getDrawable(R.drawable.ic_get_app_white_24dp));
    }

    private void initViews(View view) {
        tagsRecycleView = (RecyclerView) view.findViewById(R.id.image_tags_recycle_view);
        similarImagesRecycleView = (RecyclerView) view.findViewById(R.id.similar_images_recycle_view);
        imageView = (ImageView) view.findViewById(R.id.single_image);
        imageView.setOnClickListener(this);
        fab = (FloatingActionButton) view.findViewById(R.id.image_save_fab);
        favCountTextView = (TextView) view.findViewById(R.id.fav_count);
        downloadsCountTextView = (TextView) view.findViewById(R.id.downloaded_count);
        likesCountTextView = (TextView) view.findViewById(R.id.likes_count);
        loadedByTextView = (TextView) view.findViewById(R.id.loaded_by);
    }

    private void createRecycleView() {
        tagsAdapter = new TagsAdapter(mContext, image.getTags());
        RecyclerView.LayoutManager tagLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);

        tagsRecycleView.setAdapter(tagsAdapter);
        tagsRecycleView.setLayoutManager(tagLayoutManager);

        RecyclerView.LayoutManager similarImagesLayoutManager = new GridLayoutManager(mContext, 2);

        similarImagesRecycleView.setLayoutManager(similarImagesLayoutManager);
        similarImagesRecycleView.setNestedScrollingEnabled(false);
        loadSimilarImages();
    }

    private void loadImage() {
        if (image.getWebformatURL() == null || TextUtils.isEmpty(image.getWebformatURL())) return;
        Glide.with(this).load(image.getWebformatURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(1f)
                .into(imageView);
    }

    private void loadSimilarImages() {

        String uri = new UrlUtils.Builder()
                .addImageType(ImageTypeEnum.PHOTO)
                .addQuery(AppUtils.createQuery(image.getTags()))
                .addPerPage(20)
                .addOrder(OrderEnum.POPULAR)
                .create(mContext).getURI().toString();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ImageResponse> call = apiService.getImages(uri);
        call.enqueue(new Callback<ImageResponse>() {

            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response == null || response.body() == null || response.body().getImages() == null)
                    return;
                List<Image> similarImages = new ArrayList<>();
                for (Image image : response.body().getImages()) {
                    similarImages.add(image);
                }
                if (similarImagesAdapter == null) {
                    similarImagesAdapter = new GridImagesAdapter(similarImages, mContext);
                    similarImagesRecycleView.setAdapter(similarImagesAdapter);
                } else similarImagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });
    }
}
