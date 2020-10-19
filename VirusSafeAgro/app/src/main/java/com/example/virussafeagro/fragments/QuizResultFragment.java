package com.example.virussafeagro.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.ListQuizResultAdapter;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.ImageStorage;

public class QuizResultFragment extends Fragment {
    private QuizStartActivity quizStartActivity;
    private View view;

    // data
    private Bitmap quizResultScreenshotBitmap;

    // views
    private MotionLayout containerMotionLayout;
    private TextView rightCountTextView;
    private TextView wrongCountTextView;
    private TextView timeOutCountTextView;
    private TextView quizTakingTimeMinTextView;
    private TextView quizTakingTimeSecTextView;
    private CardView virusCardView;
    private Button redoButton;
    private Button saveScreenshotButton;
    private NestedScrollView quizResultNestedScrollView;
    private LinearLayout quizResultNSVImageLinearLayout;
    private ImageView quizResultNSVImageImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_quiz_result, container, false);

        // initialize quizActivity
        this.quizStartActivity = (QuizStartActivity)requireActivity();
        // initialize Views
        this.initializeViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // hide top progress
        MyAnimationBox.configureTheAnimation(
                this.quizStartActivity.getContainerMotionLayout(),
                R.id.start_hide_progress,
                R.id.end_hide_progress,
                300);

        // show titles
        this.showResultTitles(1000);
        // show overview
        this.showResultOverview(2800);

        // show Result Count
        this.showResultCount();
        // show quiz taking time
        this.showTakingTime();
        // show Question List
        this.showQuestionList();

        // set redo button on click listener
        this.setRedoButtonOnClickListener();
        // set save screenshot button on click listener
        this.setSaveScreenshotButtonOnClickListener();
        // set screenshot image on click listener
        this.setScreenshotImageOnClickListener();
    }

    private void initializeViews(){
        this.containerMotionLayout = view.findViewById(R.id.ml_container_quiz_result);
        this.rightCountTextView = view.findViewById(R.id.tv_right_count_quiz_result);
        this.wrongCountTextView = view.findViewById(R.id.tv_wrong_count_quiz_result);
        this.timeOutCountTextView = view.findViewById(R.id.tv_time_out_count_quiz_result);
        this.quizTakingTimeMinTextView = view.findViewById(R.id.tv_min_time_quiz_result);
        this.quizTakingTimeSecTextView = view.findViewById(R.id.tv_sec_time_quiz_result);
        this.virusCardView = view.findViewById(R.id.cv_virus_quiz_result);
        this.redoButton = view.findViewById(R.id.btn_redo_quiz_result);
        this.saveScreenshotButton = view.findViewById(R.id.btn_save_screenshot_quiz_result);
        this.quizResultNestedScrollView = view.findViewById(R.id.nsv_overview_container_quiz_result);
        this.quizResultNSVImageLinearLayout = view.findViewById(R.id.ll_nsv_image_quiz_result);
        this.quizResultNSVImageImageView = view.findViewById(R.id.img_nsv_quiz_result);
    }

    // show titles
    private void showResultTitles(int duration) {
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(this.containerMotionLayout, R.id.start_show_quiz_result_title, R.id.end_show_quiz_result_title, 300);
        }, duration);
    }

    // show overview
    private void showResultOverview(int duration) {
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(this.containerMotionLayout, R.id.start_show_quiz_result_overview, R.id.end_show_quiz_result_overview, 300);
        }, duration);
    }

    // show Result Count
    private void showResultCount() {
        String rightCountString = "" + quizStartActivity.rightCount;
        this.rightCountTextView.setText(rightCountString);
        String wrongCountString = "" + quizStartActivity.wrongCount;
        this.wrongCountTextView.setText(wrongCountString);
        String timeOutCountString = "" + quizStartActivity.timeOutCount;
        this.timeOutCountTextView.setText(timeOutCountString);
    }

    // show quiz taking time
    private void showTakingTime() {
        int min = quizStartActivity.quizTakingTime / 60;
        String minString = "" + min;
        this.quizTakingTimeMinTextView.setText(minString);
        String secString = "" + (quizStartActivity.quizTakingTime - min * 60);
        this.quizTakingTimeSecTextView.setText(secString);
    }

    // show question list
    private void showQuestionList() {
        // show the recycler view
        ListQuizResultAdapter listQuizResultAdapter = new ListQuizResultAdapter(QuizStartActivity.choiceQuestionModelFinalList, requireActivity());
        RecyclerView recyclerViewForQuizResult = view.findViewById(R.id.rv_question_list_quiz_result);
        recyclerViewForQuizResult.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForQuizResult.setAdapter(listQuizResultAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForQuizResult.setLayoutManager(layoutManager);
    }

    // set redo button on click listener
    private void setRedoButtonOnClickListener(){
        this.redoButton.setOnClickListener(redoButtonView -> {
            // animation
            QuizStartActivity.isQuizQuestionActivityClosed = true;

            Intent intent = new Intent(this.quizStartActivity, QuizStartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("currentVirusModel", this.quizStartActivity.getCurrentVirusModel());
            intent.putExtras(bundle);

            // animation
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this.quizStartActivity, this.redoButton, ViewCompat.getTransitionName(this.redoButton));
            this.quizStartActivity.startActivity(intent, options.toBundle());
            this.quizStartActivity.finish();
        });
    }

    // set save screenshot button on click listener
    private void setSaveScreenshotButtonOnClickListener() {
        this.saveScreenshotButton.setOnClickListener(saveScreenshotButtonView ->{
            // hide the 2 bottom buttons
            this.redoButton.setVisibility(View.GONE);
            this.saveScreenshotButton.setVisibility(View.GONE);
            // hide the close button
            MyAnimationBox.configureTheAnimation(this.quizStartActivity.getContainerMotionLayout(), R.id.start_hide_close_button, R.id.end_hide_close_button, 100);
            // get nested scroll view bitmap
            this.quizResultScreenshotBitmap = ImageStorage.longScreenshotGetBitmapByView(this.quizResultNestedScrollView);
            // show the image
            this.quizResultNSVImageImageView.setImageBitmap(this.quizResultScreenshotBitmap);
            MyAnimationBox.configureTheAnimation(this.containerMotionLayout, R.id.start_show_quiz_result_image, R.id.end_show_quiz_result_image, 300);
            // save the image
            ImageStorage.saveImage(
                    this.quizStartActivity,
                    this.quizResultScreenshotBitmap,
                    "Quiz Result Screenshot",
                    "quiz",
                    this.containerMotionLayout);
        });
    }

    // set screenshot image on click listener
    private void setScreenshotImageOnClickListener(){
        this.quizResultNSVImageLinearLayout.setOnClickListener(llView -> {
            // show the 2 bottom buttons
            this.redoButton.setVisibility(View.VISIBLE);
            this.saveScreenshotButton.setVisibility(View.VISIBLE);
            // show the close button
            MyAnimationBox.configureTheAnimation(this.quizStartActivity.getContainerMotionLayout(), R.id.end_hide_close_button, R.id.start_hide_close_button, 100);
            // hide the image
            MyAnimationBox.configureTheAnimation(this.containerMotionLayout, R.id.end_show_quiz_result_image, R.id.start_show_quiz_result_image, 300);
        });
    }

}
