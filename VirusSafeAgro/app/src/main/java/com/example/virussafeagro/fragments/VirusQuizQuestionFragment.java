package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.MultipleChoiceQuestionAdapter;
import com.example.virussafeagro.adapters.SingleChoiceQuestionAdapter;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.viewModel.VirusQuizQuestionViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VirusQuizQuestionFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private VirusQuizQuestionViewModel virusQuizQuestionViewModel;

    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();

    private TextView virusFullNameTitleTextView;
    private LinearLayout singleChoiceQuestionTitleLinearLayout;
    private LinearLayout multipleChoiceQuestionTitleLinearLayout;
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

        // initialize view model
        this.initializeVirusQuizQuestionViewModel();
        // find virus quiz list in new Thread
        this.findVirusQuizQuestionsFromDB();
        // observe VirusModel Quiz List Live Data
        this.observeVirusTwoTypeQuestionArrayLD();
    }

    // bind virus full name to the title
    private void initializeViews() {
        this.virusFullNameTitleTextView = view.findViewById(R.id.tv_title_virus_full_name_quiz_question);
        this.virusFullNameTitleTextView.setText(this.currentVirusModel.getVirusFullName());
        this.singleChoiceQuestionTitleLinearLayout = view.findViewById(R.id.ll_virus_single_choice_quiz_question);
        this.multipleChoiceQuestionTitleLinearLayout = view.findViewById(R.id.ll_virus_multiple_choice_quiz_question);
        this.submitAnswerButton = view.findViewById(R.id.btn_submit_answer_virus_quiz_question);
    }

    private void initializeVirusQuizQuestionViewModel() {
        this.virusQuizQuestionViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizQuestionViewModel.class);
        this.virusQuizQuestionViewModel.initiateTheContext(requireActivity());
    }

    private void findVirusQuizQuestionsFromDB() {
        this.virusQuizQuestionViewModel.processFindingVirusQuizQuestions(this.currentVirusModel.getVirusId());
    }

    private void observeVirusTwoTypeQuestionArrayLD() {
        this.virusQuizQuestionViewModel.getVirusTwoTypeQuestionArrayLD().observe(getViewLifecycleOwner(), resultVirusTwoTypeQuestionArray -> {
            if (resultVirusTwoTypeQuestionArray[0] != null && resultVirusTwoTypeQuestionArray[0].size() != 0){
                singleChoiceQuestionModelList = resultVirusTwoTypeQuestionArray[0];

                singleChoiceQuestionTitleLinearLayout.setVisibility(View.VISIBLE);
                showVirusQuizSingleChoiceQuestions();
            }
            if (resultVirusTwoTypeQuestionArray[1] != null && resultVirusTwoTypeQuestionArray[1].size() != 0){
                multipleChoiceQuestionModelList = resultVirusTwoTypeQuestionArray[1];

                multipleChoiceQuestionTitleLinearLayout.setVisibility(View.VISIBLE);
                showVirusQuizMultipleChoiceQuestions();
            }
            if (singleChoiceQuestionModelList.size() != 0 || multipleChoiceQuestionModelList.size() != 0) {
                submitAnswerButton.setVisibility(View.VISIBLE);
                setSubmitAnswerButtonOnClickListener();
            }

        });
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

    private void setSubmitAnswerButtonOnClickListener() {
        submitAnswerButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            bundle.putSerializable("singleChoiceQuestionModelList", (Serializable) singleChoiceQuestionModelList);
            bundle.putSerializable("multipleChoiceQuestionModelList", (Serializable) multipleChoiceQuestionModelList);
            VirusQuizResultFragment virusQuizResultFragment = new VirusQuizResultFragment();
            virusQuizResultFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentNoBackStack(requireActivity(), virusQuizResultFragment);
        });
    }
}
