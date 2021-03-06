package com.example.virussafeagro.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridOptionAdapter extends BaseAdapter {

    private FragmentActivity fragmentActivity;
    private LayoutInflater layoutInflater;

    private ChoiceQuestionModel currentChoiceQuestionModel;
    private List<ChoiceOptionModel> optionList;

    private ImageView optionImageView;
    private LinearLayout choiceButtonsLinearLayout;

    private GridOptionAdapter.SingleButtonOnClickListener singleButtonOnClickListener;

    public GridOptionAdapter(FragmentActivity fragmentActivity, ChoiceQuestionModel currentChoiceQuestionModel, List<ChoiceOptionModel> optionList) {
        this.fragmentActivity = fragmentActivity;
        this.currentChoiceQuestionModel = currentChoiceQuestionModel;
        this.optionList = optionList;
        this.layoutInflater = (LayoutInflater) fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface SingleButtonOnClickListener{
        void onSingleButtonClick(int position);
    }
    public void setOnSingleButtonOnClickListenerClickListener(GridOptionAdapter.SingleButtonOnClickListener singleButtonOnClickListener){
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item_option_slide_question, parent, false);

            // get option model
            ChoiceOptionModel optionModel = optionList.get(position);

            // set option image
            if (optionModel.getChoiceOptionImage() != null){
                optionImageView = convertView.findViewById(R.id.img_option_grid_item);
                optionImageView.setImageBitmap(optionModel.getChoiceOptionImage());
                optionImageView.setVisibility(View.VISIBLE);
            }

            // initialize choiceButtons LinearLayout
            choiceButtonsLinearLayout = convertView.findViewById(R.id.ll_option_grid_item);

            // setup all option views
            if (currentChoiceQuestionModel.getChoiceQuestionType().equals("single")){
                // set radio Button
                RadioButton radioButton = new RadioButton(fragmentActivity);
                // set radio Button text
                String optionTextString = optionModel.getChoiceOptionLabel().toUpperCase() + ". " + optionModel.getChoiceOptionContent();
                radioButton.setText(optionTextString);
                // set color for clicking style
                if(Build.VERSION.SDK_INT>=21)
                {
                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{
                                    new int[]{-android.R.attr.state_enabled}, //disabled
                                    new int[]{android.R.attr.state_enabled} //enabled
                            },
                            new int[] {
                                    Color.BLACK//disabled
                                    ,ContextCompat.getColor(fragmentActivity, R.color.colorPrimaryDark) //enabled
                            }
                    );
                    radioButton.setButtonTintList(colorStateList);//set the color tint list
                }
                // add radio Button into the linear layout
                choiceButtonsLinearLayout.addView(radioButton);
                // set radio Button OnClickListener
                radioButton.setOnClickListener(v -> singleButtonOnClickListener.onSingleButtonClick(position));

                // add the new radio Button into the item radio Button list
                if (QuizQuestionSlideAdapter.allRadioButtonMapList.isEmpty()){
                    // create question list
                    List<Map<Integer, RadioButton>> mapList = new ArrayList<>();
                    // create option map
                    Map<Integer, RadioButton> integerRadioButtonMap = new HashMap<>();
                    integerRadioButtonMap.put(currentChoiceQuestionModel.getChoiceQuestionId(), radioButton);
                    // add option
                    mapList.add(integerRadioButtonMap);
                    // add question
                    QuizQuestionSlideAdapter.allRadioButtonMapList.add(mapList);
                } else {
                    boolean hasTheQuestionAndOption = false;
                    boolean isFull = false;
                    // find for question and option
                    for (int mapListPosition = 0; mapListPosition < QuizQuestionSlideAdapter.allRadioButtonMapList.size(); mapListPosition++){
                        // check whether the list is full
                        List<Map<Integer, RadioButton>> mapList = QuizQuestionSlideAdapter.allRadioButtonMapList.get(mapListPosition);

                        if (mapList.get(0).containsKey(currentChoiceQuestionModel.getChoiceQuestionId())){
                            if (mapList.size() < currentChoiceQuestionModel.getChoiceQuestionOptionList().size()){ // not full
                                // create option map
                                Map<Integer, RadioButton> integerRadioButtonMap = new HashMap<>();
                                integerRadioButtonMap.put(currentChoiceQuestionModel.getChoiceQuestionId(), radioButton);
                                // add option
                                mapList.add(integerRadioButtonMap);
                                hasTheQuestionAndOption = true;
                            } else {
                                isFull = true;
                                // refresh the radio list
                                    // create question list
                                List<Map<Integer, RadioButton>> refreshedMapList = new ArrayList<>();
                                    // create option map
                                Map<Integer, RadioButton> integerRadioButtonMap = new HashMap<>();
                                integerRadioButtonMap.put(currentChoiceQuestionModel.getChoiceQuestionId(), radioButton);
                                    // add option
                                refreshedMapList.add(integerRadioButtonMap);
                                    // change the question list to refreshedMapList
                                QuizQuestionSlideAdapter.allRadioButtonMapList.set(mapListPosition, refreshedMapList);
                            }
                            break;
                        }
                    }
                    // there is no list for this question
                    if (!hasTheQuestionAndOption && (!isFull)) {
                        // create question list
                        List<Map<Integer, RadioButton>> mapList = new ArrayList<>();
                        // create option map
                        Map<Integer, RadioButton> integerRadioButtonMap = new HashMap<>();
                        integerRadioButtonMap.put(currentChoiceQuestionModel.getChoiceQuestionId(), radioButton);
                        // add option
                        mapList.add(integerRadioButtonMap);
                        // add question
                        QuizQuestionSlideAdapter.allRadioButtonMapList.add(mapList);
                    }
                }

            } else if (currentChoiceQuestionModel.getChoiceQuestionType().equals("multiple")) {
                // set checkbox
                CheckBox checkBox = new CheckBox(fragmentActivity);
                // set checkbox text
                String optionTextString = optionModel.getChoiceOptionLabel().toUpperCase() + ". " + optionModel.getChoiceOptionContent();
                checkBox.setText(optionTextString);
                // set color for clicking style
                if(Build.VERSION.SDK_INT>=21)
                {
                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{
                                    new int[]{-android.R.attr.state_enabled}, //disabled
                                    new int[]{android.R.attr.state_enabled} //enabled
                            },
                            new int[] {
                                    Color.BLACK//disabled
                                    ,ContextCompat.getColor(fragmentActivity, R.color.colorPrimaryDark) //enabled
                            }
                    );
                    checkBox.setButtonTintList(colorStateList);//set the color tint list
                }
                // add checkbox into the linear layout
                choiceButtonsLinearLayout.addView(checkBox);

                // add the new checkbox into the item checkbox list
                if (QuizQuestionSlideAdapter.allCheckBoxMapList.isEmpty()){
                    // create question list
                    List<Map<Integer, CheckBox>> mapList = new ArrayList<>();
                    // create option map
                    Map<Integer, CheckBox> integerCheckBoxMap = new HashMap<>();
                    integerCheckBoxMap.put(currentChoiceQuestionModel.getChoiceQuestionId(), checkBox);
                    // add option
                    mapList.add(integerCheckBoxMap);
                    // add question
                    QuizQuestionSlideAdapter.allCheckBoxMapList.add(mapList);
                } else {
                    boolean hasTheQuestionAndOption = false;
                    boolean isFull = false;
                    // find for question and option
                    for (int mapListPosition = 0; mapListPosition < QuizQuestionSlideAdapter.allCheckBoxMapList.size(); mapListPosition++){
                        // check whether the list is full
                        List<Map<Integer, CheckBox>> mapList = QuizQuestionSlideAdapter.allCheckBoxMapList.get(mapListPosition);

                        if (mapList.get(0).containsKey(currentChoiceQuestionModel.getChoiceQuestionId())){
                            if (mapList.size() < currentChoiceQuestionModel.getChoiceQuestionOptionList().size()){ // not full
                                // create option map
                                Map<Integer, CheckBox> integerCheckBoxMap = new HashMap<>();
                                integerCheckBoxMap.put(currentChoiceQuestionModel.getChoiceQuestionId(), checkBox);
                                // add option
                                mapList.add(integerCheckBoxMap);
                                hasTheQuestionAndOption = true;
                            } else {
                                isFull = true;
                                // refresh the CheckBox list
                                // create question list
                                List<Map<Integer, CheckBox>> refreshedMapList = new ArrayList<>();
                                // create option map
                                Map<Integer, CheckBox> integerCheckBoxMap = new HashMap<>();
                                integerCheckBoxMap.put(currentChoiceQuestionModel.getChoiceQuestionId(), checkBox);
                                // add option
                                refreshedMapList.add(integerCheckBoxMap);
                                // change the question list to refreshedMapList
                                QuizQuestionSlideAdapter.allCheckBoxMapList.set(mapListPosition, refreshedMapList);
                            }
                            break;
                        }
                    }
                    // there is no list for this question
                    if (!hasTheQuestionAndOption && (!isFull)) {
                        // create question list
                        List<Map<Integer, CheckBox>> mapList = new ArrayList<>();
                        // create option map
                        Map<Integer, CheckBox> integerCheckBoxMap = new HashMap<>();
                        integerCheckBoxMap.put(currentChoiceQuestionModel.getChoiceQuestionId(), checkBox);
                        // add option
                        mapList.add(integerCheckBoxMap);
                        // add question
                        QuizQuestionSlideAdapter.allCheckBoxMapList.add(mapList);
                    }
                }
            }
        }
        return convertView;
    }

}
