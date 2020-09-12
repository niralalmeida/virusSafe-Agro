package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestionSlideAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;

    private CardView quizQuestionCardView;
    private TextView quizQuestionNoTextView;
    private TextView quizQuestionContentTextView;
    private ImageView quizQuestionImageView;
    private GridView quizOptionGridView;
    private Button submitAnswerButton;
    private TextView quizQuestionResultTextView;

    private List<ChoiceQuestionModel> choiceQuestionModelList;

    private static final int QUESTION_COUNT = 5;

    public QuizQuestionSlideAdapter(Context context, List<ChoiceQuestionModel> choiceQuestionModelList) {
        this.context = context;
        this.choiceQuestionModelList = choiceQuestionModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        this.view = layoutInflater.inflate(R.layout.slide_quiz_question, container, false);

        this.initiateViews();
        this.setContentOfViewsByPosition(position);
        container.addView(this.view);

        return this.view;
    }

    private void initiateViews() {
        this.quizQuestionCardView = view.findViewById(R.id.cv_quiz_question);
        this.quizQuestionNoTextView = view.findViewById(R.id.tv_question_no_slide_quiz_question);
        this.quizQuestionContentTextView = view.findViewById(R.id.tv_question_content_slide_quiz_question);
        this.quizQuestionImageView = view.findViewById(R.id.img_question_slide_quiz_question);
        this.quizOptionGridView = view.findViewById(R.id.gv_options_quiz_question);
        this.submitAnswerButton = view.findViewById(R.id.btn_submit_answer_slide_quiz_question);
        this.quizQuestionResultTextView = view.findViewById(R.id.tv_result_slide_quiz_question);
    }

    private void setContentOfViewsByPosition(int position) {
        this.quizQuestionNoTextView.setText("Q" + (position + 1) + " - ");
        this.quizQuestionContentTextView.setText(this.choiceQuestionModelList.get(position).getChoiceQuestionContent());
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
