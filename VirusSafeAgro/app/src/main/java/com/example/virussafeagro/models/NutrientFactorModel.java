package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NutrientFactorModel implements Parcelable {
    private int factorId;
    private String factorContent;

    public NutrientFactorModel(int factorId, String factorContent) {
        this.factorId = factorId;
        this.factorContent = factorContent;
    }

    public NutrientFactorModel() {
    }

    protected NutrientFactorModel(Parcel in) {
        factorId = in.readInt();
        factorContent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(factorId);
        dest.writeString(factorContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NutrientFactorModel> CREATOR = new Creator<NutrientFactorModel>() {
        @Override
        public NutrientFactorModel createFromParcel(Parcel in) {
            return new NutrientFactorModel(in);
        }

        @Override
        public NutrientFactorModel[] newArray(int size) {
            return new NutrientFactorModel[size];
        }
    };

    public int getFactorId() {
        return factorId;
    }

    public void setFactorId(int factorId) {
        this.factorId = factorId;
    }

    public String getFactorContent() {
        return factorContent;
    }

    public void setFactorContent(String factorContent) {
        this.factorContent = factorContent;
    }
}
