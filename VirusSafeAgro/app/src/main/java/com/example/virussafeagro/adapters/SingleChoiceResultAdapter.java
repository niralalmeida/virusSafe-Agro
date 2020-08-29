package com.example.virussafeagro.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceQuestionCorrectAnswerModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.uitilities.Resources;

import java.util.List;

public class SingleChoiceResultAdapter extends RecyclerView.Adapter<SingleChoiceResultAdapter.ViewHolder> {
    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList;
    private List<ChoiceQuestionCorrectAnswerModel> choiceQuestionCorrectAnswerModelList;
    private FragmentActivity fragmentActivity;

    public SingleChoiceResultAdapter(List<SingleChoiceQuestionModel> singleChoiceQuestionModelList, List<ChoiceQuestionCorrectAnswerModel> choiceQuestionCorrectAnswerModelList, FragmentActivity fragmentActivity) {
        this.singleChoiceQuestionModelList = singleChoiceQuestionModelList;
        this.choiceQuestionCorrectAnswerModelList = choiceQuestionCorrectAnswerModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView singleChoiceQuestionContentTextView;
        public LinearLayout singleChoiceQuestionOptionsLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.singleChoiceQuestionContentTextView = itemView.findViewById(R.id.tv_single_question_content_virus_quiz_result);
            this.singleChoiceQuestionOptionsLinearLayout = itemView.findViewById(R.id.ll_single_question_options_virus_quiz_result);
        }
    }

    @NonNull
    @Override
    public SingleChoiceResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View singleChoiceResultView = inflater.inflate(R.layout.recycler_view_single_choice_result, parent, false);
        SingleChoiceResultAdapter.ViewHolder viewHolder = new SingleChoiceResultAdapter.ViewHolder(singleChoiceResultView);
        return viewHolder;
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull SingleChoiceResultAdapter.ViewHolder viewHolder, int position) {
        final SingleChoiceQuestionModel singleChoiceQuestionModel = this.singleChoiceQuestionModelList.get(position);

        viewHolder.singleChoiceQuestionContentTextView.setText(singleChoiceQuestionModel.getSingleChoiceQuestionContent());

        // check answer is right or not
        String userAnswer = singleChoiceQuestionModel.getSingleChoiceQuestionAnswer();
        String correctAnswer = "";
        for (ChoiceQuestionCorrectAnswerModel a : choiceQuestionCorrectAnswerModelList) {
            if (a.getChoiceQuestionId() == singleChoiceQuestionModel.getChoiceQuestionId()) {
                correctAnswer = a.getCorrectAnswerList().get(0);
                break;
            }
        }
        boolean isRight = userAnswer.equals(correctAnswer);
        // test
        System.out.println("q " + singleChoiceQuestionModel.getChoiceQuestionId() + " -> is right : " + isRight);
        // set background
        if (isRight) {
            viewHolder.singleChoiceQuestionOptionsLinearLayout.setBackgroundColor(Resources.RESULT_ITEM_RIGHT_BG);
        } else {
            viewHolder.singleChoiceQuestionOptionsLinearLayout.setBackgroundColor(Resources.RESULT_ITEM_WRONG_BG);
        }

        // bind view holder for single question options
        List<String> options = singleChoiceQuestionModel.getSingleChoiceQuestionOptionList();
        for(int i = 0; i < options.size(); i++) {
            // get option
            String option = options.get(i);
            // create new TextView
            TextView optionTextView = new TextView(fragmentActivity);

            // set TextView padding
            optionTextView.setPadding(10, 0, 0, 0);
            // set TextView text
            optionTextView.setText(option);

            String optionLabel = option.substring(0, 1);
            // set if right
            if (optionLabel.equals(correctAnswer)) {
                optionTextView.setTextColor(Resources.RIGHT_ANSWER);
                optionTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            }
            // set if wrong
            if (!isRight && optionLabel.equals(userAnswer)){
                optionTextView.setTextColor(Resources.WRONG_ANSWER);
            }
            if(!isRight && optionLabel.equals(correctAnswer)){
                optionTextView.setTextColor(Resources.CORRECT_ANSWER);
                optionTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            // add TextView into LinearLayout
            viewHolder.singleChoiceQuestionOptionsLinearLayout.addView(optionTextView);
        }

        // set if wrong
            // create new TextView
        TextView correctAnswerTextView = new TextView(fragmentActivity);
        correctAnswerTextView.setText("--> The correct answer is:" + correctAnswer);
        if (isRight){
            correctAnswerTextView.setTextColor(Resources.RIGHT_ANSWER);
        } else {
            correctAnswerTextView.setTextColor(Resources.CORRECT_ANSWER);
        }
        correctAnswerTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            // add TextView into LinearLayout
        viewHolder.singleChoiceQuestionOptionsLinearLayout.addView(correctAnswerTextView);
    }

    @Override
    public int getItemCount() {
        return this.singleChoiceQuestionModelList.size();
    }

}
