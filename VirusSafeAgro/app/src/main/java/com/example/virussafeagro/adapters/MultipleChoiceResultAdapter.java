package com.example.virussafeagro.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceQuestionCorrectAnswerModel;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.uitilities.AppResources;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceResultAdapter extends RecyclerView.Adapter<MultipleChoiceResultAdapter.ViewHolder>{
    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList;
    private List<ChoiceQuestionCorrectAnswerModel> choiceQuestionCorrectAnswerModelList;
    private FragmentActivity fragmentActivity;

    public MultipleChoiceResultAdapter(List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList, List<ChoiceQuestionCorrectAnswerModel> choiceQuestionCorrectAnswerModelList, FragmentActivity fragmentActivity) {
        this.multipleChoiceQuestionModelList = multipleChoiceQuestionModelList;
        this.choiceQuestionCorrectAnswerModelList = choiceQuestionCorrectAnswerModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView multipleChoiceQuestionContentTextView;
        public LinearLayout multipleChoiceQuestionOptionsLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.multipleChoiceQuestionContentTextView = itemView.findViewById(R.id.tv_multiple_question_content_virus_quiz_result);
            this.multipleChoiceQuestionOptionsLinearLayout = itemView.findViewById(R.id.ll_multiple_question_options_virus_quiz_result);
        }
    }

    @NonNull
    @Override
    public MultipleChoiceResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View multipleChoiceQuestionView = inflater.inflate(R.layout.recycler_view_multiple_choice_result, parent, false);
        MultipleChoiceResultAdapter.ViewHolder viewHolder = new MultipleChoiceResultAdapter.ViewHolder(multipleChoiceQuestionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleChoiceResultAdapter.ViewHolder viewHolder, int position) {
        final MultipleChoiceQuestionModel multipleChoiceQuestionModel = this.multipleChoiceQuestionModelList.get(position);

        viewHolder.multipleChoiceQuestionContentTextView.setText(multipleChoiceQuestionModel.getMultipleChoiceQuestionContent());

        // check answer is right or not
        List<String> userAnswerList = multipleChoiceQuestionModel.getMultipleChoiceQuestionAnswerList();
        List<String> correctAnswerList = new ArrayList<>();
        for (ChoiceQuestionCorrectAnswerModel a : choiceQuestionCorrectAnswerModelList) {
            if (a.getChoiceQuestionId() == multipleChoiceQuestionModel.getChoiceQuestionId()) {
                correctAnswerList = a.getCorrectAnswerList();
            }
        }
        boolean isRight = checkTwoListHaveSameItems(userAnswerList, correctAnswerList);
        // set background
        if (isRight) {
            viewHolder.multipleChoiceQuestionOptionsLinearLayout.setBackgroundColor(AppResources.COlOR_RESULT_ITEM_RIGHT_BG);
        } else {
            viewHolder.multipleChoiceQuestionOptionsLinearLayout.setBackgroundColor(AppResources.COlOR_RESULT_ITEM_WRONG_BG);
        }

        // bind view holder for multiple-question options
        List<String> options = multipleChoiceQuestionModel.getMultipleChoiceQuestionOptionList();
        for(int i = 0; i < options.size(); i++){
            // get option
            String option = options.get(i);
            // create new TextView
            TextView optionTextView = new TextView(fragmentActivity);

            // Create a LayoutParams from LinearLayout
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            // set margins
            layoutParams.setMargins(5, 0, 0, 0);

            // set TextView text
            optionTextView.setText(option);
            // set TextView padding
            optionTextView.setPadding(10, 0, 0, 0);

            String optionLabel = option.substring(0, 1);
            // set if right
            if (isRight && correctAnswerList.contains(optionLabel)) {
                optionTextView.setTextColor(AppResources.COlOR_RIGHT_ANSWER);
                optionTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            // set if wrong
                // when user select wrong item
            if (!isRight && userAnswerList.contains(optionLabel) && (!correctAnswerList.contains(optionLabel))){
                optionTextView.setTextColor(AppResources.COlOR_WRONG_ANSWER);
            }
                // when user select right item
            if(!isRight && userAnswerList.contains(optionLabel) && correctAnswerList.contains(optionLabel)){
                optionTextView.setTextColor(AppResources.COlOR_RIGHT_ANSWER);
                optionTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
                // show correct item
            if(!isRight && (!userAnswerList.contains(optionLabel)) && correctAnswerList.contains(optionLabel)){
                optionTextView.setTextColor(AppResources.COlOR_CORRECT_ANSWER);
                optionTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            // add TextView into LinearLayout
            viewHolder.multipleChoiceQuestionOptionsLinearLayout.addView(optionTextView);
        }

        // set if wrong
        // create new TextView
        TextView correctAnswerTextView = new TextView(fragmentActivity);
        String correctAnswer = "";
        for (String ans : correctAnswerList) {
            correctAnswer += (ans + " ");
        }
        correctAnswerTextView.setText("--> The correct answer is:" + correctAnswer);
        if (isRight){
            correctAnswerTextView.setTextColor(AppResources.COlOR_RIGHT_ANSWER);
        } else {
            // add text view for user selected answer
            String userAnswer = "";
            for (String user_ans : userAnswerList) {
                userAnswer += (user_ans + " ");
            }
            TextView userAnswerTextView = new TextView(fragmentActivity);
            userAnswerTextView.setText("--> You selected: " + userAnswer);
            userAnswerTextView.setTextColor(AppResources.COlOR_WRONG_ANSWER);
            userAnswerTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.multipleChoiceQuestionOptionsLinearLayout.addView(userAnswerTextView);

            correctAnswerTextView.setTextColor(AppResources.COlOR_CORRECT_ANSWER);
        }
        correctAnswerTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        // add TextView into LinearLayout
        viewHolder.multipleChoiceQuestionOptionsLinearLayout.addView(correctAnswerTextView);
    }

    private boolean checkTwoListHaveSameItems(List<String> list1, List<String> list2) {
        boolean isSame = true;
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

    @Override
    public int getItemCount() {
        return this.multipleChoiceQuestionModelList.size();
    }
}
