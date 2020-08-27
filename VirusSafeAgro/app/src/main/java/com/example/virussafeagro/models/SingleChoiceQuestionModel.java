package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SingleChoiceQuestionModel implements Parcelable {
    private int singleChoiceQuestionId;
    private String singleChoiceQuestionContent;
    private List<String> singleChoiceQuestionOptionList;
    private String singleChoiceQuestionAnswer;
    private int singleChoiceQuestionVirusId;

    public SingleChoiceQuestionModel() {
    }

    public SingleChoiceQuestionModel(int singleChoiceQuestionId, String singleChoiceQuestionContent, List<String> singleChoiceQuestionOptionList, String singleChoiceQuestionAnswer, int singleChoiceQuestionVirusId) {
        this.singleChoiceQuestionId = singleChoiceQuestionId;
        this.singleChoiceQuestionContent = singleChoiceQuestionContent;
        this.singleChoiceQuestionOptionList = singleChoiceQuestionOptionList;
        this.singleChoiceQuestionAnswer = singleChoiceQuestionAnswer;
        this.singleChoiceQuestionVirusId = singleChoiceQuestionVirusId;
    }

    protected SingleChoiceQuestionModel(Parcel in) {
        singleChoiceQuestionId = in.readInt();
        singleChoiceQuestionContent = in.readString();
        singleChoiceQuestionOptionList = in.createStringArrayList();
        singleChoiceQuestionAnswer = in.readString();
        singleChoiceQuestionVirusId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(singleChoiceQuestionId);
        dest.writeString(singleChoiceQuestionContent);
        dest.writeStringList(singleChoiceQuestionOptionList);
        dest.writeString(singleChoiceQuestionAnswer);
        dest.writeInt(singleChoiceQuestionVirusId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SingleChoiceQuestionModel> CREATOR = new Creator<SingleChoiceQuestionModel>() {
        @Override
        public SingleChoiceQuestionModel createFromParcel(Parcel in) {
            return new SingleChoiceQuestionModel(in);
        }

        @Override
        public SingleChoiceQuestionModel[] newArray(int size) {
            return new SingleChoiceQuestionModel[size];
        }
    };

    public int getSingleChoiceQuestionId() {
        return singleChoiceQuestionId;
    }

    public void setSingleChoiceQuestionId(int singleChoiceQuestionId) {
        this.singleChoiceQuestionId = singleChoiceQuestionId;
    }

    public String getSingleChoiceQuestionContent() {
        return singleChoiceQuestionContent;
    }

    public void setSingleChoiceQuestionContent(String singleChoiceQuestionContent) {
        this.singleChoiceQuestionContent = singleChoiceQuestionContent;
    }

    public List<String> getSingleChoiceQuestionOptionList() {
        return singleChoiceQuestionOptionList;
    }

    public void setSingleChoiceQuestionOptionList(List<String> singleChoiceQuestionOptionList) {
        this.singleChoiceQuestionOptionList = singleChoiceQuestionOptionList;
    }

    public String getSingleChoiceQuestionAnswer() {
        return singleChoiceQuestionAnswer;
    }

    public void setSingleChoiceQuestionAnswer(String singleChoiceQuestionAnswer) {
        this.singleChoiceQuestionAnswer = singleChoiceQuestionAnswer;
    }

    public int getSingleChoiceQuestionVirusId() {
        return singleChoiceQuestionVirusId;
    }

    public void setSingleChoiceQuestionVirusId(int singleChoiceQuestionVirusId) {
        this.singleChoiceQuestionVirusId = singleChoiceQuestionVirusId;
    }
}