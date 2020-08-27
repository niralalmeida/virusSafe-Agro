package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;

import java.util.List;

public class SingleChoiceQuestionAdapter extends RecyclerView.Adapter<SingleChoiceQuestionAdapter.ViewHolder> {
    private List<SingleChoiceQuestionModel> singleChoiceQuestionModelList;
    private FragmentActivity fragmentActivity;

    public SingleChoiceQuestionAdapter(List<SingleChoiceQuestionModel> singleChoiceQuestionModelList, FragmentActivity fragmentActivity) {
        this.singleChoiceQuestionModelList = singleChoiceQuestionModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView singleChoiceQuestionContentTextView;
        public RadioGroup singleChoiceQuestionOptionsRadioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.singleChoiceQuestionContentTextView = itemView.findViewById(R.id.tv_single_question_content_virus_quiz_question);
            this.singleChoiceQuestionOptionsRadioGroup = itemView.findViewById(R.id.rg_single_question_options_virus_quiz_question);
        }
    }

    @NonNull
    @Override
    public SingleChoiceQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View singleChoiceQuestionView = inflater.inflate(R.layout.recycler_view_single_choice_question, parent, false);
        SingleChoiceQuestionAdapter.ViewHolder viewHolder = new SingleChoiceQuestionAdapter.ViewHolder(singleChoiceQuestionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleChoiceQuestionAdapter.ViewHolder viewHolder, int position) {
        final SingleChoiceQuestionModel singleChoiceQuestionModel = this.singleChoiceQuestionModelList.get(position);

        viewHolder.singleChoiceQuestionContentTextView.setText(singleChoiceQuestionModel.getSingleChoiceQuestionContent());

        // bind view holder for single question options
        List<String> options = singleChoiceQuestionModel.getSingleChoiceQuestionOptionList();
        for(int i = 0; i < options.size(); i++) {
            // get option
            String option = options.get(i);
            // create new RadioButton
            RadioButton radioButton = new RadioButton(fragmentActivity);

            // Create a LayoutParams from RadioGroup
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            // set margins
            layoutParams.setMargins(5, 0, 0, 0);

            // set button padding
            radioButton.setPadding(10, 0, 0, 0);
            // set button text
            radioButton.setText(option);
            // set radioButton click listener
            radioButton.setOnClickListener(view -> {

            });

            // add RadioButton into RadioGroup
            viewHolder.singleChoiceQuestionOptionsRadioGroup.addView(radioButton);

        }
    }

    @Override
    public int getItemCount() {
        return this.singleChoiceQuestionModelList.size();
    }
}
