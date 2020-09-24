package com.example.virussafeagro.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VirusModel implements Parcelable {
    private int virusId;
    private String virusFullName;
    private String virusAbbreviation;
    private List<VirusDescriptionModel> virusDescriptionModelList;
    private List<VirusSymptomModel> virusSymptomModelList;
    private List<VirusCauseModel> virusCauseModelList;
    private List<VirusPreventionModel> virusPreventionModelList;
    private List<String> virusPictureURLList;
    private List<Bitmap> virusPictureBitmapList;

//    private String description;
//    private String symptoms;
//    private String causes;
//    private String prevention;

    public VirusModel() {
    }

    public VirusModel(String virusFullName) {
        this.virusFullName = virusFullName;
    }

    public VirusModel(int virusId, String virusFullName, String virusAbbreviation, List<VirusDescriptionModel> virusDescriptionModelList, List<VirusSymptomModel> virusSymptomModelList, List<VirusCauseModel> virusCauseModelList, List<VirusPreventionModel> virusPreventionModelList, List<String> virusPictureURLList, List<Bitmap> virusPictureBitmapList) {
        this.virusId = virusId;
        this.virusFullName = virusFullName;
        this.virusAbbreviation = virusAbbreviation;
        this.virusDescriptionModelList = virusDescriptionModelList;
        this.virusSymptomModelList = virusSymptomModelList;
        this.virusCauseModelList = virusCauseModelList;
        this.virusPreventionModelList = virusPreventionModelList;
        this.virusPictureURLList = virusPictureURLList;
        this.virusPictureBitmapList = virusPictureBitmapList;
    }

    protected VirusModel(Parcel in) {
        virusId = in.readInt();
        virusFullName = in.readString();
        virusAbbreviation = in.readString();
        virusDescriptionModelList = in.createTypedArrayList(VirusDescriptionModel.CREATOR);
        virusSymptomModelList = in.createTypedArrayList(VirusSymptomModel.CREATOR);
        virusCauseModelList = in.createTypedArrayList(VirusCauseModel.CREATOR);
        virusPreventionModelList = in.createTypedArrayList(VirusPreventionModel.CREATOR);
        virusPictureURLList = in.createStringArrayList();
        virusPictureBitmapList = in.createTypedArrayList(Bitmap.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(virusId);
        dest.writeString(virusFullName);
        dest.writeString(virusAbbreviation);
        dest.writeTypedList(virusDescriptionModelList);
        dest.writeTypedList(virusSymptomModelList);
        dest.writeTypedList(virusCauseModelList);
        dest.writeTypedList(virusPreventionModelList);
        dest.writeStringList(virusPictureURLList);
        dest.writeTypedList(virusPictureBitmapList);
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

    public List<VirusDescriptionModel> getVirusDescriptionModelList() {
        return virusDescriptionModelList;
    }

    public void setVirusDescriptionModelList(List<VirusDescriptionModel> virusDescriptionModelList) {
        this.virusDescriptionModelList = virusDescriptionModelList;
    }

    public List<VirusSymptomModel> getVirusSymptomModelList() {
        return virusSymptomModelList;
    }

    public void setVirusSymptomModelList(List<VirusSymptomModel> virusSymptomModelList) {
        this.virusSymptomModelList = virusSymptomModelList;
    }

    public List<VirusCauseModel> getVirusCauseModelList() {
        return virusCauseModelList;
    }

    public void setVirusCauseModelList(List<VirusCauseModel> virusCauseModelList) {
        this.virusCauseModelList = virusCauseModelList;
    }

    public List<VirusPreventionModel> getVirusPreventionModelList() {
        return virusPreventionModelList;
    }

    public void setVirusPreventionModelList(List<VirusPreventionModel> virusPreventionModelList) {
        this.virusPreventionModelList = virusPreventionModelList;
    }

    public List<String> getVirusPictureURLList() {
        return virusPictureURLList;
    }

    public void setVirusPictureURLList(List<String> virusPictureURLList) {
        this.virusPictureURLList = virusPictureURLList;
    }

    public List<Bitmap> getVirusPictureBitmapList() {
        return virusPictureBitmapList;
    }

    public void setVirusPictureBitmapList(List<Bitmap> virusPictureBitmapList) {
        this.virusPictureBitmapList = virusPictureBitmapList;
    }
}
