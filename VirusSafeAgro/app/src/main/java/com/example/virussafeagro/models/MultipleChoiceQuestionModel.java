package com.example.virussafeagro.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestionModel implements Parcelable {
    private int choiceQuestionId;
    private String multipleChoiceQuestionContent;
    private Bitmap multipleChoiceQuestionImage;
    private List<String> multipleChoiceQuestionOptionList;
    private List<String> multipleChoiceQuestionAnswerList; // store user's answers
    private int multipleChoiceQuestionVirusId;

    public MultipleChoiceQuestionModel() {
        this.multipleChoiceQuestionAnswerList = new ArrayList<>();
    }

    public MultipleChoiceQuestionModel(int choiceQuestionId, String multipleChoiceQuestionContent, Bitmap multipleChoiceQuestionImage, List<String> multipleChoiceQuestionOptionList, List<String> multipleChoiceQuestionAnswerList, int multipleChoiceQuestionVirusId) {
        this.choiceQuestionId = choiceQuestionId;
        this.multipleChoiceQuestionContent = multipleChoiceQuestionContent;
        this.multipleChoiceQuestionImage = multipleChoiceQuestionImage;
        this.multipleChoiceQuestionOptionList = multipleChoiceQuestionOptionList;
        this.multipleChoiceQuestionAnswerList = multipleChoiceQuestionAnswerList;
        this.multipleChoiceQuestionVirusId = multipleChoiceQuestionVirusId;
    }

    protected MultipleChoiceQuestionModel(Parcel in) {
        choiceQuestionId = in.readInt();
        multipleChoiceQuestionContent = in.readString();
        multipleChoiceQuestionImage = in.readParcelable(Bitmap.class.getClassLoader());
        multipleChoiceQuestionOptionList = in.createStringArrayList();
        multipleChoiceQuestionAnswerList = in.createStringArrayList();
        multipleChoiceQuestionVirusId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(choiceQuestionId);
        dest.writeString(multipleChoiceQuestionContent);
        dest.writeParcelable(multipleChoiceQuestionImage, flags);
        dest.writeStringList(multipleChoiceQuestionOptionList);
        dest.writeStringList(multipleChoiceQuestionAnswerList);
        dest.writeInt(multipleChoiceQuestionVirusId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MultipleChoiceQuestionModel> CREATOR = new Creator<MultipleChoiceQuestionModel>() {
        @Override
        public MultipleChoiceQuestionModel createFromParcel(Parcel in) {
            return new MultipleChoiceQuestionModel(in);
        }

        @Override
        public MultipleChoiceQuestionModel[] newArray(int size) {
            return new MultipleChoiceQuestionModel[size];
        }
    };

    public int getChoiceQuestionId() {
        return choiceQuestionId;
    }

    public void setChoiceQuestionId(int choiceQuestionId) {
        this.choiceQuestionId = choiceQuestionId;
    }

    public String getMultipleChoiceQuestionContent() {
        return multipleChoiceQuestionContent;
    }

    public void setMultipleChoiceQuestionContent(String multipleChoiceQuestionContent) {
        this.multipleChoiceQuestionContent = multipleChoiceQuestionContent;
    }

    public Bitmap getMultipleChoiceQuestionImage() {
        return multipleChoiceQuestionImage;
    }

    public void setMultipleChoiceQuestionImage(Bitmap multipleChoiceQuestionImage) {
        this.multipleChoiceQuestionImage = multipleChoiceQuestionImage;
    }

    public List<String> getMultipleChoiceQuestionOptionList() {
        return multipleChoiceQuestionOptionList;
    }

    public void setMultipleChoiceQuestionOptionList(List<String> multipleChoiceQuestionOptionList) {
        this.multipleChoiceQuestionOptionList = multipleChoiceQuestionOptionList;
    }

    public List<String> getMultipleChoiceQuestionAnswerList() {
        return multipleChoiceQuestionAnswerList;
    }

    public void setMultipleChoiceQuestionAnswerList(List<String> multipleChoiceQuestionAnswerList) {
        this.multipleChoiceQuestionAnswerList = multipleChoiceQuestionAnswerList;
    }

    public int getMultipleChoiceQuestionVirusId() {
        return multipleChoiceQuestionVirusId;
    }

    public void setMultipleChoiceQuestionVirusId(int multipleChoiceQuestionVirusId) {
        this.multipleChoiceQuestionVirusId = multipleChoiceQuestionVirusId;
    }
}
