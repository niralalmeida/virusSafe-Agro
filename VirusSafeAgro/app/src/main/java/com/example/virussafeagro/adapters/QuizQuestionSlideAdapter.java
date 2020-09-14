package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.viewModel.VirusQuizResultViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class QuizQuestionSlideAdapter extends PagerAdapter {
    private FragmentActivity fragmentActivity;
    private LayoutInflater layoutInflater;
    private View questionView;

    private CardView quizQuestionCardView;
    private TextView quizQuestionNoTextView;
    private TextView quizQuestionContentTextView;
    private ImageView quizQuestionImageView;
    private GridView quizOptionGridView;
    private Button submitAnswerButton;

    private List<ChoiceQuestionModel> choiceQuestionModelList;
    private MyOptionGridAdapter myOptionGridAdapter;

    private VirusQuizResultViewModel virusQuizResultViewModel; // for isCorrect
    private boolean isCorrect = true; // test

    private static final int QUESTION_COUNT = 5;

    public QuizQuestionSlideAdapter(FragmentActivity fragmentActivity, List<ChoiceQuestionModel> choiceQuestionModelList) {
        this.fragmentActivity = fragmentActivity;
        this.choiceQuestionModelList = choiceQuestionModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        this.questionView = layoutInflater.inflate(R.layout.slide_quiz_question, container, false);

        // initiate Question Views
        this.initializeQuestionViews();
        // initialize VirusQuizResultViewModel
        this.initializeVirusQuizResultViewModel();

        // setup all the views within the slides
        this.setContentOfViewsByPosition(position);

        // set submitAnswerButton OnClickListener
        this.setSubmitAnswerButtonOnClickListener();

        container.addView(this.questionView);
        return this.questionView;
    }

    private void initializeQuestionViews() {
        this.quizQuestionCardView = questionView.findViewById(R.id.cv_quiz_question);
        this.quizQuestionNoTextView = questionView.findViewById(R.id.tv_question_no_slide_quiz_question);
        this.quizQuestionContentTextView = questionView.findViewById(R.id.tv_question_content_slide_quiz_question);
        this.quizQuestionImageView = questionView.findViewById(R.id.img_question_slide_quiz_question);
        this.quizOptionGridView = questionView.findViewById(R.id.gv_options_quiz_question);
        this.submitAnswerButton = questionView.findViewById(R.id.btn_submit_answer_slide_quiz_question);
    }

    private void initializeVirusQuizResultViewModel(){
        this.virusQuizResultViewModel = new ViewModelProvider(fragmentActivity).get(VirusQuizResultViewModel.class);
    }

    private void setContentOfViewsByPosition(int slidePosition) {
        ChoiceQuestionModel currentChoiceQuestionModel = choiceQuestionModelList.get(slidePosition);

        // question title
        this.quizQuestionNoTextView.setText("Q" + (slidePosition + 1) + " - ");
        // question content
        String questionContent = "";
        if (currentChoiceQuestionModel.getChoiceQuestionType().equals("single")){
            questionContent = currentChoiceQuestionModel.getChoiceQuestionContent() + " - [ single choice ]";
        } else {
            questionContent = currentChoiceQuestionModel.getChoiceQuestionContent() + " - [ multiple choice ]";
        }
        this.quizQuestionContentTextView.setText(questionContent);
        // question image
        if (currentChoiceQuestionModel.getChoiceQuestionImage() != null){
            this.quizQuestionImageView.setImageBitmap(currentChoiceQuestionModel.getChoiceQuestionImage());
            ViewGroup.LayoutParams layoutParams = this.quizQuestionImageView.getLayoutParams();
            layoutParams.height = 150;
            this.quizQuestionImageView.setLayoutParams(layoutParams);
        }

        // get options
        List<ChoiceOptionModel> optionList = currentChoiceQuestionModel.getChoiceQuestionOptionList();
        // initialize GridView for options
        showGrid(currentChoiceQuestionModel, optionList);

        // test
        System.out.println("radio list size(" + myOptionGridAdapter.getItemRadioButtonList().size() + ")");

        // set single button on click listener
        setSingleButtonOnClickListener();
    }

    // initialize GridView for options
    private void showGrid(ChoiceQuestionModel currentChoiceQuestionModel, List<ChoiceOptionModel> optionList) {
        myOptionGridAdapter = new MyOptionGridAdapter(fragmentActivity, currentChoiceQuestionModel, optionList);
        quizOptionGridView.setAdapter(myOptionGridAdapter);
    }

    private void setSingleButtonOnClickListener() {
        myOptionGridAdapter.setOnSingleButtonOnClickListenerClickListener(optionPosition -> {
            // test
            System.out.println("after added ~~~ radio list size(" + myOptionGridAdapter.getItemRadioButtonList().size() + ")");

            // set all radio button checked false
            setCheckedRadioButtonFalse();
            // set clicked radio button checked true
            List<RadioButton> itemRadioButtonList = myOptionGridAdapter.getItemRadioButtonList();
            if ((itemRadioButtonList != null) || (!itemRadioButtonList.isEmpty())){
                // test
                System.out.println("=-======> itemRadioButtonList size [" + itemRadioButtonList.size() + "]");
                // test
                System.out.println("----> optionPosition <" + optionPosition + ">");
//                RadioButton radioButton = itemRadioButtonList.get(optionPosition);
//                radioButton.setChecked(true);
            }
        });
    }

    private void setCheckedRadioButtonFalse() {
        for (RadioButton rb : myOptionGridAdapter.getItemRadioButtonList()) {
            // test
            System.out.println("##### reached");
            // test
            System.out.println("is checked ++++ > [" + rb.isChecked() + "]");
            if (rb.isChecked()){
                rb.setChecked(false);
            }
        }
    }

    // when click, open the result view
    private void setSubmitAnswerButtonOnClickListener() {
        this.submitAnswerButton.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    fragmentActivity, R.style.BottomSheetDialogTheme
            );
            View bottomSheetView = LayoutInflater
                    .from(fragmentActivity.getApplicationContext())
                    .inflate(
                            R.layout.bottom_sheet_quiz_result,
                            fragmentActivity.findViewById(R.id.container_bottom_sheet_quiz_result)
                    );
            // close button
            bottomSheetView.findViewById(R.id.btn_close_quiz_result).setOnClickListener(closeButtonView -> {
                bottomSheetDialog.dismiss();
            });
            // next step button
            bottomSheetView.findViewById(R.id.btn_next_step_quiz_result).setOnClickListener(nextStepView -> {
                // slide to next page if it is correct
                if (isCorrect) {
                    virusQuizResultViewModel.setIsCorrectLD(true);
                }
                // just close the result view bottomSheetDialog
                bottomSheetDialog.dismiss();
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return QUESTION_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
