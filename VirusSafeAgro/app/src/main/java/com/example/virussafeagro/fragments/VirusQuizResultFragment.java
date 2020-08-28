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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.MultipleChoiceResultAdapter;
import com.example.virussafeagro.adapters.SingleChoiceResultAdapter;
import com.example.virussafeagro.models.ChoiceQuestionCorrectAnswerModel;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.FragmentOperator;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizResultFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();
    private List<ChoiceQuestionCorrectAnswerModel> choiceQuestionCorrectAnswerModelList = new ArrayList<>();

    private TextView virusFullNameTitleTextView;
    private LinearLayout singleChoiceQuestionTitleLinearLayout;
    private LinearLayout multipleChoiceQuestionTitleLinearLayout;
    private Button reviewButton;

    private SingleChoiceResultAdapter singleChoiceResultAdapter;
    private RecyclerView recyclerViewForSingleChoiceQuestionModelList;
    private RecyclerView.LayoutManager layoutManagerForSingleChoiceQuestion;

    private MultipleChoiceResultAdapter multipleChoiceResultAdapter;
    private RecyclerView recyclerViewForMultipleChoiceQuestionModelList;
    private RecyclerView.LayoutManager layoutManagerForMultipleChoiceQuestion;

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
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setReviewButtonOnClickListener();
    }

    private void initializeViews() {
        this.virusFullNameTitleTextView = view.findViewById(R.id.tv_title_virus_full_name_quiz_result);
        this.virusFullNameTitleTextView.setText(this.currentVirusModel.getVirusFullName());
        this.singleChoiceQuestionTitleLinearLayout = view.findViewById(R.id.ll_virus_single_choice_quiz_result);
        this.multipleChoiceQuestionTitleLinearLayout = view.findViewById(R.id.ll_virus_multiple_choice_quiz_result);
        this.reviewButton = view.findViewById(R.id.btn_review_virus_quiz_result);
        if (this.singleChoiceQuestionModelList.size() != 0) {
            singleChoiceQuestionTitleLinearLayout.setVisibility(View.VISIBLE);
            showVirusQuizSingleChoiceQuestions();
        }
        if (this.multipleChoiceQuestionModelList.size() != 0) {
            multipleChoiceQuestionTitleLinearLayout.setVisibility(View.VISIBLE);
            showVirusQuizMultipleChoiceQuestions();
        }
    }

    private void setReviewButtonOnClickListener() {
        this.reviewButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
            virusDetailFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentNoBackStack(requireActivity(), virusDetailFragment);
        });
    }

    private void showVirusQuizSingleChoiceQuestions() {
        singleChoiceResultAdapter = new SingleChoiceResultAdapter(singleChoiceQuestionModelList, requireActivity());
        recyclerViewForSingleChoiceQuestionModelList = view.findViewById(R.id.rv_virus_single_choice_quiz_result);
        recyclerViewForSingleChoiceQuestionModelList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForSingleChoiceQuestionModelList.setAdapter(singleChoiceResultAdapter);
        layoutManagerForSingleChoiceQuestion = new LinearLayoutManager(requireActivity());
        recyclerViewForSingleChoiceQuestionModelList.setLayoutManager(layoutManagerForSingleChoiceQuestion);
    }

    private void showVirusQuizMultipleChoiceQuestions() {
        multipleChoiceResultAdapter = new MultipleChoiceResultAdapter(multipleChoiceQuestionModelList, requireActivity());
        recyclerViewForMultipleChoiceQuestionModelList = view.findViewById(R.id.rv_virus_multiple_choice_quiz_result);
        recyclerViewForMultipleChoiceQuestionModelList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForMultipleChoiceQuestionModelList.setAdapter(multipleChoiceResultAdapter);
        layoutManagerForMultipleChoiceQuestion = new LinearLayoutManager(requireActivity());
        recyclerViewForMultipleChoiceQuestionModelList.setLayoutManager(layoutManagerForMultipleChoiceQuestion);
    }
}
