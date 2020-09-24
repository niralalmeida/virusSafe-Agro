package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NutrientSymptomModel implements Parcelable {
    private int symId;
    private String symContent;
    private String symType;

    public NutrientSymptomModel() {
    }

    public NutrientSymptomModel(int symId, String symContent, String symType) {
        this.symId = symId;
        this.symContent = symContent;
        this.symType = symType;
    }

    protected NutrientSymptomModel(Parcel in) {
        symId = in.readInt();
        symContent = in.readString();
        symType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(symId);
        dest.writeString(symContent);
        dest.writeString(symType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NutrientSymptomModel> CREATOR = new Creator<NutrientSymptomModel>() {
        @Override
        public NutrientSymptomModel createFromParcel(Parcel in) {
            return new NutrientSymptomModel(in);
        }

        @Override
        public NutrientSymptomModel[] newArray(int size) {
            return new NutrientSymptomModel[size];
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

    public String getSymType() {
        return symType;
    }

    public void setSymType(String symType) {
        this.symType = symType;
    }
}
