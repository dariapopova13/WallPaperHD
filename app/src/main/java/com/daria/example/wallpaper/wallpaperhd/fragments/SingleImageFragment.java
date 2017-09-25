package com.daria.example.wallpaper.wallpaperhd.fragments;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ProgressBar;
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
    private FloatingActionButton fab;
    private TextView favCountTextView;
    private TextView likesCountTextView;
    private TextView downloadsCountTextView;
    private TextView loadedByTextView;
    private ProgressBar fabProgressCircle;
    private static final String ARG_IMAGE = "image";


    public static SingleImageFragment newInstance(Image image) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_IMAGE, image);
        SingleImageFragment fragment = new SingleImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.image_save_fab: {
                Drawable.ConstantState current = fab.getDrawable().getConstantState();
                if (current.equals(getContext().getDrawable(R.drawable.ic_arrow_downward_white_24dp).getConstantState())) {
                    saveImage();
                } else if (current.equals(getContext().getDrawable(R.drawable.ic_get_app_white_24dp).getConstantState())) {
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
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        try {
            Bitmap imageBitmap = BitmapFactory.decodeFile(AppUtils.getImagePath(image));
            wallpaperManager.setBitmap(imageBitmap);
            wallpaperManager.suggestDesiredDimensions(imageBitmap.getWidth(), imageBitmap.getHeight());
            fab.setImageDrawable(getContext().getDrawable(R.drawable.ic_check_white_24dp));
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorCheckFab));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap createScrollableBitmap(Bitmap imageBitmap) {
//        int height = AppUtils.getDeviceHeight(getContext());
//        int width = (imageBitmap.getWidth() * AppUtils.getDeviceHeight(getContext())) / imageBitmap.getHeight();

        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth(),
                AppUtils.getDeviceHeight(getContext()), true);
        return imageBitmap;
    }

    private void saveImage() {
        new SaveImageAsyncTask().execute();
    }

    @Override
    public void onCreate(@Nullable Bundle args) {
        super.onCreate(args);
        image = getArguments().getParcelable(ARG_IMAGE);
        if (image == null)
            getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_image, container, false);
        view.setBackgroundColor(AppUtils.getRandomColorId(getContext()));

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

//        getFabCurrentState();
    }

    private void getFabCurrentState() {
//        if (MockUtils.isDownloaded(imageUrl))
//            fab.setImageDrawable(getContext().getDrawable(R.drawable.ic_get_app_white_24dp));
    }

    private void initViews(View view) {
        fabProgressCircle = (ProgressBar) view.findViewById(R.id.image_loading_progress_bar);
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
        tagsAdapter = new TagsAdapter(getContext(), image.getTags());
        RecyclerView.LayoutManager tagLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);

        tagsRecycleView.setAdapter(tagsAdapter);
        tagsRecycleView.setLayoutManager(tagLayoutManager);

        RecyclerView.LayoutManager similarImagesLayoutManager = new GridLayoutManager(getContext(), 2);

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
                .create(getContext()).getURI().toString();

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
                    similarImagesAdapter = new GridImagesAdapter(similarImages, getContext());
                    similarImagesRecycleView.setAdapter(similarImagesAdapter);
                } else similarImagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });
    }

    private class SaveImageAsyncTask extends AsyncTask<Void, Void, Void> {

        private FileOutputStream out;
        private String imagePath;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fabProgressCircle.getIndeterminateDrawable().setColorFilter(AppUtils.getRandomColorId(getContext()), PorterDuff.Mode.MULTIPLY);
            fab.setImageResource(0);
            fabProgressCircle.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, AppUtils.getImageMimeType(image.getWebformatURL()));
            values.put(MediaStore.Images.Media.DATA, imagePath);

            getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


            fabProgressCircle.setVisibility(View.GONE);
            fab.setImageDrawable(getContext().getDrawable(R.drawable.ic_get_app_white_24dp));
        }

        private boolean createImageFile() {
            File dir = new File(AppUtils.getApplicationDirectory());

            boolean isCreated = false;
            File imageFile = null;
            try {
                if (!dir.exists())
                    dir.mkdir();
                imageFile = new File(dir, AppUtils.getImageFileName(image));
                if (!imageFile.exists())
                    isCreated = imageFile.createNewFile();
                if (isCreated)
                    out = new FileOutputStream(imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagePath = imageFile.getAbsolutePath();
            return isCreated;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!createImageFile()) return null;
            try {
                Bitmap saveImage = Glide.with(getContext()).load(image.getWebformatURL())
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
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
