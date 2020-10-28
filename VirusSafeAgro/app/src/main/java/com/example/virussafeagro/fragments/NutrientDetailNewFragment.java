package com.example.virussafeagro.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.GalleryActivity;
import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.QuizActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.models.NutrientSymptomModel;
import com.example.virussafeagro.uitilities.DataConverter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionLayout;
import com.squareup.picasso.Picasso;

public class NutrientDetailNewFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    public static NutrientModel currentNutrientModel;

    // views
    private ImageView nutrientPictureImageView;
    private CollapsingToolbarLayout nutrientDetailCollapsingToolbarLayout;
    private androidx.appcompat.widget.Toolbar nutrientDetailToolbar;
    
    private LinearLayout nutrientSymptomLeafLinearLayout;
    private LinearLayout nutrientSymptomPetiolesLinearLayout;
    private LinearLayout nutrientSymptomFruitLinearLayout;
    private LinearLayout nutrientSymptomStemPetiolesLinearLayout;
    private LinearLayout nutrientSymptomPlantLinearLayout;
    private LinearLayout nutrientSymptomRootsLinearLayout;

    private LinearLayout nutrientSymptomLeafItemsLinearLayout;
    private LinearLayout nutrientSymptomPetiolesItemsLinearLayout;
    private LinearLayout nutrientSymptomFruitItemsLinearLayout;
    private LinearLayout nutrientSymptomStemPetiolesItemsLinearLayout;
    private LinearLayout nutrientSymptomPlantItemsLinearLayout;
    private LinearLayout nutrientSymptomRootsItemsLinearLayout;

    private LinearLayout nutrientReasonLinearLayout;
    private LinearLayout nutrientFactorLinearLayout;
    private LinearLayout nutrientTreatmentLinearLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_nutrient_detail_new, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_nutrient_detail_new);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();

        // get passed bundle and the NutrientModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        currentNutrientModel = bundle.getParcelable("currentNutrientModel");

        // set menu selected item
        if (!this.mainActivity.isLearnIconClicked()) {
            this.mainActivity.setLearnButton(true);
        }

        // move for the toolbar
        new Handler().postDelayed(()->{
            this.mainActivity.moveTipAndMoreToRight(getTag(), 200);
        },200);

        // show views
        this.showViews();

        return this.view;
    }

    private void initializeViews() {
        this.nutrientPictureImageView = view.findViewById(R.id.img_nutrient_detail_new);
        this.nutrientDetailCollapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout_nutrient_detail);
        this.nutrientDetailToolbar = view.findViewById(R.id.toolbar_nutrient_detail);

        this.nutrientSymptomLeafLinearLayout = view.findViewById(R.id.ll_symptom_leaves_nutrient_detail);
        this.nutrientSymptomPetiolesLinearLayout = view.findViewById(R.id.ll_symptom_petioles_nutrient_detail);
        this.nutrientSymptomFruitLinearLayout = view.findViewById(R.id.ll_symptom_fruit_nutrient_detail);
        this.nutrientSymptomStemPetiolesLinearLayout = view.findViewById(R.id.ll_symptom_stems_petioles_nutrient_detail);
        this.nutrientSymptomPlantLinearLayout = view.findViewById(R.id.ll_symptom_plant_nutrient_detail);
        this.nutrientSymptomRootsLinearLayout = view.findViewById(R.id.ll_symptom_roots_nutrient_detail);

        this.nutrientSymptomLeafItemsLinearLayout = view.findViewById(R.id.ll_symptom_leaves_items_nutrient_detail);
        this.nutrientSymptomPetiolesItemsLinearLayout = view.findViewById(R.id.ll_symptom_petioles_items_nutrient_detail);
        this.nutrientSymptomFruitItemsLinearLayout = view.findViewById(R.id.ll_symptom_fruit_items_nutrient_detail);
        this.nutrientSymptomStemPetiolesItemsLinearLayout = view.findViewById(R.id.ll_symptom_stems_petioles_items_nutrient_detail);
        this.nutrientSymptomPlantItemsLinearLayout = view.findViewById(R.id.ll_symptom_plant_items_nutrient_detail);
        this.nutrientSymptomRootsItemsLinearLayout = view.findViewById(R.id.ll_symptom_roots_items_nutrient_detail);

        this.nutrientReasonLinearLayout = view.findViewById(R.id.ll_reasons_nutrient_detail);
        this.nutrientFactorLinearLayout = view.findViewById(R.id.ll_factors_nutrient_detail);
        this.nutrientTreatmentLinearLayout = view.findViewById(R.id.ll_treatment_nutrient_detail);
    }

    private void showViews() {
        // set image view
        this.nutrientPictureImageView.setImageBitmap(NutrientFragment.currentNutrientImageBitmap);
        // set nutrientDetailCollapsingToolbarLayout title color
        this.nutrientDetailCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        this.nutrientDetailCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        // set title -- nutrient abb
        this.nutrientDetailToolbar.setTitle(currentNutrientModel.getNutrientName());

        // nutrient symptom
        for(NutrientSymptomModel nutrientSymptomModel : currentNutrientModel.getNutrientSymptomList()){
            // show linear layout
            if(nutrientSymptomModel.getSymType().equals("l")){
                // set visibility
                nutrientSymptomLeafLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.nutrientSymptomLeafItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(nutrientSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if(nutrientSymptomModel.getSymType().equals("p")){
                // set visibility
                nutrientSymptomPetiolesLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.nutrientSymptomPetiolesItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(nutrientSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if(nutrientSymptomModel.getSymType().equals("f")){
                // set visibility
                nutrientSymptomFruitLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.nutrientSymptomFruitItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(nutrientSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if(nutrientSymptomModel.getSymType().equals("s")){
                // set visibility
                nutrientSymptomStemPetiolesLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.nutrientSymptomStemPetiolesItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(nutrientSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if(nutrientSymptomModel.getSymType().equals("t")){
                // set visibility
                nutrientSymptomPlantLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.nutrientSymptomPlantItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(nutrientSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if(nutrientSymptomModel.getSymType().equals("r")){
                // set visibility
                nutrientSymptomRootsLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.nutrientSymptomRootsItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(nutrientSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            }
        }

        // nutrient reason
        for(NutrientSymptomModel nutrientSymptomModel : currentNutrientModel.getNutrientSymptomList()){
            // set horizontal linear layout
            LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
            horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
            this.nutrientReasonLinearLayout.addView(horizontalLinearLayout);
            LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
            horizontalLinearLayoutLayoutParams.topMargin = 10;
            horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

            // set point
            View pointView = new View(mainActivity);
            pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
            horizontalLinearLayout.addView(pointView);
            LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
            pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
            pointLayoutParams.height = 20;
            pointLayoutParams.width = 20;
            pointView.setLayoutParams(pointLayoutParams);
            // set reason
            TextView reasonTextView = new TextView(mainActivity);
            reasonTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            reasonTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            reasonTextView.setText(nutrientSymptomModel.getSymContent());
            horizontalLinearLayout.addView(reasonTextView);
        }

        // nutrient factor
        for(NutrientSymptomModel nutrientSymptomModel : currentNutrientModel.getNutrientSymptomList()){
            // set horizontal linear layout
            LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
            horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
            this.nutrientFactorLinearLayout.addView(horizontalLinearLayout);
            LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
            horizontalLinearLayoutLayoutParams.topMargin = 10;
            horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

            // set point
            View pointView = new View(mainActivity);
            pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
            horizontalLinearLayout.addView(pointView);
            LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
            pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
            pointLayoutParams.height = 20;
            pointLayoutParams.width = 20;
            pointView.setLayoutParams(pointLayoutParams);
            // set factor
            TextView factorTextView = new TextView(mainActivity);
            factorTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            factorTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            factorTextView.setText(nutrientSymptomModel.getSymContent());
            horizontalLinearLayout.addView(factorTextView);
        }

        // nutrient reason
        for(NutrientSymptomModel nutrientSymptomModel : currentNutrientModel.getNutrientSymptomList()){
            // set horizontal linear layout
            LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
            horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
            this.nutrientTreatmentLinearLayout.addView(horizontalLinearLayout);
            LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
            horizontalLinearLayoutLayoutParams.topMargin = 10;
            horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

            // set point
            View pointView = new View(mainActivity);
            pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
            horizontalLinearLayout.addView(pointView);
            LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
            pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
            pointLayoutParams.height = 20;
            pointLayoutParams.width = 20;
            pointView.setLayoutParams(pointLayoutParams);
            // set treatment
            TextView treatmentTextView = new TextView(mainActivity);
            treatmentTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            treatmentTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            treatmentTextView.setText(nutrientSymptomModel.getSymContent());
            horizontalLinearLayout.addView(treatmentTextView);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        this.mainActivity.setLearnButton(false);
    }
}

