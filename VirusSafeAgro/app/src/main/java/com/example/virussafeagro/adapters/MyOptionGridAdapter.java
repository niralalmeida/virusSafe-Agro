package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.uitilities.DataConverter;

import java.util.ArrayList;
import java.util.List;

public class MyOptionGridAdapter extends BaseAdapter {

    private FragmentActivity fragmentActivity;
    private LayoutInflater layoutInflater;

    private ChoiceQuestionModel currentChoiceQuestionModel;
    private List<ChoiceOptionModel> optionList;

    private ImageView optionImageView;
    private LinearLayout choiceButtonsLinearLayout;

    private MyOptionGridAdapter.SingleButtonOnClickListener singleButtonOnClickListener;

    private List<RadioButton> itemRadioButtonList = new ArrayList<>();;
    private List<CheckBox> itemCheckboxList = new ArrayList<>();;

    public MyOptionGridAdapter(FragmentActivity fragmentActivity, ChoiceQuestionModel currentChoiceQuestionModel, List<ChoiceOptionModel> optionList) {
        this.fragmentActivity = fragmentActivity;
        this.currentChoiceQuestionModel = currentChoiceQuestionModel;
        this.optionList = optionList;
        this.layoutInflater = (LayoutInflater) fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface SingleButtonOnClickListener{
        void onSingleButtonClick(int position);
    }
    public void setOnSingleButtonOnClickListenerClickListener(MyOptionGridAdapter.SingleButtonOnClickListener singleButtonOnClickListener){
        this.singleButtonOnClickListener = singleButtonOnClickListener;
    }

    @Override
    public int getCount() {
        return optionList.size();
    }

    @Override
    public ChoiceOptionModel getItem(int position) {
        return optionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return optionList.get(position).getChoiceOptionId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item_option_slide_question, parent, false);

            // set option image
            if (optionList.get(position).getChoiceOptionImage() != null){
                optionImageView = convertView.findViewById(R.id.img_option_grid_item);
                ViewGroup.LayoutParams layoutParams = optionImageView.getLayoutParams();
                layoutParams.height = 50;
                optionImageView.setLayoutParams(layoutParams);
                optionImageView.setImageBitmap(optionList.get(position).getChoiceOptionImage());
            }

            // initialize choiceButtons LinearLayout
            choiceButtonsLinearLayout = convertView.findViewById(R.id.ll_option_grid_item);
            // get option model
            ChoiceOptionModel optionModel = optionList.get(position);

            // setup all option views
            if (currentChoiceQuestionModel.getChoiceQuestionType().equals("single")){
                // set radio Button
                RadioButton radioButton = new RadioButton(fragmentActivity);
                // set radio Button text
                radioButton.setText(optionList.get(position).getChoiceOptionContent());
                // add radio Button into the linear layout
                choiceButtonsLinearLayout.addView(radioButton);
                // set radio Button OnClickListener
                radioButton.setOnClickListener(v -> singleButtonOnClickListener.onSingleButtonClick(position));

                // add the new radio Button into the item radio Button list
                itemRadioButtonList.add(radioButton);

                // test
                System.out.println("~~~~~~ added ~~~~~~");

            } else if (currentChoiceQuestionModel.getChoiceQuestionType().equals("multiple")) {
                // set checkbox
                CheckBox checkBox = new CheckBox(fragmentActivity);
                // set checkbox text
                checkBox.setText(optionList.get(position).getChoiceOptionContent());
                // add checkbox into the linear layout
                choiceButtonsLinearLayout.addView(checkBox);

                // add the new check box into the item checkbox list
                itemCheckboxList.add(checkBox);
            }

            // test
            System.out.println("## type ### - " + currentChoiceQuestionModel.getChoiceQuestionType() + " === : [ in adapter radio list size ] -> {" + itemRadioButtonList.size() + "}");
        }
        return convertView;
    }

    public List<RadioButton> getItemRadioButtonList() {
        return itemRadioButtonList;
    }
    public List<CheckBox> getItemCheckboxList() {
        return itemCheckboxList;
    }
}
