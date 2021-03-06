package com.example.virussafeagro.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ChoiceOptionModel implements Parcelable {
    private int choiceOptionId;
    private String choiceOptionLabel;
    private String choiceOptionContent;
    private String choiceOptionImageURL;
    private Bitmap choiceOptionImage;

    public ChoiceOptionModel() {
    }

    public ChoiceOptionModel(int choiceOptionId, String choiceOptionLabel, String choiceOptionContent) {
        this.choiceOptionId = choiceOptionId;
        this.choiceOptionLabel = choiceOptionLabel;
        this.choiceOptionContent = choiceOptionContent;
    }

    public ChoiceOptionModel(int choiceOptionId, String choiceOptionLabel, String choiceOptionContent, String choiceOptionImageURL, Bitmap choiceOptionImage) {
        this.choiceOptionId = choiceOptionId;
        this.choiceOptionLabel = choiceOptionLabel;
        this.choiceOptionContent = choiceOptionContent;
        this.choiceOptionImageURL = choiceOptionImageURL;
        this.choiceOptionImage = choiceOptionImage;
    }

    protected ChoiceOptionModel(Parcel in) {
        choiceOptionId = in.readInt();
        choiceOptionLabel = in.readString();
        choiceOptionContent = in.readString();
        choiceOptionImageURL = in.readString();
        choiceOptionImage = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<ChoiceOptionModel> CREATOR = new Creator<ChoiceOptionModel>() {
        @Override
        public ChoiceOptionModel createFromParcel(Parcel in) {
            return new ChoiceOptionModel(in);
        }

        @Override
        public ChoiceOptionModel[] newArray(int size) {
            return new ChoiceOptionModel[size];
        }
    };

    public int getChoiceOptionId() {
        return choiceOptionId;
    }

    public void setChoiceOptionId(int choiceOptionId) {
        this.choiceOptionId = choiceOptionId;
    }

    public String getChoiceOptionLabel() {
        return choiceOptionLabel;
    }

    public void setChoiceOptionLabel(String choiceOptionLabel) {
        this.choiceOptionLabel = choiceOptionLabel;
    }

    public String getChoiceOptionContent() {
        return choiceOptionContent;
    }

    public void setChoiceOptionContent(String choiceOptionContent) {
        this.choiceOptionContent = choiceOptionContent;
    }

    public String getChoiceOptionImageURL() {
        return choiceOptionImageURL;
    }

    public void setChoiceOptionImageURL(String choiceOptionImageURL) {
        this.choiceOptionImageURL = choiceOptionImageURL;
    }

    public Bitmap getChoiceOptionImage() {
        return choiceOptionImage;
    }

    public void setChoiceOptionImage(Bitmap choiceOptionImage) {
        this.choiceOptionImage = choiceOptionImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(choiceOptionId);
        dest.writeString(choiceOptionLabel);
        dest.writeString(choiceOptionContent);
        dest.writeString(choiceOptionImageURL);
        dest.writeParcelable(choiceOptionImage, flags);
    }
}
