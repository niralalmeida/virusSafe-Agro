package com.example.virussafeagro.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NewsModel implements Parcelable {
    private int newsId;
    private String newsTitle;
    private String newsSnippet;
    private String newsPressTime;
    private String newsAuthor;
    private String newsImageURL;
    private Bitmap newsImage;
    private List<String> newsArticleBody;
    private String newsURL;

    public NewsModel() {
    }

    public NewsModel(String newsSnippet) {
        this.newsSnippet = newsSnippet;
    }

    public NewsModel(int newsId, String newsTitle, String newsSnippet, String newsPressTime, String newsAuthor, String newsImageURL, Bitmap newsImage, List<String> newsArticleBody, String newsURL) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsSnippet = newsSnippet;
        this.newsPressTime = newsPressTime;
        this.newsAuthor = newsAuthor;
        this.newsImageURL = newsImageURL;
        this.newsImage = newsImage;
        this.newsArticleBody = newsArticleBody;
        this.newsURL = newsURL;
    }

    protected NewsModel(Parcel in) {
        newsId = in.readInt();
        newsTitle = in.readString();
        newsSnippet = in.readString();
        newsPressTime = in.readString();
        newsAuthor = in.readString();
        newsImageURL = in.readString();
        newsImage = in.readParcelable(Bitmap.class.getClassLoader());
        newsArticleBody = in.createStringArrayList();
        newsURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(newsId);
        dest.writeString(newsTitle);
        dest.writeString(newsSnippet);
        dest.writeString(newsPressTime);
        dest.writeString(newsAuthor);
        dest.writeString(newsImageURL);
        dest.writeParcelable(newsImage, flags);
        dest.writeStringList(newsArticleBody);
        dest.writeString(newsURL);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public void setNewsImageURL(String newsImageURL) {
        this.newsImageURL = newsImageURL;
    }

    public Bitmap getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(Bitmap newsImage) {
        this.newsImage = newsImage;
    }

    public List<String> getNewsArticleBody() {
        return newsArticleBody;
    }

    public void setNewsArticleBody(List<String> newsArticleBody) {
        this.newsArticleBody = newsArticleBody;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }
}
