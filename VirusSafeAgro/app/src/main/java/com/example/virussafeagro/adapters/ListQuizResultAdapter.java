package com.example.virussafeagro.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.uitilities.DataComparison;

import java.util.List;

public class ListQuizResultAdapter extends RecyclerView.Adapter<ListQuizResultAdapter.ViewHolder> {
    private List<ChoiceQuestionModel> choiceQuestionModelList;
    private FragmentActivity fragmentActivity;

    public ListQuizResultAdapter(List<ChoiceQuestionModel> choiceQuestionModelList, FragmentActivity fragmentActivity) {
        this.choiceQuestionModelList = choiceQuestionModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView quizResultCardView;
        public ImageView questionMarkImageVew;
        public TextView questionNoTextView;
        public TextView questionContentTextView;
        public ImageView questionImageView;
        public TextView userAnswersTextView;
        public LinearLayout correctAnswerLinearLayout;
        public TextView correctAnswersTextView;
        public TextView explanationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.quizResultCardView = itemView.findViewById(R.id.cv_each_quiz_result_final);
            this.questionMarkImageVew = itemView.findViewById(R.id.img_question_mark_quiz_result_final);
            this.questionNoTextView = itemView.findViewById(R.id.tv_question_no_quiz_result_final);
            this.questionContentTextView = itemView.findViewById(R.id.tv_question_content_quiz_result_final);
            this.questionImageView = itemView.findViewById(R.id.img_question_quiz_result_final);
            this.userAnswersTextView = itemView.findViewById(R.id.tv_user_answer_quiz_result_final);
            this.correctAnswerLinearLayout = itemView.findViewById(R.id.ll_correct_answer_quiz_result_final);
            this.correctAnswersTextView = itemView.findViewById(R.id.tv_correct_answer_quiz_result_final);
            this.explanationTextView = itemView.findViewById(R.id.tv_explanation_quiz_result_final);
        }
    }

    @NonNull
    @Override
    public ListQuizResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View quizResultView = inflater.inflate(R.layout.recycler_view_quiz_result_final, parent, false);
        ListQuizResultAdapter.ViewHolder viewHolder = new ListQuizResultAdapter.ViewHolder(quizResultView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListQuizResultAdapter.ViewHolder viewHolder, int position) {
        final ChoiceQuestionModel choiceQuestionModel = this.choiceQuestionModelList.get(position);

        // question mark and background color
        if (DataComparison.checkTwoListHaveSameItems(choiceQuestionModel.getUserAnswerList(), choiceQuestionModel.getCorrectAnswerList())){
            viewHolder.questionMarkImageVew.setImageResource(R.drawable.ic_right_circle_white_50dp);
            viewHolder.quizResultCardView.setCardBackgroundColor(ContextCompat.getColor(fragmentActivity, R.color.rightAnswer));
            viewHolder.correctAnswerLinearLayout.setVisibility(View.GONE);
        } else {
            viewHolder.questionMarkImageVew.setImageResource(R.drawable.ic_wrong_circle_white_50dp);
            viewHolder.quizResultCardView.setCardBackgroundColor(ContextCompat.getColor(fragmentActivity, R.color.wrongAnswer));
            // correct answer
            StringBuilder correctAnswerStringBuilder = new StringBuilder();
            // get correct answer content by the label
            for (ChoiceOptionModel choiceOptionModel : choiceQuestionModel.getChoiceQuestionOptionList()) {
                for (String answerLabel : choiceQuestionModel.getCorrectAnswerList()) {
                    if (answerLabel.equals(choiceOptionModel.getChoiceOptionLabel())) {
                        correctAnswerStringBuilder.append(answerLabel).append(". ").append(choiceOptionModel.getChoiceOptionContent()).append("\n");
                        break;
                    }
                }
            }
            correctAnswerStringBuilder.deleteCharAt(correctAnswerStringBuilder.length() - 1);
            viewHolder.correctAnswersTextView.setText(correctAnswerStringBuilder.toString());
        }

        // question no
        String questionNoString = "Q" + (position + 1) + " - ";
        viewHolder.questionNoTextView.setText(questionNoString);

        // question content
        viewHolder.questionContentTextView.setText(choiceQuestionModel.getChoiceQuestionContent());

        // question image
        List<Bitmap> questionImageList = choiceQuestionModel.getChoiceQuestionImageList();
        if (questionImageList != null && questionImageList.size() != 0 && (questionImageList.get(0) != null)){
            // just one image can be shown for now
            viewHolder.questionImageView.setImageBitmap(questionImageList.get(0));
            viewHolder.questionImageView.setVisibility(View.VISIBLE);
        }

        // user answer
        StringBuilder userAnswerStringBuilder = new StringBuilder();
            // get user answer content by the label
        if(choiceQuestionModel.getUserAnswerList() != null){
            for (ChoiceOptionModel choiceOptionModel : choiceQuestionModel.getChoiceQuestionOptionList()) {
                for (String answerLabel : choiceQuestionModel.getUserAnswerList()) {
                    if (answerLabel.equals(choiceOptionModel.getChoiceOptionLabel())) {
                        userAnswerStringBuilder.append(answerLabel).append(". ").append(choiceOptionModel.getChoiceOptionContent()).append("\n");
                        break;
                    }
                }
            }
            userAnswerStringBuilder.deleteCharAt(userAnswerStringBuilder.length() - 1);
            viewHolder.userAnswersTextView.setText(userAnswerStringBuilder.toString());
        }

        // question explanation
        viewHolder.explanationTextView.setText(choiceQuestionModel.getChoiceQuestionExplanation());

    }

    @Override
    public int getItemCount() {
        return this.choiceQuestionModelList.size();
    }
}
