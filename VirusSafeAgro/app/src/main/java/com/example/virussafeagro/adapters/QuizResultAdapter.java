package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;

import java.util.List;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.ViewHolder> {
    private List<ChoiceQuestionModel> choiceQuestionModelList;
    private FragmentActivity fragmentActivity;

    public QuizResultAdapter(List<ChoiceQuestionModel> choiceQuestionModelList, FragmentActivity fragmentActivity) {
        this.choiceQuestionModelList = choiceQuestionModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView questionMarkImageVew;
        public TextView questionNoTextView;
        public TextView questionContentTextView;
        public ImageView questionImageView;
        public TextView userAnswersTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.questionMarkImageVew = itemView.findViewById(R.id.img_question_mark_quiz_result_final);
            this.questionNoTextView = itemView.findViewById(R.id.tv_question_no_quiz_result_final);
            this.questionContentTextView = itemView.findViewById(R.id.tv_question_content_quiz_result_final);
            this.questionImageView = itemView.findViewById(R.id.img_question_quiz_result_final);
            this.userAnswersTextView = itemView.findViewById(R.id.tv_user_answer_quiz_result_final);
        }
    }

    @NonNull
    @Override
    public QuizResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View quizResultView = inflater.inflate(R.layout.recycler_view_quiz_result_final, parent, false);
        QuizResultAdapter.ViewHolder viewHolder = new QuizResultAdapter.ViewHolder(quizResultView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizResultAdapter.ViewHolder viewHolder, int position) {
        final ChoiceQuestionModel choiceQuestionModel = this.choiceQuestionModelList.get(position);

//        viewHolder.questionMarkImageVew.setImageResource(R.drawable.rig);
//        viewHolder.singleChoiceQuestionContentTextView.setText(singleChoiceQuestionModel.getSingleChoiceQuestionContent());


    }

    @Override
    public int getItemCount() {
        return this.choiceQuestionModelList.size();
    }
}
