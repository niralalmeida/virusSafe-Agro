package com.example.virussafeagro.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.QuizQuestionSlideAdapter;
import com.example.virussafeagro.adapters.ListQuizResultAdapter;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppAuthentication;
import com.example.virussafeagro.uitilities.DataComparison;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.uitilities.MyProgressBar;
import com.example.virussafeagro.uitilities.NonSwipeableViewPager;
import com.example.virussafeagro.viewModel.VirusQuizQuestionViewModel;
import com.example.virussafeagro.viewModel.VirusQuizResultViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirusQuizQuestionFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    private VirusModel currentVirusModel;

    private VirusQuizQuestionViewModel virusQuizQuestionViewModel;
    private VirusQuizResultViewModel virusQuizResultViewModel;
    private List<ChoiceQuestionModel> choiceQuestionModelList;
    private static TextView[] topDotsTextViewArray = new TextView[QuizQuestionSlideAdapter.QUESTION_COUNT]; // dots
    private static boolean isLastAnswerRight; // for dot color

    private TextView virusFullNameTitleTextView;
    private NonSwipeableViewPager questionViewPager;
    private LinearLayout dotButtonsLinearLayout;

    // progress bar
    private LinearLayout processBarLinearLayout;
    private MyProgressBar progressBar;
    private TextView dot1TextView;
    private TextView dot2TextView;
    private TextView dot3TextView;

    // result views
    private LinearLayout quizResultLinearLayout;
    private TextView quizResultTitleTextView;
    private LinearLayout networkErrorLinearLayout;

    private QuizQuestionSlideAdapter quizQuestionSlideAdapter;
    private int currentPagePosition;

    // check is initial open this page
    private boolean isInitialOpening;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_quiz_question, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_virus_quiz);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirusModel = bundle.getParcelable("currentVirusModel");

        // set menu selected item
        if (!this.mainActivity.isLearnIconClicked()) {
            this.mainActivity.setLearnButton(true);
        }

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // initialize views
        this.initializeViews();
        this.quizResultLinearLayout.setVisibility(View.GONE);
        this.processBarLinearLayout.setVisibility(View.VISIBLE);
        this.questionViewPager.setVisibility(View.GONE);
        this.show3DotsAnimation();

        // initialize view model
        this.initializeVirusQuizQuestionViewModel();
        // initialize view model
        this.initializeVirusQuizResultViewModel();
        // add dots
        this.addDotsIndicator(0);
        // find virus quiz list in new Thread
        this.findVirusQuizQuestionsFromDB();
        // observe VirusModel Quiz List Live Data
        this.observeVirusQuizQuestionArrayLD();

        // check is initial open this page
        isInitialOpening = true;
        // slide to next page when the button in bottom sheet is clicked
        this.observeIsCorrectLD();
        // hide the view pager when it comes to the last question slide
        this.observeIsLastSlideLD();

    }

    private void initializeViews() {
        this.dotButtonsLinearLayout = view.findViewById(R.id.ll_dot_quiz_question);
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_quiz_question);
        this.progressBar = view.findViewById(R.id.pb_quiz_question);
        this.dot1TextView = view.findViewById(R.id.tv_dot1_quiz_question);
        this.dot2TextView = view.findViewById(R.id.tv_dot2_quiz_question);
        this.dot3TextView = view.findViewById(R.id.tv_dot3_quiz_question);
        this.virusFullNameTitleTextView = view.findViewById(R.id.tv_title_virus_full_name_quiz_question);
        this.virusFullNameTitleTextView.setText(this.currentVirusModel.getVirusFullName());
        this.questionViewPager = view.findViewById(R.id.slide_virus_quiz_question);
        this.quizResultLinearLayout = view.findViewById(R.id.ll_quiz_result_question);
        this.quizResultTitleTextView = view.findViewById(R.id.tv_title_quiz_result_final);
        this.networkErrorLinearLayout = view.findViewById(R.id.ll_fail_network_virus_quiz_question);

        // initialize the dot array
        for (int i = 0; i < topDotsTextViewArray.length; i++) {
            topDotsTextViewArray[i] = new TextView(requireActivity());
            topDotsTextViewArray[i].setText(Html.fromHtml("&#8226 &nbsp"));
            topDotsTextViewArray[i].setTextSize(35);
            topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.colorGreyForDots));
            topDotsTextViewArray[i].setGravity(Gravity.TOP);
        }
    }

    private void show3DotsAnimation() {
        MyAnimationBox.runFlickerAnimation(dot3TextView, 500);
        new Handler().postDelayed(() -> {
            MyAnimationBox.runFlickerAnimation(dot2TextView, 500);
        }, 500);
        new Handler().postDelayed(() -> {
            MyAnimationBox.runFlickerAnimation(dot1TextView, 500);
        },1000);
    }

    private void initializeVirusQuizQuestionViewModel() {
        this.virusQuizQuestionViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizQuestionViewModel.class);
        this.virusQuizQuestionViewModel.setProgressBar(progressBar);
    }
    private void initializeVirusQuizResultViewModel() {
        this.virusQuizResultViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizResultViewModel.class);
    }

    private void addDotsIndicator(int position) {
        dotButtonsLinearLayout.removeAllViews(); // clean the views
        for (int i = 0; i < topDotsTextViewArray.length; i++) {
            if (i > position){
                topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.colorGreyForDots));
            } else if (i == position) {
                topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.colorWhite));
            } else if (i == position - 1){
                if (isLastAnswerRight){
                    topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    topDotsTextViewArray[i].setTextColor(requireActivity().getResources().getColor(R.color.wrongAnswer));
                }
            }
            dotButtonsLinearLayout.addView(topDotsTextViewArray[i]);
        }
    }

    private void findVirusQuizQuestionsFromDB() {
        this.virusQuizQuestionViewModel.processFindingVirusQuizQuestions(this.currentVirusModel.getVirusId());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void observeVirusQuizQuestionArrayLD() {
        this.virusQuizQuestionViewModel.getQuizQuestionModelListLD().observe(getViewLifecycleOwner(), resultQuizQuestionModelList -> {
            if ((resultQuizQuestionModelList != null) && (resultQuizQuestionModelList.size() != 0)){
                // hide process bar
                processBarLinearLayout.setVisibility(View.GONE);
                // clear dots animations
                dot1TextView.clearAnimation();
                dot2TextView.clearAnimation();
                dot3TextView.clearAnimation();
                // check network connection for question text content
                if (resultQuizQuestionModelList.get(0).getChoiceQuestionType().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                    // show network error image
                    MyAnimationBox.runFadeInAnimation(networkErrorLinearLayout, 1000);
                } else {
                    // show dots
                    MyAnimationBox.runFadeInAnimation(dotButtonsLinearLayout, 1000);
                    // show slides (view pag)
                    MyAnimationBox.runFadeInAnimation(questionViewPager, 1000);

                    // set question list
                    choiceQuestionModelList = resultQuizQuestionModelList;
                    // initialize the QuizQuestionSlideAdapter and ViewPager
                    quizQuestionSlideAdapter = new QuizQuestionSlideAdapter(requireActivity(), choiceQuestionModelList);
                    questionViewPager.setAdapter(quizQuestionSlideAdapter);
                    // get the current slide position
                    questionViewPager.addOnPageChangeListener(viewPagerListener);
                }
                // check network connection for question image
                if (resultQuizQuestionModelList.get(resultQuizQuestionModelList.size() - 1).getChoiceQuestionType().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(), "Fail to connect to the image server! Something wrong with the network!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // for each question slide result
    private void observeIsCorrectLD() {
        this.virusQuizResultViewModel.getIsCorrectLD().observe(getViewLifecycleOwner(), isCorrectLD -> {
            if (isCorrectLD) {
                isInitialOpening = false;
            }
            if (isInitialOpening) {
                isInitialOpening = false;
            } else {
                isLastAnswerRight = isCorrectLD;
                // swipe to the next slide
                questionViewPager.setCurrentItem(currentPagePosition + 1);
            }
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
                showQuizResultView();
            }
        });
    }

    // show the result view
    private void showQuizResultView() {
        // show the layout
        MyAnimationBox.runFadeInAnimation(quizResultLinearLayout, 1000);

        // show the title
            // count the number of right answer
        int rightAnswerCount = 0;
        for (ChoiceQuestionModel q : choiceQuestionModelList) {
            if (DataComparison.checkTwoListHaveSameItems(q.getUserAnswerList(), q.getCorrectAnswerList())){
                rightAnswerCount++;
            }
        }
        String quizResultTitleString = "You Got " + rightAnswerCount + " Out of 5 Correct";
        quizResultTitleTextView.setText(quizResultTitleString);

        // show the recycler view
        ListQuizResultAdapter listQuizResultAdapter = new ListQuizResultAdapter(choiceQuestionModelList, requireActivity());
        RecyclerView recyclerViewForQuizResult = view.findViewById(R.id.rv_quiz_result_question);
        recyclerViewForQuizResult.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForQuizResult.setAdapter(listQuizResultAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForQuizResult.setLayoutManager(layoutManager);
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
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();

        // cancel the AsyncTask
        VirusQuizQuestionViewModel.FindVirusQuizQuestionsAsyncTask f = this.virusQuizQuestionViewModel.getCurrentFindVirusQuizQuestionsAsyncTask();
        f.cancel(true);

        this.virusQuizQuestionViewModel.getQuizQuestionModelListLD().removeObservers(getViewLifecycleOwner());
        List<ChoiceQuestionModel> choiceQuestionModelList = new ArrayList<>();
        this.virusQuizQuestionViewModel.setQuizQuestionModelListLD(choiceQuestionModelList);

        this.virusQuizResultViewModel.getIsCorrectLD().removeObservers(getViewLifecycleOwner());
        this.virusQuizResultViewModel.setIsCorrectLD(false);

        this.virusQuizResultViewModel.getIsLastSlideLD().removeObservers(getViewLifecycleOwner());
        this.virusQuizResultViewModel.setIsLastSlideLD(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppAuthentication.setAuthenticationAsNo((AppCompatActivity)requireActivity());

        this.mainActivity.setLearnButton(false);
    }
}
