package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.MultipleChoiceQuestionAdapter;
import com.example.virussafeagro.adapters.SingleChoiceQuestionAdapter;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;

import java.util.List;

public class VirusQuizQuestionFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList;
    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList;

    private TextView virusFullNameTitleTextView;
    private Button submitAnswerButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_quiz_question, container, false);

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirusModel = bundle.getParcelable("currentVirusModel");
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // bind virus full name to the title
    private void initializeViews() {
        this.virusFullNameTitleTextView = view.findViewById(R.id.tv_title_virus_full_name_quiz_question);
        this.virusFullNameTitleTextView.setText(this.currentVirusModel.getVirusFullName());
    }

    private void showVirusQuizSingleChoiceQuestions() {
        SingleChoiceQuestionAdapter singleChoiceQuestionAdapter = new SingleChoiceQuestionAdapter(singleChoiceQuestionModelList, requireActivity());
        RecyclerView recyclerViewForSingleChoiceQuestionModelList = view.findViewById(R.id.rv_virus_single_choice_quiz_question);
        recyclerViewForSingleChoiceQuestionModelList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForSingleChoiceQuestionModelList.setAdapter(singleChoiceQuestionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForSingleChoiceQuestionModelList.setLayoutManager(layoutManager);
    }

    private void showVirusQuizMultipleChoiceQuestions() {
        MultipleChoiceQuestionAdapter multipleChoiceQuestionAdapter = new MultipleChoiceQuestionAdapter(multipleChoiceQuestionModelList, requireActivity());
        RecyclerView recyclerViewForMultipleChoiceQuestionModelList = view.findViewById(R.id.rv_virus_multiple_choice_quiz_question);
        recyclerViewForMultipleChoiceQuestionModelList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForMultipleChoiceQuestionModelList.setAdapter(multipleChoiceQuestionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForMultipleChoiceQuestionModelList.setLayoutManager(layoutManager);
    }
}
