package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceOptionModel;

import java.util.ArrayList;
import java.util.List;

public class MyOptionGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<ChoiceOptionModel> optionList;

    private ImageView imageView;
    private LinearLayout choiceButtonsLinearLayout;

    private MyOptionGridAdapter.SingleButtonOnClickListener singleButtonOnClickListener;

    // test
    private List<RadioButton> itemRadioButtonList;

    public MyOptionGridAdapter(Context context, List<ChoiceOptionModel> optionList) {
        this.context = context;
        this.optionList = optionList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemRadioButtonList = new ArrayList<>();
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
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item_option_slide_question, parent, false);
        }

        // set option image
        if (optionList.get(position).getChoiceOptionImage() != null){
            imageView = convertView.findViewById(R.id.img_option_grid_item);
            imageView.setImageBitmap(optionList.get(position).getChoiceOptionImage());
        }

        // set radio Button
        choiceButtonsLinearLayout = convertView.findViewById(R.id.ll_option_grid_item);
        RadioButton radioButton = new RadioButton(context);
        radioButton.setText(optionList.get(position).getChoiceOptionContent());
        choiceButtonsLinearLayout.addView(radioButton);
        radioButton.setOnClickListener(v -> singleButtonOnClickListener.onSingleButtonClick(position));
        if ((position != 0) || itemRadioButtonList.isEmpty()){
            itemRadioButtonList.add(radioButton);
        }

        return convertView;
    }

    public List<RadioButton> getItemRadioButtonList() {
        return itemRadioButtonList;
    }
}
