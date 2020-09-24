package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NutrientModel implements Parcelable {
    private int nutrientId;
    private String nutrientName;
    private List<NutrientSymptomModel> nutrientSymptomList;
    private List<NutrientReasonModel> nutrientReasonList;
    private List<NutrientFactorModel> nutrientFactorsList;
    private List<NutrientCorrectionMethodModel> nutrientCorrectionMethodList;

    public NutrientModel() {
    }

    public NutrientModel(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public NutrientModel(int nutrientId, String nutrientName, List<NutrientSymptomModel> nutrientSymptomList, List<NutrientReasonModel> nutrientReasonList, List<NutrientFactorModel> nutrientFactorsList, List<NutrientCorrectionMethodModel> nutrientCorrectionMethodList) {
        this.nutrientId = nutrientId;
        this.nutrientName = nutrientName;
        this.nutrientSymptomList = nutrientSymptomList;
        this.nutrientReasonList = nutrientReasonList;
        this.nutrientFactorsList = nutrientFactorsList;
        this.nutrientCorrectionMethodList = nutrientCorrectionMethodList;
    }

    protected NutrientModel(Parcel in) {
        nutrientId = in.readInt();
        nutrientName = in.readString();
        nutrientSymptomList = in.createTypedArrayList(NutrientSymptomModel.CREATOR);
        nutrientReasonList = in.createTypedArrayList(NutrientReasonModel.CREATOR);
        nutrientFactorsList = in.createTypedArrayList(NutrientFactorModel.CREATOR);
        nutrientCorrectionMethodList = in.createTypedArrayList(NutrientCorrectionMethodModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nutrientId);
        dest.writeString(nutrientName);
        dest.writeTypedList(nutrientSymptomList);
        dest.writeTypedList(nutrientReasonList);
        dest.writeTypedList(nutrientFactorsList);
        dest.writeTypedList(nutrientCorrectionMethodList);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public List<NutrientSymptomModel> getNutrientSymptomList() {
        return nutrientSymptomList;
    }

    public void setNutrientSymptomList(List<NutrientSymptomModel> nutrientSymptomList) {
        this.nutrientSymptomList = nutrientSymptomList;
    }

    public List<NutrientReasonModel> getNutrientReasonList() {
        return nutrientReasonList;
    }

    public void setNutrientReasonList(List<NutrientReasonModel> nutrientReasonList) {
        this.nutrientReasonList = nutrientReasonList;
    }

    public List<NutrientFactorModel> getNutrientFactorsList() {
        return nutrientFactorsList;
    }

    public void setNutrientFactorsList(List<NutrientFactorModel> nutrientFactorsList) {
        this.nutrientFactorsList = nutrientFactorsList;
    }

    public List<NutrientCorrectionMethodModel> getNutrientCorrectionMethodList() {
        return nutrientCorrectionMethodList;
    }

    public void setNutrientCorrectionMethodList(List<NutrientCorrectionMethodModel> nutrientCorrectionMethodList) {
        this.nutrientCorrectionMethodList = nutrientCorrectionMethodList;
    }
}
