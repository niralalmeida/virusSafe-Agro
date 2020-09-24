package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VirusCauseModel implements Parcelable {
    private int causeId;
    private String causeContent;
    private String causeType;

    public VirusCauseModel() {
    }

    public VirusCauseModel(int causeId, String causeContent, String causeType) {
        this.causeId = causeId;
        this.causeContent = causeContent;
        this.causeType = causeType;
    }

    protected VirusCauseModel(Parcel in) {
        causeId = in.readInt();
        causeContent = in.readString();
        causeType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(causeId);
        dest.writeString(causeContent);
        dest.writeString(causeType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VirusCauseModel> CREATOR = new Creator<VirusCauseModel>() {
        @Override
        public VirusCauseModel createFromParcel(Parcel in) {
            return new VirusCauseModel(in);
        }

        @Override
        public VirusCauseModel[] newArray(int size) {
            return new VirusCauseModel[size];
        }
    };

    public int getCauseId() {
        return causeId;
    }

    public void setCauseId(int causeId) {
        this.causeId = causeId;
    }

    public String getCauseContent() {
        return causeContent;
    }

    public void setCauseContent(String causeContent) {
        this.causeContent = causeContent;
    }

    public String getCauseType() {
        return causeType;
    }

    public void setCauseType(String causeType) {
        this.causeType = causeType;
    }
}
