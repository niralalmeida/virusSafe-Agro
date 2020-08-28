package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizResultFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_quiz_result, container, false);

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirusModel = bundle.getParcelable("currentVirusModel");
        this.singleChoiceQuestionModelList = (List<SingleChoiceQuestionModel>)bundle.getSerializable("singleChoiceQuestionModelList");
        this.multipleChoiceQuestionModelList = (List<MultipleChoiceQuestionModel>) bundle.getSerializable("multipleChoiceQuestionModelList");
//        this.initializeViews();


        return this.view;
    }
}
