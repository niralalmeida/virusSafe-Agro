package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NutrientModel implements Parcelable {
    private int nutrientId;
    private String nutrientName;
    private String nutrientSymptom;
    private String nutrientReason;
    private String nutrientFactors;
//    private List<String> nutrientSymptomList;
//    private List<String> nutrientReasonList;
//    private List<String> nutrientFactorsList;
    private String nutrientCorrectionMethod;

    public NutrientModel() {
    }

    public NutrientModel(String nutrientReason) {
        this.nutrientReason = nutrientReason;
    }

    public NutrientModel(int nutrientId, String nutrientName, String nutrientSymptom, String nutrientReason, String nutrientFactors, String nutrientCorrectionMethod) {
        this.nutrientId = nutrientId;
        this.nutrientName = nutrientName;
        this.nutrientSymptom = nutrientSymptom;
        this.nutrientReason = nutrientReason;
        this.nutrientFactors = nutrientFactors;
        this.nutrientCorrectionMethod = nutrientCorrectionMethod;
    }

    protected NutrientModel(Parcel in) {
        nutrientId = in.readInt();
        nutrientName = in.readString();
        nutrientSymptom = in.readString();
        nutrientReason = in.readString();
        nutrientFactors = in.readString();
        nutrientCorrectionMethod = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nutrientId);
        dest.writeString(nutrientName);
        dest.writeString(nutrientSymptom);
        dest.writeString(nutrientReason);
        dest.writeString(nutrientFactors);
        dest.writeString(nutrientCorrectionMethod);
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

    public String getNutrientSymptom() {
        return nutrientSymptom;
    }

    public void setNutrientSymptom(String nutrientSymptom) {
        this.nutrientSymptom = nutrientSymptom;
    }

    public String getNutrientReason() {
        return nutrientReason;
    }

    public void setNutrientReason(String nutrientReason) {
        this.nutrientReason = nutrientReason;
    }

    public String getNutrientFactors() {
        return nutrientFactors;
    }

    public void setNutrientFactors(String nutrientFactors) {
        this.nutrientFactors = nutrientFactors;
    }

    public String getNutrientCorrectionMethod() {
        return nutrientCorrectionMethod;
    }

    public void setNutrientCorrectionMethod(String nutrientCorrectionMethod) {
        this.nutrientCorrectionMethod = nutrientCorrectionMethod;
    }
}
