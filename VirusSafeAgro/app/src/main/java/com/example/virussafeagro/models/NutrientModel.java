package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NutrientModel implements Parcelable {
    private int nutrientId;
    private String nutrientName;
    private List<String> nutrientSymptomList;
    private List<String> nutrientReasonList;
    private List<String> nutrientFactoryList;

    public NutrientModel() {
    }

    public NutrientModel(int nutrientId, String nutrientName, List<String> nutrientSymptomList, List<String> nutrientReasonList, List<String> nutrientFactoryList) {
        this.nutrientId = nutrientId;
        this.nutrientName = nutrientName;
        this.nutrientSymptomList = nutrientSymptomList;
        this.nutrientReasonList = nutrientReasonList;
        this.nutrientFactoryList = nutrientFactoryList;
    }

    protected NutrientModel(Parcel in) {
        nutrientId = in.readInt();
        nutrientName = in.readString();
        nutrientSymptomList = in.createStringArrayList();
        nutrientReasonList = in.createStringArrayList();
        nutrientFactoryList = in.createStringArrayList();
    }

    public static final Creator<NutrientModel> CREATOR = new Creator<NutrientModel>() {
        @Override
        public NutrientModel createFromParcel(Parcel in) {
            return new NutrientModel(in);
        }

        @Override
        public NutrientModel[] newArray(int size) {
            return new NutrientModel[size];
        }
    };

    public int getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public List<String> getNutrientSymptomList() {
        return nutrientSymptomList;
    }

    public void setNutrientSymptomList(List<String> nutrientSymptomList) {
        this.nutrientSymptomList = nutrientSymptomList;
    }

    public List<String> getNutrientReasonList() {
        return nutrientReasonList;
    }

    public void setNutrientReasonList(List<String> nutrientReasonList) {
        this.nutrientReasonList = nutrientReasonList;
    }

    public List<String> getNutrientFactoryList() {
        return nutrientFactoryList;
    }

    public void setNutrientFactoryList(List<String> nutrientFactoryList) {
        this.nutrientFactoryList = nutrientFactoryList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nutrientId);
        dest.writeString(nutrientName);
        dest.writeStringList(nutrientSymptomList);
        dest.writeStringList(nutrientReasonList);
        dest.writeStringList(nutrientFactoryList);
    }
}
