package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;

import java.util.List;

public class MultipleChoiceQuestionAdapter extends RecyclerView.Adapter<MultipleChoiceQuestionAdapter.ViewHolder>{
    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList;
    private FragmentActivity fragmentActivity;

    public MultipleChoiceQuestionAdapter(List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList, FragmentActivity fragmentActivity) {
        this.multipleChoiceQuestionModelList = multipleChoiceQuestionModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView multipleChoiceQuestionContentTextView;
        public LinearLayout multipleChoiceQuestionOptionsLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.multipleChoiceQuestionContentTextView = itemView.findViewById(R.id.tv_multiple_question_content_virus_quiz_question);
            this.multipleChoiceQuestionOptionsLinearLayout = itemView.findViewById(R.id.ll_single_question_options_virus_quiz_question);
        }
    }

    @NonNull
    @Override
    public MultipleChoiceQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View multipleChoiceQuestionView = inflater.inflate(R.layout.recycler_view_multiple_choice_question, parent, false);
        MultipleChoiceQuestionAdapter.ViewHolder viewHolder = new MultipleChoiceQuestionAdapter.ViewHolder(multipleChoiceQuestionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleChoiceQuestionAdapter.ViewHolder viewHolder, int position) {
        final MultipleChoiceQuestionModel multipleChoiceQuestionModel = this.multipleChoiceQuestionModelList.get(position);

        viewHolder.multipleChoiceQuestionContentTextView.setText(multipleChoiceQuestionModel.getMultipleChoiceQuestionContent());

        // bind view holder for multiple-question options
        List<String> options = multipleChoiceQuestionModel.getMultipleChoiceQuestionOptionList();
        for(int i = 0; i < options.size(); i++){
            // get option
            String option = options.get(i);
            // create new checkbox
            CheckBox checkBox = new CheckBox(fragmentActivity);

            // Create a LayoutParams from LinearLayout
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            // set margins
            layoutParams.setMargins(5, 0, 0, 0);

            // set checkbox text
            checkBox.setText(option);
            // set checkbox padding
            checkBox.setPadding(10, 0, 0, 0);

            // add checkbox into LinearLayout
            viewHolder.multipleChoiceQuestionOptionsLinearLayout.addView(checkBox);
        }
    }

    @Override
    public int getItemCount() {
        return this.multipleChoiceQuestionModelList.size();
    }
}
