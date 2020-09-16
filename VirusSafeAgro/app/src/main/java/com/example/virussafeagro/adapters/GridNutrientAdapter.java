package com.example.virussafeagro.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.uitilities.AppResources;

import java.util.List;

public class GridNutrientAdapter extends BaseAdapter {

    private FragmentActivity fragmentActivity;
    private LayoutInflater layoutInflater;

    private List<NutrientModel> nutrientModelList;

    private ImageView nutrientImageView;
    private TextView nutrientNameTextView;
    private RelativeLayout nutrientRelativeLayout;

    private GridNutrientAdapter.NutrientCardClickListener nutrientCardClickListener;

    public GridNutrientAdapter(FragmentActivity fragmentActivity, List<NutrientModel> nutrientModelList) {
        this.fragmentActivity = fragmentActivity;
        this.layoutInflater = (LayoutInflater) fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.nutrientModelList = nutrientModelList;
    }

    public interface NutrientCardClickListener{
        void onNutrientCardClick(int position);
    }
    public void setOnNutrientCardClickListener(GridNutrientAdapter.NutrientCardClickListener nutrientCardClickListener){
        this.nutrientCardClickListener = nutrientCardClickListener;
    }

    @Override
    public int getCount() {
        return this.nutrientModelList.size();
    }

    @Override
    public NutrientModel getItem(int position) {
        return nutrientModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item_card_nutrient, parent, false);

            // initialize views
            this.nutrientImageView = convertView.findViewById(R.id.img_nutrient_grid_item);
            this.nutrientNameTextView = convertView.findViewById(R.id.tv_name_nutrient_list);

            // get nutrient model
            NutrientModel nutrientModel = nutrientModelList.get(position);

            // set nutrient image
//            int nutrientPictureDrawableId = AppResources.getNutrientPictureDrawableId(nutrientModel.getNutrientId());
//            Bitmap nutrientPictureBitmap = BitmapFactory.decodeResource(fragmentActivity.getResources(), nutrientPictureDrawableId);
//            this.nutrientImageView.setImageBitmap(nutrientPictureBitmap);

            // set nutrient full name
            this.nutrientNameTextView.setText(nutrientModel.getNutrientName());

            // set card on click listener
            this.nutrientRelativeLayout = convertView.findViewById(R.id.rl_nutrient_list);
            this.nutrientRelativeLayout.setOnClickListener(v -> nutrientCardClickListener.onNutrientCardClick(position));

        }
        return convertView;
    }
}
