package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NutrientReasonModel implements Parcelable {
    private int reasonId;
    private String reasonContent;

    public NutrientReasonModel() {
    }

    public NutrientReasonModel(int reasonId, String reasonContent) {
        this.reasonId = reasonId;
        this.reasonContent = reasonContent;
    }

    protected NutrientReasonModel(Parcel in) {
        reasonId = in.readInt();
        reasonContent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reasonId);
        dest.writeString(reasonContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NutrientReasonModel> CREATOR = new Creator<NutrientReasonModel>() {
        @Override
        public NutrientReasonModel createFromParcel(Parcel in) {
            return new NutrientReasonModel(in);
        }

        @Override
        public NutrientReasonModel[] newArray(int size) {
            return new NutrientReasonModel[size];
        }
    };

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonContent() {
        return reasonContent;
    }

    public void setReasonContent(String reasonContent) {
        this.reasonContent = reasonContent;
    }
}
