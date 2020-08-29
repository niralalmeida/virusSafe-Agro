package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    private SingleChoiceQuestionAdapter singleChoiceQuestionAdapter;
    private RecyclerView recyclerViewForSingleChoiceQuestionModelList;
    private RecyclerView.LayoutManager layoutManagerForSingleChoiceQuestion;

    private MultipleChoiceQuestionAdapter multipleChoiceQuestionAdapter;
    private RecyclerView recyclerViewForMultipleChoiceQuestionModelList;
    private RecyclerView.LayoutManager layoutManagerForMultipleChoiceQuestion;

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
        singleChoiceQuestionAdapter = new SingleChoiceQuestionAdapter(singleChoiceQuestionModelList, requireActivity());
        recyclerViewForSingleChoiceQuestionModelList = view.findViewById(R.id.rv_virus_single_choice_quiz_question);
        recyclerViewForSingleChoiceQuestionModelList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForSingleChoiceQuestionModelList.setAdapter(singleChoiceQuestionAdapter);
        layoutManagerForSingleChoiceQuestion = new LinearLayoutManager(requireActivity());
        recyclerViewForSingleChoiceQuestionModelList.setLayoutManager(layoutManagerForSingleChoiceQuestion);
    }

    private void showVirusQuizMultipleChoiceQuestions() {
        multipleChoiceQuestionAdapter = new MultipleChoiceQuestionAdapter(multipleChoiceQuestionModelList, requireActivity());
        recyclerViewForMultipleChoiceQuestionModelList = view.findViewById(R.id.rv_virus_multiple_choice_quiz_question);
        recyclerViewForMultipleChoiceQuestionModelList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForMultipleChoiceQuestionModelList.setAdapter(multipleChoiceQuestionAdapter);
        layoutManagerForMultipleChoiceQuestion = new LinearLayoutManager(requireActivity());
        recyclerViewForMultipleChoiceQuestionModelList.setLayoutManager(layoutManagerForMultipleChoiceQuestion);
    }

    private void setSubmitAnswerButtonOnClickListener() {
        submitAnswerButton.setOnClickListener(view -> {
            if (checkAllQuestionsAreAnswered()){
                toQuizResultFragmentWithBundle();
            } else {
                Toast.makeText(requireActivity(), "Please answer all the questions before submit !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkAllQuestionsAreAnswered() {
        int singleChoiceQuestionCount = this.singleChoiceQuestionModelList.size();
        int multipleChoiceQuestionCount = this.multipleChoiceQuestionModelList.size();
        int questionCount = singleChoiceQuestionCount + multipleChoiceQuestionCount;
        int answeredQuestionCount = 0;
        for(int i = 0; i < singleChoiceQuestionCount; i++){
            SingleChoiceQuestionAdapter.ViewHolder singleChoiceViewHolder = (SingleChoiceQuestionAdapter.ViewHolder)recyclerViewForSingleChoiceQuestionModelList.findViewHolderForAdapterPosition(i);

            // find checked radio button
            RadioGroup genderRadioGroup = singleChoiceViewHolder.singleChoiceQuestionOptionsRadioGroup;
            int checkedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId != -1){
                answeredQuestionCount++;
            }
        }
        for (int i = 0; i < multipleChoiceQuestionCount; i++) {
            MultipleChoiceQuestionAdapter.ViewHolder multipleChoiceViewHolder = (MultipleChoiceQuestionAdapter.ViewHolder)recyclerViewForMultipleChoiceQuestionModelList.findViewHolderForAdapterPosition(i);

            LinearLayout multipleChoiceQuestionOptionsLinearLayout = multipleChoiceViewHolder.multipleChoiceQuestionOptionsLinearLayout;
            int checkboxCount = multipleChoiceQuestionOptionsLinearLayout.getChildCount();
            for (int k = 0; k < checkboxCount; k++){
                CheckBox checkBox = (CheckBox)multipleChoiceQuestionOptionsLinearLayout.getChildAt(k);
                if (checkBox.isChecked()){
                    answeredQuestionCount++;
                    break;
                }
            }
        }
        return questionCount == answeredQuestionCount;
    }

    private void toQuizResultFragmentWithBundle() {
        storeUserAnswerIntoLists();
        Bundle bundle = new Bundle();
        bundle.putParcelable("currentVirusModel", currentVirusModel);
        bundle.putSerializable("singleChoiceQuestionModelList", (Serializable) singleChoiceQuestionModelList);
        bundle.putSerializable("multipleChoiceQuestionModelList", (Serializable) multipleChoiceQuestionModelList);
        VirusQuizResultFragment virusQuizResultFragment = new VirusQuizResultFragment();
        virusQuizResultFragment.setArguments(bundle);
        FragmentOperator.replaceFragmentNoBackStack(requireActivity(), virusQuizResultFragment);
    }

    private void storeUserAnswerIntoLists() {
        for(int i = 0; i < singleChoiceQuestionModelList.size(); i++){
            SingleChoiceQuestionAdapter.ViewHolder singleChoiceViewHolder = (SingleChoiceQuestionAdapter.ViewHolder)recyclerViewForSingleChoiceQuestionModelList.findViewHolderForAdapterPosition(i);

            // find checked radio button and get answer label
            RadioGroup genderRadioGroup = singleChoiceViewHolder.singleChoiceQuestionOptionsRadioGroup;
            int checkedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = view.findViewById(checkedRadioButtonId);
            String answerOption = (String) radioButton.getText();
            String answerLabel = answerOption.substring(0, 1);
            singleChoiceQuestionModelList.get(i).setSingleChoiceQuestionAnswer(answerLabel);
        }
        for (int i = 0; i < multipleChoiceQuestionModelList.size(); i++) {
            MultipleChoiceQuestionAdapter.ViewHolder multipleChoiceViewHolder = (MultipleChoiceQuestionAdapter.ViewHolder)recyclerViewForMultipleChoiceQuestionModelList.findViewHolderForAdapterPosition(i);

            LinearLayout multipleChoiceQuestionOptionsLinearLayout = multipleChoiceViewHolder.multipleChoiceQuestionOptionsLinearLayout;
            int checkboxCount = multipleChoiceQuestionOptionsLinearLayout.getChildCount();
            for (int k = 0; k < checkboxCount; k++){
                CheckBox checkBox = (CheckBox)multipleChoiceQuestionOptionsLinearLayout.getChildAt(k);
                if (checkBox.isChecked()){
                    String answerOption = (String) checkBox.getText();
                    String answerLabel = answerOption.substring(0, 1);
                    multipleChoiceQuestionModelList.get(i).getMultipleChoiceQuestionAnswerList().add(answerLabel);
                }
            }
        }
    }
}
