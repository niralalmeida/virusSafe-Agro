package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;

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
    private TextView quizQuestionResultTextView;

    private List<ChoiceQuestionModel> choiceQuestionModelList;
    private MyOptionGridAdapter myOptionGridAdapter;

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

        this.initiateQuestionViews();
        this.setContentOfViewsByPosition(position);
        container.addView(this.questionView);

        return this.questionView;
    }

    private void initiateQuestionViews() {
        this.quizQuestionCardView = questionView.findViewById(R.id.cv_quiz_question);
        this.quizQuestionNoTextView = questionView.findViewById(R.id.tv_question_no_slide_quiz_question);
        this.quizQuestionContentTextView = questionView.findViewById(R.id.tv_question_content_slide_quiz_question);
        this.quizQuestionImageView = questionView.findViewById(R.id.img_question_slide_quiz_question);
        this.quizOptionGridView = questionView.findViewById(R.id.gv_options_quiz_question);
        this.submitAnswerButton = questionView.findViewById(R.id.btn_submit_answer_slide_quiz_question);
        this.quizQuestionResultTextView = questionView.findViewById(R.id.tv_result_slide_quiz_question);
    }

    private void setContentOfViewsByPosition(int slidePosition) {
        ChoiceQuestionModel currentChoiceQuestionModel = choiceQuestionModelList.get(slidePosition);

        this.quizQuestionNoTextView.setText("Q" + (slidePosition + 1) + " - ");
        this.quizQuestionContentTextView.setText(currentChoiceQuestionModel.getChoiceQuestionContent());

        // get options
        List<ChoiceOptionModel> optionList = currentChoiceQuestionModel.getChoiceQuestionOptionList();
        // initialize GridView
        showGrid(currentChoiceQuestionModel, optionList);
    }

    private void showGrid(ChoiceQuestionModel currentChoiceQuestionModel, List<ChoiceOptionModel> optionList) {
        myOptionGridAdapter = new MyOptionGridAdapter(fragmentActivity, currentChoiceQuestionModel, optionList);
        quizOptionGridView.setAdapter(myOptionGridAdapter);
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
