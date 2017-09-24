package com.daria.example.wallpaper.wallpaperhd.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daria Popova on 22.09.17.
 */


public class Image implements Parcelable {

    private long id;
    private String pageURL;
    private String type;
    private String tags;
    private String previewURL;
    private int previewWidth;
    private int previewHeigh;
    private String webformatURL;
    private int webformatWidth;
    private int webformatHeight;
    private int imageWidth;
    private int imageHeight;
    private int imageSize;
    private int views;
    private int downloads;
    private int favorites;
    private int likes;
    private String user;

    public Image() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public int getPreviewHeigh() {
        return previewHeigh;
    }

    public void setPreviewHeigh(int previewHeigh) {
        this.previewHeigh = previewHeigh;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public int getWebformatWidth() {
        return webformatWidth;
    }

    public void setWebformatWidth(int webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    public int getWebformatHeight() {
        return webformatHeight;
    }

    public void setWebformatHeight(int webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.pageURL);
        dest.writeString(this.type);
        dest.writeString(this.tags);
        dest.writeString(this.previewURL);
        dest.writeInt(this.previewWidth);
        dest.writeInt(this.previewHeigh);
        dest.writeString(this.webformatURL);
        dest.writeInt(this.webformatWidth);
        dest.writeInt(this.webformatHeight);
        dest.writeInt(this.imageWidth);
        dest.writeInt(this.imageHeight);
        dest.writeInt(this.imageSize);
        dest.writeInt(this.views);
        dest.writeInt(this.downloads);
        dest.writeInt(this.favorites);
        dest.writeInt(this.likes);
        dest.writeString(this.user);
    }

    protected Image(Parcel in) {
        this.id = in.readLong();
        this.pageURL = in.readString();
        this.type = in.readString();
        this.tags = in.readString();
        this.previewURL = in.readString();
        this.previewWidth = in.readInt();
        this.previewHeigh = in.readInt();
        this.webformatURL = in.readString();
        this.webformatWidth = in.readInt();
        this.webformatHeight = in.readInt();
        this.imageWidth = in.readInt();
        this.imageHeight = in.readInt();
        this.imageSize = in.readInt();
        this.views = in.readInt();
        this.downloads = in.readInt();
        this.favorites = in.readInt();
        this.likes = in.readInt();
        this.user = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
