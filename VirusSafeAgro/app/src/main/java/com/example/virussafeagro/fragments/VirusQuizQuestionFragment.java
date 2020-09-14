package com.example.virussafeagro.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.viewpager.widget.ViewPager;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.MultipleChoiceQuestionAdapter;
import com.example.virussafeagro.adapters.QuizQuestionSlideAdapter;
import com.example.virussafeagro.adapters.SingleChoiceQuestionAdapter;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppAuthentication;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.NonSwipeableViewPager;
import com.example.virussafeagro.viewModel.VirusQuizQuestionViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirusQuizQuestionFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private VirusQuizQuestionViewModel virusQuizQuestionViewModel;
    private List<ChoiceQuestionModel> choiceQuestionModelList;

    private LinearLayout processBarLinearLayout;
    private TextView virusFullNameTitleTextView;
    private NonSwipeableViewPager questionViewPager;

    private QuizQuestionSlideAdapter quizQuestionSlideAdapter;

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
        this.questionViewPager.setVisibility(View.INVISIBLE);

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
        this.questionViewPager = view.findViewById(R.id.slide_virus_quiz_question);
    }

    private void initializeVirusQuizQuestionViewModel() {
        this.virusQuizQuestionViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizQuestionViewModel.class);
    }

    private void findVirusQuizQuestionsFromDB() {
        this.virusQuizQuestionViewModel.processFindingVirusQuizQuestions(this.currentVirusModel.getVirusId());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void observeVirusTwoTypeQuestionArrayLD() {
        this.virusQuizQuestionViewModel.getQuizQuestionModelListLD().observe(getViewLifecycleOwner(), resultQuizQuestionModelList -> {
            if ((resultQuizQuestionModelList != null) && (resultQuizQuestionModelList.size() != 0)){
                // set recycler view linear layout visible and process bar invisible
                processBarLinearLayout.setVisibility(View.INVISIBLE);
                questionViewPager.setVisibility(View.VISIBLE);
                // set question list
                choiceQuestionModelList = resultQuizQuestionModelList;
                // initialize the QuizQuestionSlideAdapter and ViewPager
                quizQuestionSlideAdapter = new QuizQuestionSlideAdapter(requireActivity(), choiceQuestionModelList);
                questionViewPager.setAdapter(quizQuestionSlideAdapter);

                // test
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.virusQuizQuestionViewModel.getQuizQuestionModelListLD().removeObservers(getViewLifecycleOwner());
        List<ChoiceQuestionModel> choiceQuestionModelList = new ArrayList<>();
        this.virusQuizQuestionViewModel.setQuizQuestionModelListLD(choiceQuestionModelList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppAuthentication.setAuthenticationAsNo((AppCompatActivity)requireActivity());
    }
}
