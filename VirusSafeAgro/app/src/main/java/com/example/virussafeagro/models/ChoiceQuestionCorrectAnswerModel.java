package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ChoiceQuestionCorrectAnswerModel implements Parcelable {
    private int choiceQuestionId;
    private String choiceQuestionType;
    private List<String> correctAnswerList;

    public ChoiceQuestionCorrectAnswerModel() {
    }

    public ChoiceQuestionCorrectAnswerModel(int choiceQuestionId) {
        this.choiceQuestionId = choiceQuestionId;
    }

    public ChoiceQuestionCorrectAnswerModel(int choiceQuestionId, String choiceQuestionType, List<String> correctAnswerList) {
        this.choiceQuestionId = choiceQuestionId;
        this.choiceQuestionType = choiceQuestionType;
        this.correctAnswerList = correctAnswerList;
    }

    protected ChoiceQuestionCorrectAnswerModel(Parcel in) {
        choiceQuestionId = in.readInt();
        choiceQuestionType = in.readString();
        correctAnswerList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(choiceQuestionId);
        dest.writeString(choiceQuestionType);
        dest.writeStringList(correctAnswerList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChoiceQuestionCorrectAnswerModel> CREATOR = new Creator<ChoiceQuestionCorrectAnswerModel>() {
        @Override
        public ChoiceQuestionCorrectAnswerModel createFromParcel(Parcel in) {
            return new ChoiceQuestionCorrectAnswerModel(in);
        }

        @Override
        public ChoiceQuestionCorrectAnswerModel[] newArray(int size) {
            return new ChoiceQuestionCorrectAnswerModel[size];
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

    public List<String> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setCorrectAnswerList(List<String> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
    }
}
