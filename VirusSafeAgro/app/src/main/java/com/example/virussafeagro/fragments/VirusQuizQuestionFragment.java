package com.example.virussafeagro.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.NonSwipeableViewPager;
import com.example.virussafeagro.viewModel.VirusQuizQuestionViewModel;
import com.example.virussafeagro.viewModel.VirusQuizResultViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirusQuizQuestionFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private VirusQuizQuestionViewModel virusQuizQuestionViewModel;
    private VirusQuizResultViewModel virusQuizResultViewModel;
    private List<ChoiceQuestionModel> choiceQuestionModelList;
    private static TextView[] topDotsTextViewArray = new TextView[QuizQuestionSlideAdapter.QUESTION_COUNT]; // dots
    private static boolean isLastAnswerRight; // for dot color

    private LinearLayout processBarLinearLayout;
    private TextView virusFullNameTitleTextView;
    private NonSwipeableViewPager questionViewPager;
    private LinearLayout dotButtonsLinearLayout;
    private LinearLayout quizResultLinearLayout;
//    private LinearLayout slideBackButtonLinearLayout;
//    private ImageButton slideBackButton;

    private QuizQuestionSlideAdapter quizQuestionSlideAdapter;
    private int currentPagePosition;

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
        this.questionViewPager.setVisibility(View.GONE);

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // initialize view model
        this.initializeVirusQuizQuestionViewModel();
        // initialize view model
        this.initializeVirusQuizResultViewModel();
        // add dots
        this.addDotsIndicator(0);
        // find virus quiz list in new Thread
        this.findVirusQuizQuestionsFromDB();
        // observe VirusModel Quiz List Live Data
        this.observeVirusTwoTypeQuestionArrayLD();
        // back button
//        this.setSlideBackButtonOnClickListener();
    }

    private void initializeViews() {
        this.dotButtonsLinearLayout = view.findViewById(R.id.ll_dot_quiz_question);
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_quiz_question);
        this.virusFullNameTitleTextView = view.findViewById(R.id.tv_title_virus_full_name_quiz_question);
        this.virusFullNameTitleTextView.setText(this.currentVirusModel.getVirusFullName());
        this.questionViewPager = view.findViewById(R.id.slide_virus_quiz_question);
        this.quizResultLinearLayout = view.findViewById(R.id.ll_quiz_result_question);

        // initialize the dot array
        for (int i = 0; i < topDotsTextViewArray.length; i++) {
            topDotsTextViewArray[i] = new TextView(requireActivity());
            topDotsTextViewArray[i].setText(Html.fromHtml("&#8226 &nbsp"));
            topDotsTextViewArray[i].setTextSize(35);
            topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.colorGreyForDots));
            topDotsTextViewArray[i].setGravity(Gravity.TOP);
        }

//        this.slideBackButtonLinearLayout = view.findViewById(R.id.ll_slide_back_quiz_question);
//        this.slideBackButton = view.findViewById(R.id.btn_slide_back_quiz_question);
    }

    private void initializeVirusQuizQuestionViewModel() {
        this.virusQuizQuestionViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizQuestionViewModel.class);
    }
    private void initializeVirusQuizResultViewModel() {
        this.virusQuizResultViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizResultViewModel.class);
    }

    private void addDotsIndicator(int position) {
        dotButtonsLinearLayout.removeAllViews(); // clean the views

//        TextView questionProcessTextView = new TextView(requireActivity());
//        String questionProcessString = (position + 1) + " / " + QuizQuestionSlideAdapter.QUESTION_COUNT;
//        questionProcessTextView.setText(questionProcessString);
//        questionProcessTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorWhite));
//        questionProcessTextView.setTextSize(12);
//        dotButtonsLinearLayout.addView(questionProcessTextView);

        for (int i = 0; i < topDotsTextViewArray.length; i++) {
            if (i > position){
                topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.colorGreyForDots));
            } else if (i == position) {
                topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.colorWhite));
            } else if (i == position - 1){
                if (isLastAnswerRight){
                    topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.rightAnswer));
                } else {
                    topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.wrongAnswer));
                }
            }
            dotButtonsLinearLayout.addView(topDotsTextViewArray[i]);
        }

//        if (topDotsTextViewArray.length > 0) {
//            for (int p = 0; p <= position; p++){
//                topDotsTextViewArray[p].setTextColor(requireActivity().getResources().getColor(R.color.colorWhite));
//            }
//        }
    }

    private void findVirusQuizQuestionsFromDB() {
        this.virusQuizQuestionViewModel.processFindingVirusQuizQuestions(this.currentVirusModel.getVirusId());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void observeVirusTwoTypeQuestionArrayLD() {
        this.virusQuizQuestionViewModel.getQuizQuestionModelListLD().observe(getViewLifecycleOwner(), resultQuizQuestionModelList -> {
            if ((resultQuizQuestionModelList != null) && (resultQuizQuestionModelList.size() != 0)){

                // set recycler view linear layout visible and process bar invisible
                processBarLinearLayout.setVisibility(View.GONE);
                // show dots
                dotButtonsLinearLayout.startAnimation(MyAnimationBox.getAlphaAnimationForFadeIn(1000));
                dotButtonsLinearLayout.setVisibility(View.VISIBLE);
                // show slides (view pag)
                questionViewPager.startAnimation(MyAnimationBox.getAlphaAnimationForFadeIn(1000));
                questionViewPager.setVisibility(View.VISIBLE);

                // set question list
                choiceQuestionModelList = resultQuizQuestionModelList;
                // initialize the QuizQuestionSlideAdapter and ViewPager
                quizQuestionSlideAdapter = new QuizQuestionSlideAdapter(requireActivity(), choiceQuestionModelList);
                questionViewPager.setAdapter(quizQuestionSlideAdapter);
                // get the current slide position
                questionViewPager.addOnPageChangeListener(viewPagerListener);
                // slide to next page when the button in bottom sheet is clicked
                observeIsCorrectLD();
                // hide the view pager when it comes to the last question slide
                observeIsLastSlideLD();
            }
        });
    }

    // for each question slide result
    private void observeIsCorrectLD() {
        this.virusQuizResultViewModel.getIsCorrectLD().observe(getViewLifecycleOwner(), isCorrectLD -> {
            isLastAnswerRight = isCorrectLD;
            // swipe to the next slide
            questionViewPager.setCurrentItem(currentPagePosition + 1);
        });
    }

    // for the last question slide result
    private void observeIsLastSlideLD() {
        this.virusQuizResultViewModel.getIsLastSlideLD().observe(getViewLifecycleOwner(), isLastSlideLD -> {
            if(isLastSlideLD){
                // hide the dots -> GONE
                dotButtonsLinearLayout.setVisibility(View.GONE);
                // hide the view pager -> GONE
                questionViewPager.setVisibility(View.GONE);
                // show the final result view
                quizResultLinearLayout.startAnimation(MyAnimationBox.getAlphaAnimationForFadeIn(1000)); // set fade in animation
                quizResultLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPagePosition = position;
            // set dots
            addDotsIndicator(position);
            // set position in QuizQuestionSlideAdapter
            ((QuizQuestionSlideAdapter) Objects.requireNonNull(questionViewPager.getAdapter())).setCurrentQuestionSlidePosition(position);
            // control the visibility of the slide back button
//            if (position == 0){
//                slideBackButtonLinearLayout.setVisibility(View.INVISIBLE);
//                slideBackButton.setEnabled(false);
//            } else {
//                slideBackButtonLinearLayout.setVisibility(View.VISIBLE);
//                slideBackButton.setEnabled(true);
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

//    private void setSlideBackButtonOnClickListener() {
//        this.slideBackButton.setOnClickListener(view -> {
//            // swipe to the previous slide
//            questionViewPager.setCurrentItem(currentPagePosition - 1);
//        });
//    }

    @Override
    public void onPause() {
        super.onPause();
        this.virusQuizQuestionViewModel.getQuizQuestionModelListLD().removeObservers(getViewLifecycleOwner());
        List<ChoiceQuestionModel> choiceQuestionModelList = new ArrayList<>();
        this.virusQuizQuestionViewModel.setQuizQuestionModelListLD(choiceQuestionModelList);

        this.virusQuizResultViewModel.getIsCorrectLD().removeObservers(getViewLifecycleOwner());
//        this.virusQuizResultViewModel.setIsCorrectLD(false);

        this.virusQuizResultViewModel.getIsLastSlideLD().removeObservers(getViewLifecycleOwner());
//        this.virusQuizResultViewModel.setIsLastSlideLD(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppAuthentication.setAuthenticationAsNo((AppCompatActivity)requireActivity());
    }
}
