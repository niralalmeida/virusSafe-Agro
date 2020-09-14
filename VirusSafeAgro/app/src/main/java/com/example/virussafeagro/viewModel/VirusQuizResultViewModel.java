package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.ChoiceQuestionCorrectAnswerModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizResultViewModel extends ViewModel {

    private MutableLiveData<Boolean> isCorrectLD;

    public VirusQuizResultViewModel() {
        this.isCorrectLD = new MutableLiveData<>();
    }
    
    public void setIsCorrectLD(Boolean isCorrectL){
        this.isCorrectLD.setValue(isCorrectL);
    }
    public LiveData<Boolean> getIsCorrectLD(){
        return this.isCorrectLD;
    }
}
