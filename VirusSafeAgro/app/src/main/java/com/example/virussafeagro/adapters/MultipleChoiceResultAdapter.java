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

public class MultipleChoiceResultAdapter extends RecyclerView.Adapter<MultipleChoiceResultAdapter.ViewHolder>{
    private List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList;
    private FragmentActivity fragmentActivity;

    public MultipleChoiceResultAdapter(List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList, FragmentActivity fragmentActivity) {
        this.multipleChoiceQuestionModelList = multipleChoiceQuestionModelList;
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

            // add TextView into LinearLayout
            viewHolder.multipleChoiceQuestionOptionsLinearLayout.addView(optionTextView);
        }
    }

    @Override
    public int getItemCount() {
        return this.multipleChoiceQuestionModelList.size();
    }
}
