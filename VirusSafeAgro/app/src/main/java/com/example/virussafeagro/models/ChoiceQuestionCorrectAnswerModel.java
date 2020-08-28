package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ChoiceQuestionCorrectAnswerModel implements Parcelable {
    private int choiceQuestionId;
    private List<String> correctAnswerList;

    public ChoiceQuestionCorrectAnswerModel() {
    }

    public ChoiceQuestionCorrectAnswerModel(int choiceQuestionId, List<String> correctAnswerList) {
        this.choiceQuestionId = choiceQuestionId;
        this.correctAnswerList = correctAnswerList;
    }

    protected ChoiceQuestionCorrectAnswerModel(Parcel in) {
        choiceQuestionId = in.readInt();
        correctAnswerList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(choiceQuestionId);
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

    public List<String> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setCorrectAnswerList(List<String> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
    }
}
