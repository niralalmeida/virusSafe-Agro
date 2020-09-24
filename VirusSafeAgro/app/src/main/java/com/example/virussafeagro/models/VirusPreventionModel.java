package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VirusPreventionModel implements Parcelable {
    private int preId;
    private String preContent;
    private String preType;

    public VirusPreventionModel() {
    }

    public VirusPreventionModel(int preId, String preContent, String preType) {
        this.preId = preId;
        this.preContent = preContent;
        this.preType = preType;
    }

    protected VirusPreventionModel(Parcel in) {
        preId = in.readInt();
        preContent = in.readString();
        preType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(preId);
        dest.writeString(preContent);
        dest.writeString(preType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VirusPreventionModel> CREATOR = new Creator<VirusPreventionModel>() {
        @Override
        public VirusPreventionModel createFromParcel(Parcel in) {
            return new VirusPreventionModel(in);
        }

        @Override
        public VirusPreventionModel[] newArray(int size) {
            return new VirusPreventionModel[size];
        }
    };

    public int getPreId() {
        return preId;
    }

    public void setPreId(int preId) {
        this.preId = preId;
    }

    public String getPreContent() {
        return preContent;
    }

    public void setPreContent(String preContent) {
        this.preContent = preContent;
    }

    public String getPreType() {
        return preType;
    }

    public void setPreType(String preType) {
        this.preType = preType;
    }
}
