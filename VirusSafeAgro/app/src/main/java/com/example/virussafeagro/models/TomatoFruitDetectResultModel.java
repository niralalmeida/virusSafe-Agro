package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TomatoFruitDetectResultModel implements Parcelable {
    private int tomatoCount;
    private String tomatoDetectResultImageStringURL;

    public TomatoFruitDetectResultModel() {
    }

    public TomatoFruitDetectResultModel(int tomatoCount, String tomatoDetectResultImageStringURL) {
        this.tomatoCount = tomatoCount;
        this.tomatoDetectResultImageStringURL = tomatoDetectResultImageStringURL;
    }

    protected TomatoFruitDetectResultModel(Parcel in) {
        tomatoCount = in.readInt();
        tomatoDetectResultImageStringURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tomatoCount);
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

    public String getTomatoDetectResultImageStringURL() {
        return tomatoDetectResultImageStringURL;
    }

    public void setTomatoDetectResultImageStringURL(String tomatoDetectResultImageStringURL) {
        this.tomatoDetectResultImageStringURL = tomatoDetectResultImageStringURL;
    }
}
