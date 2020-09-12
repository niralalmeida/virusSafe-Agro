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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.MultipleChoiceQuestionAdapter;
import com.example.virussafeagro.adapters.SingleChoiceQuestionAdapter;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.viewModel.VirusQuizQuestionViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirusQuizQuestionFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private VirusQuizQuestionViewModel virusQuizQuestionViewModel;
    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();

    private LinearLayout processBarLinearLayout;
    private NestedScrollView recyclerViewNestedScrollView;

    private TextView virusFullNameTitleTextView;
    private LinearLayout singleChoiceQuestionTitleLinearLayout;
    private LinearLayout multipleChoiceQuestionTitleLinearLayout;
    private Button submitAnswerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_quiz_question, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Take Quiz");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirusModel = bundle.getParcelable("currentVirusModel");

        // initialize views
        this.initializeViews();
        this.processBarLinearLayout.setVisibility(View.VISIBLE);
        this.recyclerViewNestedScrollView.setVisibility(View.INVISIBLE);

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
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_quiz_question);
        this.virusFullNameTitleTextView = view.findViewById(R.id.tv_title_virus_full_name_quiz_question);
        this.virusFullNameTitleTextView.setText(this.currentVirusModel.getVirusFullName());
    }

    private void initializeVirusQuizQuestionViewModel() {
        this.virusQuizQuestionViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizQuestionViewModel.class);
    }

    private void findVirusQuizQuestionsFromDB() {
        this.virusQuizQuestionViewModel.processFindingVirusQuizQuestions(this.currentVirusModel.getVirusId());
    }

    private void observeVirusTwoTypeQuestionArrayLD() {
        this.virusQuizQuestionViewModel.getQuizQuestionModelListLD().observe(getViewLifecycleOwner(), resultVirusTwoTypeQuestionArray -> {
//            if ((resultVirusTwoTypeQuestionArray[0] != null) && (resultVirusTwoTypeQuestionArray[1] != null)){
//                if ((resultVirusTwoTypeQuestionArray[0].size() != 0) || (resultVirusTwoTypeQuestionArray[1].size() != 0)){
//                    // set recycler view linear layout visible and process bar invisible
//                    processBarLinearLayout.setVisibility(View.INVISIBLE);
//                    recyclerViewNestedScrollView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            if (resultVirusTwoTypeQuestionArray[0] != null && resultVirusTwoTypeQuestionArray[0].size() != 0){
//                singleChoiceQuestionModelList.clear();
//                singleChoiceQuestionModelList = resultVirusTwoTypeQuestionArray[0];
//
//                singleChoiceQuestionTitleLinearLayout.setVisibility(View.VISIBLE);
//            }
//            if (resultVirusTwoTypeQuestionArray[1] != null && resultVirusTwoTypeQuestionArray[1].size() != 0){
//                multipleChoiceQuestionModelList.clear();
//                multipleChoiceQuestionModelList = resultVirusTwoTypeQuestionArray[1];
//
//                multipleChoiceQuestionTitleLinearLayout.setVisibility(View.VISIBLE);
//            }
//            if (singleChoiceQuestionModelList.size() != 0 || multipleChoiceQuestionModelList.size() != 0) {
//                submitAnswerButton.setVisibility(View.VISIBLE);
//            }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.virusQuizQuestionViewModel.getQuizQuestionModelListLD().removeObservers(getViewLifecycleOwner());
        List<ChoiceQuestionModel> choiceQuestionModelList = new ArrayList<>();
        this.virusQuizQuestionViewModel.setQuizQuestionModelListLD(choiceQuestionModelList);
    }
}
