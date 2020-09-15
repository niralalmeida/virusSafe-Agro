package com.example.virussafeagro.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private ChoiceQuestionModel currentChoiceQuestionModel;
    private int currentQuestionSlidePosition;
    public static List<List<Map<Integer, RadioButton>>> allRadioButtonMapList = new ArrayList<>(); // questionId + RadioButton
    public static List<List<Map<Integer, CheckBox>>> allCheckBoxMapList = new ArrayList<>(); // questionId + CheckBox

    private VirusQuizResultViewModel virusQuizResultViewModel; // for isCorrect
    private boolean isCorrect = true;

    public static final int QUESTION_COUNT = 5;

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

        // question options
        // get options
        List<ChoiceOptionModel> optionList = currentChoiceQuestionModel.getChoiceQuestionOptionList();
        // initialize GridView for options
        showGrid(currentChoiceQuestionModel, optionList);

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
            // set currentChoiceQuestionModel
            this.currentChoiceQuestionModel = choiceQuestionModelList.get(currentQuestionSlidePosition);

            // set all radio button checked false and get checked Radio button
            RadioButton checkedRadioButton = setCheckedRadioButtonFalseAndGetCheckedOne(optionPosition);

            // set clicked radio button checked true
            checkedRadioButton.setChecked(true);
        });
    }

    private RadioButton setCheckedRadioButtonFalseAndGetCheckedOne(int optionPosition) {
        RadioButton checkedRadioButton = null;
        for (List<Map<Integer, RadioButton>> mapList : allRadioButtonMapList) {
            // if find the question option radio button list
            if (mapList.get(0).keySet().iterator().next() == (currentChoiceQuestionModel.getChoiceQuestionId())){
                // assign the checkedRadioButton
                Map<Integer, RadioButton> checkedMap = mapList.get(optionPosition);
                checkedRadioButton = checkedMap.get(checkedMap.keySet().iterator().next());
                // set all radio button checked false
                for (Map<Integer, RadioButton> map : mapList) {
                    RadioButton radioButton = map.get(map.keySet().iterator().next());
                    if (radioButton.isChecked()) {
                        radioButton.setChecked(false);
                    }
                }
                break;
            }
        }
        return checkedRadioButton;
    }

    // get all radio buttons of this slide (if not exist, return an empty list)
    private List<RadioButton> getAllCurrentSlideRadioButton() {
        List<RadioButton> currentRadioButtonList = new ArrayList<>();
        for (List<Map<Integer, RadioButton>> mapList : allRadioButtonMapList) {
            if (mapList.get(0).containsKey(currentChoiceQuestionModel.getChoiceQuestionId())){
                for (Map<Integer, RadioButton> map : mapList) {
                    RadioButton radioButton = map.get(currentChoiceQuestionModel.getChoiceQuestionId());
                    currentRadioButtonList.add(radioButton);
                }
            }
        }
        return currentRadioButtonList;
    }

    // get all checkbox of this slide (if not exist, return an empty list)
    private List<CheckBox> getAllCurrentSlideCheckBox() {
        List<CheckBox> currentCheckBoxList = new ArrayList<>();
        for (List<Map<Integer, CheckBox>> mapList : allCheckBoxMapList) {
            if (mapList.get(0).containsKey(currentChoiceQuestionModel.getChoiceQuestionId())){
                for (Map<Integer, CheckBox> map : mapList) {
                    CheckBox checkBox = map.get(currentChoiceQuestionModel.getChoiceQuestionId());
                    currentCheckBoxList.add(checkBox);
                }
            }
        }
        return currentCheckBoxList;
    }

    // when click, open the result view
    private void setSubmitAnswerButtonOnClickListener() {
        this.submitAnswerButton.setOnClickListener(view -> {
            currentChoiceQuestionModel = choiceQuestionModelList.get(currentQuestionSlidePosition);

            // check the question type
            if (currentChoiceQuestionModel.getChoiceQuestionType().equals("single")){
                // check whether user select an option
                if (!checkHasUserAnsweredSingleChoiceQuestion()) {
                    Toast.makeText(fragmentActivity, "Please make a choice!", Toast.LENGTH_SHORT).show();
                } else {
                    // open the bottom sheet dialog for result
                    openBottomSheetDialogForResult();
                }
            } else if (currentChoiceQuestionModel.getChoiceQuestionType().equals("multiple")){
                if (!checkHasUserAnsweredMultipleChoiceQuestion()){
                    Toast.makeText(fragmentActivity, "Please make a choice!", Toast.LENGTH_SHORT).show();
                } else {
                    // open the bottom sheet dialog for result
                    openBottomSheetDialogForResult();
                }
            }
        });
    }

    private boolean checkHasUserAnsweredSingleChoiceQuestion() {
        for (RadioButton radioButton : getAllCurrentSlideRadioButton()){
            if (radioButton.isChecked()) {
                String userAnswer = radioButton.getText().toString().substring(0, 1);
                // store user's answer
                List<String> userAnswerList = new ArrayList<>();
                userAnswerList.add(userAnswer);
                currentChoiceQuestionModel.setUserAnswerList(userAnswerList);
                // check the answer
                if (currentChoiceQuestionModel.getCorrectAnswerList().get(0).equals(userAnswer)) {
                    isCorrect = true;
                } else {
                    isCorrect = false;
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkHasUserAnsweredMultipleChoiceQuestion() {
        // set true if there is checked checkbox
        boolean isSelected = false;
        // create a list for user answers
        List<String> userAnswerList = new ArrayList<>();
        // traverse the checkbox list
        for (CheckBox checkBox : getAllCurrentSlideCheckBox()){
            if (checkBox.isChecked()) {
                isSelected = true;
                String userAnswer = checkBox.getText().toString().substring(0, 1);
                // store one user's answer
                userAnswerList.add(userAnswer);
            }
        }
        // store all the answers of user
        currentChoiceQuestionModel.setUserAnswerList(userAnswerList);
        // check the answer
        if (checkTwoListHaveSameItems(currentChoiceQuestionModel.getCorrectAnswerList(), userAnswerList)) {
            isCorrect = true;
        } else {
            isCorrect = false;
        }
        return isSelected;
    }

    private boolean checkTwoListHaveSameItems(List<String> list1, List<String> list2) {
        if (list1 == list2) {
            return false;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        List<Integer> checkedList1IndexList = new ArrayList<>();
        List<Integer> checkedList2IndexList = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for(int i2 = 0; i2 < list2.size(); i2++) {
                if (list1.get(i1).equals(list2.get(i2)) && (!checkedList1IndexList.contains(i1) || !checkedList2IndexList.contains(i2))) {
                    checkedList1IndexList.add(i1);
                    checkedList2IndexList.add(i2);
                }
            }
        }
        return (checkedList1IndexList.size() == list1.size()) && (checkedList2IndexList.size() == list2.size());
    }

    private void openBottomSheetDialogForResult() {
        // initialize the bottom sheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(fragmentActivity, R.style.BottomSheetDialogTheme);
        // initialize the bottom sheet view
        View bottomSheetView;
        // change the view according to the answer
        if (isCorrect){ // right
            bottomSheetView = LayoutInflater
                    .from(fragmentActivity.getApplicationContext())
                    .inflate(
                            R.layout.bottom_sheet_right_quiz_result,
                            fragmentActivity.findViewById(R.id.container_bottom_sheet_right_quiz_result)
                    );

            // show the user answer
            TextView userRightAnswer = bottomSheetView.findViewById(R.id.tv_user_answer_right_quiz_result);
            StringBuilder userAnswersStringBuilder = new StringBuilder();
            for(String optionAnswer : currentChoiceQuestionModel.getUserAnswerList()){
                userAnswersStringBuilder.append(" ").append(optionAnswer);
            }
            userRightAnswer.setText(userAnswersStringBuilder.toString());

            // close button
            bottomSheetView.findViewById(R.id.btn_close_right_quiz_result).setOnClickListener(closeButtonView -> {
                // close the dialog
                bottomSheetDialog.dismiss();
            });

            // next step button
            Button nextStepButton = bottomSheetView.findViewById(R.id.btn_next_step_right_quiz_result);
            nextStepButton.setOnClickListener(nextStepView -> {
                // hide the view pager when it comes to the last question slide
                if (currentQuestionSlidePosition == QUESTION_COUNT - 1){
                    virusQuizResultViewModel.setIsLastSlideLD(true);
                }
                // close the dialog
                bottomSheetDialog.dismiss();
            });
            // change the button text for last slide
            if (currentQuestionSlidePosition == QUESTION_COUNT - 1){
                nextStepButton.setText("Finish");
            }

        } else { // wrong
            bottomSheetView = LayoutInflater
                    .from(fragmentActivity.getApplicationContext())
                    .inflate(
                            R.layout.bottom_sheet_wrong_quiz_result,
                            fragmentActivity.findViewById(R.id.container_bottom_sheet_wrong_quiz_result)
                    );

            // show the user answer
            TextView userRightAnswer = bottomSheetView.findViewById(R.id.tv_user_answer_wrong_quiz_result);
            StringBuilder userAnswersStringBuilder = new StringBuilder();
            for(String optionAnswer : currentChoiceQuestionModel.getUserAnswerList()){
                userAnswersStringBuilder.append(" ").append(optionAnswer);
            }
            userRightAnswer.setText(userAnswersStringBuilder.toString());

            // show the correct answer
            TextView correctAnswer = bottomSheetView.findViewById(R.id.tv_correct_answer_wrong_quiz_result);
            StringBuilder correctAnswersStringBuilder = new StringBuilder();
            for(String optionAnswer : currentChoiceQuestionModel.getCorrectAnswerList()){
                correctAnswersStringBuilder.append(" ").append(optionAnswer);
            }
            correctAnswer.setText(correctAnswersStringBuilder.toString());

            // close button
            bottomSheetView.findViewById(R.id.btn_close_wrong_quiz_result).setOnClickListener(closeButtonView -> {
                // close the dialog
                bottomSheetDialog.dismiss();
            });

            // next step button
            Button nextStepButton = bottomSheetView.findViewById(R.id.btn_next_step_wrong_quiz_result);
            nextStepButton.setOnClickListener(nextStepView -> {
                // hide the view pager when it comes to the last question slide
                if (currentQuestionSlidePosition == QUESTION_COUNT - 1){
                    virusQuizResultViewModel.setIsLastSlideLD(true);
                }
                // close the dialog
                bottomSheetDialog.dismiss();
            });
                // change the button text for last slide
            if (currentQuestionSlidePosition == QUESTION_COUNT - 1){
                nextStepButton.setText("Finish");
            }

        }
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        bottomSheetDialog.setOnDismissListener(dialog -> {
            // slide to next page if it is correct
            virusQuizResultViewModel.setIsCorrectLD(isCorrect);
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

    public void setCurrentQuestionSlidePosition(int currentQuestionSlidePosition) {
        this.currentQuestionSlidePosition = currentQuestionSlidePosition;
        this.currentChoiceQuestionModel = choiceQuestionModelList.get(currentQuestionSlidePosition);
    }
}
