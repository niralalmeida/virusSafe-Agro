package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MultipleChoiceQuestionModel implements Parcelable {
    private int multipleChoiceQuestionId;
    private String multipleChoiceQuestionContent;
    private List<String> multipleChoiceQuestionOptionList;
    private List<String> multipleChoiceQuestionAnswerList;
    private int multipleChoiceQuestionVirusId;

    public MultipleChoiceQuestionModel() {
    }

    public MultipleChoiceQuestionModel(int multipleChoiceQuestionId, String multipleChoiceQuestionContent, List<String> multipleChoiceQuestionOptionList, List<String> multipleChoiceQuestionAnswerList, int multipleChoiceQuestionVirusId) {
        this.multipleChoiceQuestionId = multipleChoiceQuestionId;
        this.multipleChoiceQuestionContent = multipleChoiceQuestionContent;
        this.multipleChoiceQuestionOptionList = multipleChoiceQuestionOptionList;
        this.multipleChoiceQuestionAnswerList = multipleChoiceQuestionAnswerList;
        this.multipleChoiceQuestionVirusId = multipleChoiceQuestionVirusId;
    }

    protected MultipleChoiceQuestionModel(Parcel in) {
        multipleChoiceQuestionId = in.readInt();
        multipleChoiceQuestionContent = in.readString();
        multipleChoiceQuestionOptionList = in.createStringArrayList();
        multipleChoiceQuestionAnswerList = in.createStringArrayList();
        multipleChoiceQuestionVirusId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(multipleChoiceQuestionId);
        dest.writeString(multipleChoiceQuestionContent);
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

    public int getMultipleChoiceQuestionId() {
        return multipleChoiceQuestionId;
    }

    public void setMultipleChoiceQuestionId(int multipleChoiceQuestionId) {
        this.multipleChoiceQuestionId = multipleChoiceQuestionId;
    }

    public String getMultipleChoiceQuestionContent() {
        return multipleChoiceQuestionContent;
    }

    public void setMultipleChoiceQuestionContent(String multipleChoiceQuestionContent) {
        this.multipleChoiceQuestionContent = multipleChoiceQuestionContent;
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
