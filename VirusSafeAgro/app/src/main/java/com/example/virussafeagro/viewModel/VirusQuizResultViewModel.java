package com.example.virussafeagro.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VirusQuizResultViewModel extends ViewModel {

    private MutableLiveData<Boolean> isCorrectLD; // for each question slide
    private MutableLiveData<Boolean> isLastSlideLD; // for last question slide

    public VirusQuizResultViewModel() {
        this.isCorrectLD = new MutableLiveData<>();
        this.isLastSlideLD = new MutableLiveData<>();
    }
    
    public void setIsCorrectLD(Boolean isCorrect){
        this.isCorrectLD.setValue(isCorrect);
    }
    public LiveData<Boolean> getIsCorrectLD(){
        return this.isCorrectLD;
    }

    public void setIsLastSlideLD(Boolean isLastSlide){
        this.isLastSlideLD.setValue(isLastSlide);
    }
    public LiveData<Boolean> getIsLastSlideLD(){
        return this.isLastSlideLD;
    }
}
