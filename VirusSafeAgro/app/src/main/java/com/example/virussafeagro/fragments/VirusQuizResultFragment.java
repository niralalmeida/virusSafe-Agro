//package com.example.virussafeagro.fragments;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.widget.NestedScrollView;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.virussafeagro.MainActivity;
//import com.example.virussafeagro.R;
//import com.example.virussafeagro.adapters.MultipleChoiceResultAdapter;
//import com.example.virussafeagro.adapters.SingleChoiceResultAdapter;
//import com.example.virussafeagro.models.ChoiceQuestionCorrectAnswerModel;
//import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
//import com.example.virussafeagro.models.SingleChoiceQuestionModel;
//import com.example.virussafeagro.models.VirusModel;
//import com.example.virussafeagro.uitilities.AppResources;
//import com.example.virussafeagro.uitilities.FragmentOperator;
//import com.example.virussafeagro.viewModel.VirusQuizResultViewModel;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class VirusQuizResultFragment extends Fragment {
//    private View view;
//    private VirusModel currentVirusModel;
//
//    private VirusQuizResultViewModel virusQuizResultViewModel;
//    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
//    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();
//    private List<ChoiceQuestionCorrectAnswerModel> choiceQuestionCorrectAnswerModelList = new ArrayList<>();
//
//    private LinearLayout processBarLinearLayout;
//    private NestedScrollView recyclerViewNestedScrollView;
//
//    private TextView virusFullNameTitleTextView;
//    private LinearLayout singleChoiceQuestionTitleLinearLayout;
//    private LinearLayout multipleChoiceQuestionTitleLinearLayout;
//    private Button retakeQuizButton;
//    private Button reviewButton;
//
//    private SingleChoiceResultAdapter singleChoiceResultAdapter;
//    private RecyclerView recyclerViewForSingleChoiceQuestionModelList;
//    private RecyclerView.LayoutManager layoutManagerForSingleChoiceQuestion;
//
//    private MultipleChoiceResultAdapter multipleChoiceResultAdapter;
//    private RecyclerView recyclerViewForMultipleChoiceQuestionModelList;
//    private RecyclerView.LayoutManager layoutManagerForMultipleChoiceQuestion;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Inflate the View for this fragment
//        this.view = inflater.inflate(R.layout.fragment_virus_quiz_result, container, false);
//
//        // set title
//        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Quiz Result");
//
//
//        // show back button
//        MainActivity.showTopActionBar((MainActivity)requireActivity());
//
//        // get passed bundle and the VirusModel within it
//        Bundle bundle = getArguments();
//        assert bundle != null;
//        this.currentVirusModel = bundle.getParcelable("currentVirusModel");
//        this.singleChoiceQuestionModelList = (List<SingleChoiceQuestionModel>)bundle.getSerializable("singleChoiceQuestionModelList");
//        this.multipleChoiceQuestionModelList = (List<MultipleChoiceQuestionModel>) bundle.getSerializable("multipleChoiceQuestionModelList");
//
//        // initialize views
//        this.initializeViews();
//        this.processBarLinearLayout.setVisibility(View.VISIBLE);
//        this.recyclerViewNestedScrollView.setVisibility(View.INVISIBLE);
//
//        return this.view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // add retake quiz button on click listener
//        this.setRetakeQuizButtonOnClickListener();
//        // add review button on click listener
//        this.setReviewButtonOnClickListener();
//
//        // initialize view model
//        this.initializeVirusQuizResultViewModel();
//        // find virus answers list in new Thread
//        this.findCorrectAnswersFromDB();
//        // observe VirusModel answers Live Data
//        this.observeCorrectAnswerListLD();
//    }
//
//    private void initializeViews() {
//        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_quiz_result);
//        this.recyclerViewNestedScrollView = view.findViewById(R.id.nsv_virus_quiz_result);
//        this.virusFullNameTitleTextView = view.findViewById(R.id.tv_title_virus_full_name_quiz_result);
//        this.virusFullNameTitleTextView.setText(this.currentVirusModel.getVirusFullName());
//        this.singleChoiceQuestionTitleLinearLayout = view.findViewById(R.id.ll_virus_single_choice_quiz_result);
//        this.multipleChoiceQuestionTitleLinearLayout = view.findViewById(R.id.ll_virus_multiple_choice_quiz_result);
//        this.retakeQuizButton = view.findViewById(R.id.btn_retake_quiz_result);
//        this.reviewButton = view.findViewById(R.id.btn_review_virus_quiz_result);
//    }
//
//    private void initializeVirusQuizResultViewModel() {
//        this.virusQuizResultViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizResultViewModel.class);
//        this.virusQuizResultViewModel.initiateTheContext(requireActivity());
//    }
//
//    private void findCorrectAnswersFromDB() {
//        this.virusQuizResultViewModel.processFindingCorrectAnswers(this.currentVirusModel.getVirusId());
//    }
//
//    private void observeCorrectAnswerListLD() {
//        this.virusQuizResultViewModel.getCorrectAnswerListLD().observe(getViewLifecycleOwner(), resultCorrectAnswerList -> {
//            if (resultCorrectAnswerList != null && resultCorrectAnswerList.size() != 0) {
//                this.choiceQuestionCorrectAnswerModelList = resultCorrectAnswerList;
//
//                // set recycler view linear layout visible and process bar invisible
//                processBarLinearLayout.setVisibility(View.INVISIBLE);
//                recyclerViewNestedScrollView.setVisibility(View.VISIBLE);
//
//                if (this.singleChoiceQuestionModelList.size() != 0) {
//                    singleChoiceQuestionTitleLinearLayout.setVisibility(View.VISIBLE);
//                    showVirusQuizSingleChoiceQuestions();
//                }
//                if (this.multipleChoiceQuestionModelList.size() != 0) {
//                    multipleChoiceQuestionTitleLinearLayout.setVisibility(View.VISIBLE);
//                    showVirusQuizMultipleChoiceQuestions();
//                }
//            }
//        });
//    }
//
//    private void setRetakeQuizButtonOnClickListener() {
//        this.retakeQuizButton.setOnClickListener(view -> {
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("currentVirusModel", currentVirusModel);
//            VirusQuizQuestionFragment virusQuizQuestionFragment = new VirusQuizQuestionFragment();
//            virusQuizQuestionFragment.setArguments(bundle);
//            FragmentOperator.replaceFragment(requireActivity(), virusQuizQuestionFragment, AppResources.FRAGMENT_TAG_VIRUS_QUIZ_QUESTION);
//        });
//    }
//
//    private void setReviewButtonOnClickListener() {
//        this.reviewButton.setOnClickListener(view -> {
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("currentVirusModel", currentVirusModel);
//            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
//            virusDetailFragment.setArguments(bundle);
//            FragmentOperator.replaceFragment(requireActivity(), virusDetailFragment, AppResources.FRAGMENT_TAG_VIRUS_DETAIL);
//        });
//    }
//
//    private void showVirusQuizSingleChoiceQuestions() {
//        singleChoiceResultAdapter = new SingleChoiceResultAdapter(singleChoiceQuestionModelList, choiceQuestionCorrectAnswerModelList, requireActivity());
//        recyclerViewForSingleChoiceQuestionModelList = view.findViewById(R.id.rv_virus_single_choice_quiz_result);
//        recyclerViewForSingleChoiceQuestionModelList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
//        recyclerViewForSingleChoiceQuestionModelList.setAdapter(singleChoiceResultAdapter);
//        layoutManagerForSingleChoiceQuestion = new LinearLayoutManager(requireActivity());
//        recyclerViewForSingleChoiceQuestionModelList.setLayoutManager(layoutManagerForSingleChoiceQuestion);
//    }
//
//    private void showVirusQuizMultipleChoiceQuestions() {
//        multipleChoiceResultAdapter = new MultipleChoiceResultAdapter(multipleChoiceQuestionModelList, choiceQuestionCorrectAnswerModelList, requireActivity());
//        recyclerViewForMultipleChoiceQuestionModelList = view.findViewById(R.id.rv_virus_multiple_choice_quiz_result);
//        recyclerViewForMultipleChoiceQuestionModelList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
//        recyclerViewForMultipleChoiceQuestionModelList.setAdapter(multipleChoiceResultAdapter);
//        layoutManagerForMultipleChoiceQuestion = new LinearLayoutManager(requireActivity());
//        recyclerViewForMultipleChoiceQuestionModelList.setLayoutManager(layoutManagerForMultipleChoiceQuestion);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        this.virusQuizResultViewModel.getCorrectAnswerListLD().removeObservers(getViewLifecycleOwner());
//        this.virusQuizResultViewModel.setCorrectAnswerListLD(new ArrayList<>());
//    }
//}
