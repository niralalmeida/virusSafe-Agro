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
    private String virusChemicalControlImageURLString;
    private Bitmap virusChemicalControlImageBitmap;
    private String virusNonChemicalControlImageURLString;
    private Bitmap virusNonChemicalControlImageBitmap;
    private String virusSymptomLeafImageURLString;
    private Bitmap virusSymptomLeafImageBitmap;
    private String virusSymptomFruitImageURLString;
    private Bitmap virusSymptomFruitImageBitmap;

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

    public VirusModel(int virusId, String virusFullName, String virusAbbreviation, List<VirusDescriptionModel> virusDescriptionModelList, List<VirusSymptomModel> virusSymptomModelList, List<VirusCauseModel> virusCauseModelList, List<VirusPreventionModel> virusPreventionModelList, List<String> virusPictureURLList, List<Bitmap> virusPictureBitmapList, String virusChemicalControlImageURLString, Bitmap virusChemicalControlImageBitmap, String virusNonChemicalControlImageURLString, Bitmap virusNonChemicalControlImageBitmap, String virusSymptomLeafImageURLString, Bitmap virusSymptomLeafImageBitmap, String virusSymptomFruitImageURLString, Bitmap virusSymptomFruitImageBitmap) {
        this.virusId = virusId;
        this.virusFullName = virusFullName;
        this.virusAbbreviation = virusAbbreviation;
        this.virusDescriptionModelList = virusDescriptionModelList;
        this.virusSymptomModelList = virusSymptomModelList;
        this.virusCauseModelList = virusCauseModelList;
        this.virusPreventionModelList = virusPreventionModelList;
        this.virusPictureURLList = virusPictureURLList;
        this.virusPictureBitmapList = virusPictureBitmapList;
        this.virusChemicalControlImageURLString = virusChemicalControlImageURLString;
        this.virusChemicalControlImageBitmap = virusChemicalControlImageBitmap;
        this.virusNonChemicalControlImageURLString = virusNonChemicalControlImageURLString;
        this.virusNonChemicalControlImageBitmap = virusNonChemicalControlImageBitmap;
        this.virusSymptomLeafImageURLString = virusSymptomLeafImageURLString;
        this.virusSymptomLeafImageBitmap = virusSymptomLeafImageBitmap;
        this.virusSymptomFruitImageURLString = virusSymptomFruitImageURLString;
        this.virusSymptomFruitImageBitmap = virusSymptomFruitImageBitmap;
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
        virusChemicalControlImageURLString = in.readString();
        virusChemicalControlImageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        virusNonChemicalControlImageURLString = in.readString();
        virusNonChemicalControlImageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        virusSymptomLeafImageURLString = in.readString();
        virusSymptomLeafImageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        virusSymptomFruitImageURLString = in.readString();
        virusSymptomFruitImageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
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
        dest.writeString(virusChemicalControlImageURLString);
        dest.writeParcelable(virusChemicalControlImageBitmap, flags);
        dest.writeString(virusNonChemicalControlImageURLString);
        dest.writeParcelable(virusNonChemicalControlImageBitmap, flags);
        dest.writeString(virusSymptomLeafImageURLString);
        dest.writeParcelable(virusSymptomLeafImageBitmap, flags);
        dest.writeString(virusSymptomFruitImageURLString);
        dest.writeParcelable(virusSymptomFruitImageBitmap, flags);
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

    public String getVirusChemicalControlImageURLString() {
        return virusChemicalControlImageURLString;
    }

    public void setVirusChemicalControlImageURLString(String virusChemicalControlImageURLString) {
        this.virusChemicalControlImageURLString = virusChemicalControlImageURLString;
    }

    public Bitmap getVirusChemicalControlImageBitmap() {
        return virusChemicalControlImageBitmap;
    }

    public void setVirusChemicalControlImageBitmap(Bitmap virusChemicalControlImageBitmap) {
        this.virusChemicalControlImageBitmap = virusChemicalControlImageBitmap;
    }

    public String getVirusNonChemicalControlImageURLString() {
        return virusNonChemicalControlImageURLString;
    }

    public void setVirusNonChemicalControlImageURLString(String virusNonChemicalControlImageURLString) {
        this.virusNonChemicalControlImageURLString = virusNonChemicalControlImageURLString;
    }

    public Bitmap getVirusNonChemicalControlImageBitmap() {
        return virusNonChemicalControlImageBitmap;
    }

    public void setVirusNonChemicalControlImageBitmap(Bitmap virusNonChemicalControlImageBitmap) {
        this.virusNonChemicalControlImageBitmap = virusNonChemicalControlImageBitmap;
    }

    public String getVirusSymptomLeafImageURLString() {
        return virusSymptomLeafImageURLString;
    }

    public void setVirusSymptomLeafImageURLString(String virusSymptomLeafImageURLString) {
        this.virusSymptomLeafImageURLString = virusSymptomLeafImageURLString;
    }

    public Bitmap getVirusSymptomLeafImageBitmap() {
        return virusSymptomLeafImageBitmap;
    }

    public void setVirusSymptomLeafImageBitmap(Bitmap virusSymptomLeafImageBitmap) {
        this.virusSymptomLeafImageBitmap = virusSymptomLeafImageBitmap;
    }

    public String getVirusSymptomFruitImageURLString() {
        return virusSymptomFruitImageURLString;
    }

    public void setVirusSymptomFruitImageURLString(String virusSymptomFruitImageURLString) {
        this.virusSymptomFruitImageURLString = virusSymptomFruitImageURLString;
    }

    public Bitmap getVirusSymptomFruitImageBitmap() {
        return virusSymptomFruitImageBitmap;
    }

    public void setVirusSymptomFruitImageBitmap(Bitmap virusSymptomFruitImageBitmap) {
        this.virusSymptomFruitImageBitmap = virusSymptomFruitImageBitmap;
    }
}
