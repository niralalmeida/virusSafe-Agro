package com.example.virussafeagro.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ChoiceQuestionModel implements Parcelable {
    private int choiceQuestionId;
    private String choiceQuestionType; // single or multiple
    private String choiceQuestionContent;
    private Bitmap choiceQuestionImage;
    private List<ChoiceOptionModel> choiceQuestionOptionList;
    private List<String> userAnswerList; // store user's answers
    private List<String> correctAnswerList; // store correct answers
    private int choiceQuestionVirusId;

    public ChoiceQuestionModel() {
    }

    public ChoiceQuestionModel(int choiceQuestionId, String choiceQuestionType, String choiceQuestionContent, List<String> correctAnswerList) {
        this.choiceQuestionId = choiceQuestionId;
        this.choiceQuestionType = choiceQuestionType;
        this.choiceQuestionContent = choiceQuestionContent;
        this.correctAnswerList = correctAnswerList;
    }

    public ChoiceQuestionModel(int choiceQuestionId, String choiceQuestionType, String choiceQuestionContent, Bitmap choiceQuestionImage, List<ChoiceOptionModel> choiceQuestionOptionList, List<String> userAnswerList, List<String> correctAnswerList, int choiceQuestionVirusId) {
        this.choiceQuestionId = choiceQuestionId;
        this.choiceQuestionType = choiceQuestionType;
        this.choiceQuestionContent = choiceQuestionContent;
        this.choiceQuestionImage = choiceQuestionImage;
        this.choiceQuestionOptionList = choiceQuestionOptionList;
        this.userAnswerList = userAnswerList;
        this.correctAnswerList = correctAnswerList;
        this.choiceQuestionVirusId = choiceQuestionVirusId;
    }

    protected ChoiceQuestionModel(Parcel in) {
        choiceQuestionId = in.readInt();
        choiceQuestionType = in.readString();
        choiceQuestionContent = in.readString();
        choiceQuestionImage = in.readParcelable(Bitmap.class.getClassLoader());
        choiceQuestionOptionList = in.createTypedArrayList(ChoiceOptionModel.CREATOR);
        userAnswerList = in.createStringArrayList();
        correctAnswerList = in.createStringArrayList();
        choiceQuestionVirusId = in.readInt();
    }

    public static final Creator<ChoiceQuestionModel> CREATOR = new Creator<ChoiceQuestionModel>() {
        @Override
        public ChoiceQuestionModel createFromParcel(Parcel in) {
            return new ChoiceQuestionModel(in);
        }

        @Override
        public ChoiceQuestionModel[] newArray(int size) {
            return new ChoiceQuestionModel[size];
        }
    };

    public int getChoiceQuestionId() {
        return choiceQuestionId;
    }

    public void setChoiceQuestionId(int choiceQuestionId) {
        this.choiceQuestionId = choiceQuestionId;
    }

    public String getChoiceQuestionType() {
        return choiceQuestionType;
    }

    public void setChoiceQuestionType(String choiceQuestionType) {
        this.choiceQuestionType = choiceQuestionType;
    }

    public String getChoiceQuestionContent() {
        return choiceQuestionContent;
    }

    public void setChoiceQuestionContent(String choiceQuestionContent) {
        this.choiceQuestionContent = choiceQuestionContent;
    }

    public Bitmap getChoiceQuestionImage() {
        return choiceQuestionImage;
    }

    public void setChoiceQuestionImage(Bitmap choiceQuestionImage) {
        this.choiceQuestionImage = choiceQuestionImage;
    }

    public List<ChoiceOptionModel> getChoiceQuestionOptionList() {
        return choiceQuestionOptionList;
    }

    public void setChoiceQuestionOptionList(List<ChoiceOptionModel> choiceQuestionOptionList) {
        this.choiceQuestionOptionList = choiceQuestionOptionList;
    }

    public List<String> getUserAnswerList() {
        return userAnswerList;
    }

    public void setUserAnswerList(List<String> userAnswerList) {
        this.userAnswerList = userAnswerList;
    }

    public List<String> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setCorrectAnswerList(List<String> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
    }

    public int getChoiceQuestionVirusId() {
        return choiceQuestionVirusId;
    }

    public void setChoiceQuestionVirusId(int choiceQuestionVirusId) {
        this.choiceQuestionVirusId = choiceQuestionVirusId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(choiceQuestionId);
        dest.writeString(choiceQuestionType);
        dest.writeString(choiceQuestionContent);
        dest.writeParcelable(choiceQuestionImage, flags);
        dest.writeTypedList(choiceQuestionOptionList);
        dest.writeStringList(userAnswerList);
        dest.writeStringList(correctAnswerList);
        dest.writeInt(choiceQuestionVirusId);
    }
}
