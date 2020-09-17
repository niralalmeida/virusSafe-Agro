package com.example.virussafeagro.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class NewsModel implements Parcelable {
    private int newsId;
    private String newsTitle;
    private String newsSnippet;
    private String newsPressTime;
    private Bitmap newsImage;
    private String newsURL;

    public NewsModel() {
    }

    public NewsModel(int newsId, String newsTitle, String newsSnippet, String newsPressTime, Bitmap newsImage, String newsURL) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsSnippet = newsSnippet;
        this.newsPressTime = newsPressTime;
        this.newsImage = newsImage;
        this.newsURL = newsURL;
    }

    protected NewsModel(Parcel in) {
        newsId = in.readInt();
        newsTitle = in.readString();
        newsSnippet = in.readString();
        newsPressTime = in.readString();
        newsImage = in.readParcelable(Bitmap.class.getClassLoader());
        newsURL = in.readString();
    }

    public static final Creator<NewsModel> CREATOR = new Creator<NewsModel>() {
        @Override
        public NewsModel createFromParcel(Parcel in) {
            return new NewsModel(in);
        }

        @Override
        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsSnippet() {
        return newsSnippet;
    }

    public void setNewsSnippet(String newsSnippet) {
        this.newsSnippet = newsSnippet;
    }

    public String getNewsPressTime() {
        return newsPressTime;
    }

    public void setNewsPressTime(String newsPressTime) {
        this.newsPressTime = newsPressTime;
    }

    public Bitmap getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(Bitmap newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(newsId);
        dest.writeString(newsTitle);
        dest.writeString(newsSnippet);
        dest.writeString(newsPressTime);
        dest.writeParcelable(newsImage, flags);
        dest.writeString(newsURL);
    }
}
