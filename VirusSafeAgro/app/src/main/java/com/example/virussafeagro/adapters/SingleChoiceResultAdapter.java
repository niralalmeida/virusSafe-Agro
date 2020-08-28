package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;

import java.util.List;

public class SingleChoiceResultAdapter extends RecyclerView.Adapter<SingleChoiceResultAdapter.ViewHolder> {
    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList;
    private FragmentActivity fragmentActivity;

    public SingleChoiceResultAdapter(List<SingleChoiceQuestionModel> singleChoiceQuestionModelList, FragmentActivity fragmentActivity) {
        this.singleChoiceQuestionModelList = singleChoiceQuestionModelList;
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

    @Override
    public void onBindViewHolder(@NonNull SingleChoiceResultAdapter.ViewHolder viewHolder, int position) {
        final SingleChoiceQuestionModel singleChoiceQuestionModel = this.singleChoiceQuestionModelList.get(position);

        viewHolder.singleChoiceQuestionContentTextView.setText(singleChoiceQuestionModel.getSingleChoiceQuestionContent());

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

            // add TextView into LinearLayout
            viewHolder.singleChoiceQuestionOptionsLinearLayout.addView(optionTextView);

        }
    }

    @Override
    public int getItemCount() {
        return this.singleChoiceQuestionModelList.size();
    }

}
