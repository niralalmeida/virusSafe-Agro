package com.example.virussafeagro.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class VirusModel implements Parcelable {
    private int virusId;
    private String virusFullName;
    private String virusAbbreviation;
    private String symptoms;
    private String spread;
    private String prevention;
    private String distribution;
    private Bitmap virusPicture;

    public VirusModel() {
    }

    public VirusModel(int virusId, String virusFullName, String virusAbbreviation, String symptoms, String spread, String prevention, String distribution, Bitmap virusPicture) {
        this.virusId = virusId;
        this.virusFullName = virusFullName;
        this.virusAbbreviation = virusAbbreviation;
        this.symptoms = symptoms;
        this.spread = spread;
        this.prevention = prevention;
        this.distribution = distribution;
        this.virusPicture = virusPicture;
    }

    protected VirusModel(Parcel in) {
        virusId = in.readInt();
        virusFullName = in.readString();
        virusAbbreviation = in.readString();
        symptoms = in.readString();
        spread = in.readString();
        prevention = in.readString();
        distribution = in.readString();
        virusPicture = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(virusId);
        dest.writeString(virusFullName);
        dest.writeString(virusAbbreviation);
        dest.writeString(symptoms);
        dest.writeString(spread);
        dest.writeString(prevention);
        dest.writeString(distribution);
        dest.writeParcelable(virusPicture, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VirusModel> CREATOR = new Creator<VirusModel>() {
        @Override
        public VirusModel createFromParcel(Parcel in) {
            return new VirusModel(in);
        }

        @Override
        public VirusModel[] newArray(int size) {
            return new VirusModel[size];
        }
    };

    public int getVirusId() {
        return virusId;
    }

    public void setVirusId(int virusId) {
        this.virusId = virusId;
    }

    public String getVirusFullName() {
        return virusFullName;
    }

    public void setVirusFullName(String virusFullName) {
        this.virusFullName = virusFullName;
    }

    public String getVirusAbbreviation() {
        return virusAbbreviation;
    }

    public void setVirusAbbreviation(String virusAbbreviation) {
        this.virusAbbreviation = virusAbbreviation;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSpread() {
        return spread;
    }

    public void setSpread(String spread) {
        this.spread = spread;
    }

    public String getPrevention() {
        return prevention;
    }

    public void setPrevention(String prevention) {
        this.prevention = prevention;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public Bitmap getVirusPicture() {
        return virusPicture;
    }

    public void setVirusPicture(Bitmap virusPicture) {
        this.virusPicture = virusPicture;
    }
}
