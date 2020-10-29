package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TomatoFruitDetectResultModel implements Parcelable {
    private int tomatoCount;
    private int goodCount;
    private int goodCountPercentage;
    private int badCount;
    private int badCountPercentage;
    private String tomatoDetectResultImageStringURL;

    public TomatoFruitDetectResultModel() {
    }

    public TomatoFruitDetectResultModel(int tomatoCount, int goodCount, int goodCountPercentage, int badCount, int badCountPercentage, String tomatoDetectResultImageStringURL) {
        this.tomatoCount = tomatoCount;
        this.goodCount = goodCount;
        this.goodCountPercentage = goodCountPercentage;
        this.badCount = badCount;
        this.badCountPercentage = badCountPercentage;
        this.tomatoDetectResultImageStringURL = tomatoDetectResultImageStringURL;
    }

    protected TomatoFruitDetectResultModel(Parcel in) {
        tomatoCount = in.readInt();
        goodCount = in.readInt();
        goodCountPercentage = in.readInt();
        badCount = in.readInt();
        badCountPercentage = in.readInt();
        tomatoDetectResultImageStringURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tomatoCount);
        dest.writeInt(goodCount);
        dest.writeInt(goodCountPercentage);
        dest.writeInt(badCount);
        dest.writeInt(badCountPercentage);
        dest.writeString(tomatoDetectResultImageStringURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TomatoFruitDetectResultModel> CREATOR = new Creator<TomatoFruitDetectResultModel>() {
        @Override
        public TomatoFruitDetectResultModel createFromParcel(Parcel in) {
            return new TomatoFruitDetectResultModel(in);
        }

        @Override
        public TomatoFruitDetectResultModel[] newArray(int size) {
            return new TomatoFruitDetectResultModel[size];
        }
    };

    public int getTomatoCount() {
        return tomatoCount;
    }

    public void setTomatoCount(int tomatoCount) {
        this.tomatoCount = tomatoCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getGoodCountPercentage() {
        return goodCountPercentage;
    }

    public void setGoodCountPercentage(int goodCountPercentage) {
        this.goodCountPercentage = goodCountPercentage;
    }

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public int getBadCountPercentage() {
        return badCountPercentage;
    }

    public void setBadCountPercentage(int badCountPercentage) {
        this.badCountPercentage = badCountPercentage;
    }

    public String getTomatoDetectResultImageStringURL() {
        return tomatoDetectResultImageStringURL;
    }

    public void setTomatoDetectResultImageStringURL(String tomatoDetectResultImageStringURL) {
        this.tomatoDetectResultImageStringURL = tomatoDetectResultImageStringURL;
    }
}
