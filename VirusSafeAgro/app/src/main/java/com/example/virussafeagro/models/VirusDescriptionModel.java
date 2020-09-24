package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VirusDescriptionModel implements Parcelable {
    private int desId;
    private String desContent;

    public VirusDescriptionModel() {
    }

    public VirusDescriptionModel(int desId, String desContent) {
        this.desId = desId;
        this.desContent = desContent;
    }

    protected VirusDescriptionModel(Parcel in) {
        desId = in.readInt();
        desContent = in.readString();
    }

    public static final Creator<VirusDescriptionModel> CREATOR = new Creator<VirusDescriptionModel>() {
        @Override
        public VirusDescriptionModel createFromParcel(Parcel in) {
            return new VirusDescriptionModel(in);
        }

        @Override
        public VirusDescriptionModel[] newArray(int size) {
            return new VirusDescriptionModel[size];
        }
    };

    public int getDesId() {
        return desId;
    }

    public void setDesId(int desId) {
        this.desId = desId;
    }

    public String getDesContent() {
        return desContent;
    }

    public void setDesContent(String desContent) {
        this.desContent = desContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(desId);
        dest.writeString(desContent);
    }
}
