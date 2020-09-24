package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VirusSymptomModel implements Parcelable {
    private int symId;
    private String symContent;
    private String symObjectType;

    public VirusSymptomModel() {
    }

    public VirusSymptomModel(int symId, String symContent, String symObjectType) {
        this.symId = symId;
        this.symContent = symContent;
        this.symObjectType = symObjectType;
    }

    protected VirusSymptomModel(Parcel in) {
        symId = in.readInt();
        symContent = in.readString();
        symObjectType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(symId);
        dest.writeString(symContent);
        dest.writeString(symObjectType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VirusSymptomModel> CREATOR = new Creator<VirusSymptomModel>() {
        @Override
        public VirusSymptomModel createFromParcel(Parcel in) {
            return new VirusSymptomModel(in);
        }

        @Override
        public VirusSymptomModel[] newArray(int size) {
            return new VirusSymptomModel[size];
        }
    };

    public int getSymId() {
        return symId;
    }

    public void setSymId(int symId) {
        this.symId = symId;
    }

    public String getSymContent() {
        return symContent;
    }

    public void setSymContent(String symContent) {
        this.symContent = symContent;
    }

    public String getSymObjectType() {
        return symObjectType;
    }

    public void setSymObjectType(String symObjectType) {
        this.symObjectType = symObjectType;
    }
}
