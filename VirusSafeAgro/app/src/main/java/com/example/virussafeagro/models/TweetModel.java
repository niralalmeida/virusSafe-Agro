package com.example.virussafeagro.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class TweetModel implements Parcelable {
    private int tweetId;
    private String tweetContent; // twitter:description
    private String tweetSite; // @xxx
    private String tweetPublishTime; // "yyyy-MM-dd'T'HH:mm:ssXXX" --> "dd MMMM yyyy, HH:mm"
    private String tweetImageURL;
    private Bitmap tweetImageBitmap;
    private Bitmap tweetPortraitBitmap;

    public TweetModel() {
    }

    public TweetModel(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public TweetModel(int tweetId, String tweetContent, String tweetSite, String tweetPublishTime, String tweetImageURL, Bitmap tweetImageBitmap, Bitmap tweetPortraitBitmap) {
        this.tweetId = tweetId;
        this.tweetContent = tweetContent;
        this.tweetSite = tweetSite;
        this.tweetPublishTime = tweetPublishTime;
        this.tweetImageURL = tweetImageURL;
        this.tweetImageBitmap = tweetImageBitmap;
        this.tweetPortraitBitmap = tweetPortraitBitmap;
    }

    protected TweetModel(Parcel in) {
        tweetId = in.readInt();
        tweetContent = in.readString();
        tweetSite = in.readString();
        tweetPublishTime = in.readString();
        tweetImageURL = in.readString();
        tweetImageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        tweetPortraitBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tweetId);
        dest.writeString(tweetContent);
        dest.writeString(tweetSite);
        dest.writeString(tweetPublishTime);
        dest.writeString(tweetImageURL);
        dest.writeParcelable(tweetImageBitmap, flags);
        dest.writeParcelable(tweetPortraitBitmap, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TweetModel> CREATOR = new Creator<TweetModel>() {
        @Override
        public TweetModel createFromParcel(Parcel in) {
            return new TweetModel(in);
        }

        @Override
        public TweetModel[] newArray(int size) {
            return new TweetModel[size];
        }
    };

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public String getTweetSite() {
        return tweetSite;
    }

    public void setTweetSite(String tweetSite) {
        this.tweetSite = tweetSite;
    }

    public String getTweetPublishTime() {
        return tweetPublishTime;
    }

    public void setTweetPublishTime(String tweetPublishTime) {
        this.tweetPublishTime = tweetPublishTime;
    }

    public String getTweetImageURL() {
        return tweetImageURL;
    }

    public void setTweetImageURL(String tweetImageURL) {
        this.tweetImageURL = tweetImageURL;
    }

    public Bitmap getTweetImageBitmap() {
        return tweetImageBitmap;
    }

    public void setTweetImageBitmap(Bitmap tweetImageBitmap) {
        this.tweetImageBitmap = tweetImageBitmap;
    }

    public Bitmap getTweetPortraitBitmap() {
        return tweetPortraitBitmap;
    }

    public void setTweetPortraitBitmap(Bitmap tweetPortraitBitmap) {
        this.tweetPortraitBitmap = tweetPortraitBitmap;
    }
}
