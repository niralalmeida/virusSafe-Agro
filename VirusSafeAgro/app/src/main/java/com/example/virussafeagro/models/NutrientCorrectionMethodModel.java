package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NutrientCorrectionMethodModel implements Parcelable {
    private int methodId;
    private String methodContent;

    public NutrientCorrectionMethodModel() {
    }

    public NutrientCorrectionMethodModel(int methodId, String methodContent) {
        this.methodId = methodId;
        this.methodContent = methodContent;
    }

    protected NutrientCorrectionMethodModel(Parcel in) {
        methodId = in.readInt();
        methodContent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(methodId);
        dest.writeString(methodContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NutrientCorrectionMethodModel> CREATOR = new Creator<NutrientCorrectionMethodModel>() {
        @Override
        public NutrientCorrectionMethodModel createFromParcel(Parcel in) {
            return new NutrientCorrectionMethodModel(in);
        }

        @Override
        public NutrientCorrectionMethodModel[] newArray(int size) {
            return new NutrientCorrectionMethodModel[size];
        }
    };

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public String getMethodContent() {
        return methodContent;
    }

    public void setMethodContent(String methodContent) {
        this.methodContent = methodContent;
    }
}
