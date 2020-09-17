package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class NutrientDetailFragment extends Fragment {
    private View view;
    private NutrientModel currentNutrientModel;
    private String nutrientCorrectionMethodMessage;

    private RelativeLayout nutrientDetailRelativeLayout;

    private TextView nutrientNameTextView;
    private ImageView nutrientPictureImageView;

    private Button nutrientSymptomsButton;
    private Button nutrientReasonsButton;
    private Button nutrientFactoryButton;
    private Button nutrientCorrectionMethodButton;

    private NestedScrollView nutrientSymptomsNestedScrollView;
    private NestedScrollView nutrientReasonsNestedScrollView;
    private NestedScrollView nutrientFactoryNestedScrollView;
    private NestedScrollView nutrientCorrectionMethodNestedScrollView;

    private TextView nutrientSymptomsTextView;
    private TextView nutrientReasonsTextView;
    private TextView nutrientFactoryTextView;
    private TextView nutrientCorrectionMethodTextView;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_nutrient_detail, container, false);
        this.initializeViews();

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Nutrient Details");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // get passed bundle and the NutrientModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentNutrientModel = bundle.getParcelable("currentNutrientModel");
        this.nutrientCorrectionMethodMessage = bundle.getString("nutrientCorrectionMethod");

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show nutrient details
        MyAnimationBox.runFadeInAnimation(this.nutrientDetailRelativeLayout, 1000);
        this.showNutrientDetails();

        // initialize nutrientSymptoms content / or nutrientCorrectionMethod content
        if (this.nutrientCorrectionMethodMessage != null){
            // show nutrientCorrectionMethod
            this.showPreventionContent();
        } else {
            // show nutrientSymptoms
            this.showDescriptionContent();
        }

        // set top buttons listener
        this.setTopButtonsListener();
    }

    private void initializeViews() {
        this.nutrientDetailRelativeLayout = view.findViewById(R.id.rl_nutrient_detail);

        this.nutrientNameTextView = view.findViewById(R.id.tv_name_nutrient_detail);
        this.nutrientPictureImageView = view.findViewById(R.id.img_top_pic_nutrient_detail);

        this.nutrientSymptomsButton = view.findViewById(R.id.btn_symptoms_nutrient_detail);
        this.nutrientReasonsButton = view.findViewById(R.id.btn_reasons_nutrient_detail);
        this.nutrientFactoryButton = view.findViewById(R.id.btn_factory_nutrient_detail);
        this.nutrientCorrectionMethodButton = view.findViewById(R.id.btn_correction_method_nutrient_detail);

        this.nutrientSymptomsNestedScrollView = view.findViewById(R.id.nsv_symptoms_nutrient_detail);
        this.nutrientReasonsNestedScrollView = view.findViewById(R.id.nsv_reasons_nutrient_detail);
        this.nutrientFactoryNestedScrollView = view.findViewById(R.id.nsv_factory_nutrient_detail);
        this.nutrientCorrectionMethodNestedScrollView = view.findViewById(R.id.nsv_correction_method_nutrient_detail);

        this.nutrientSymptomsTextView = view.findViewById(R.id.tv_symptoms_nutrient_detail);
        this.nutrientReasonsTextView = view.findViewById(R.id.tv_reasons_nutrient_detail);
        this.nutrientFactoryTextView = view.findViewById(R.id.tv_factory_nutrient_detail);
        this.nutrientCorrectionMethodTextView = view.findViewById(R.id.tv_correction_method_nutrient_detail);
    }

    private void showDescriptionContent() {
        nutrientSymptomsButton.setEnabled(false);
        nutrientReasonsButton.setEnabled(true);
        nutrientFactoryButton.setEnabled(true);
        nutrientCorrectionMethodButton.setEnabled(true);

        MyAnimationBox.runFadeInAnimation(nutrientSymptomsNestedScrollView, 1000);
        nutrientReasonsNestedScrollView.setVisibility(View.GONE);
        nutrientFactoryNestedScrollView.setVisibility(View.GONE);
        nutrientCorrectionMethodNestedScrollView.setVisibility(View.GONE);
    }

    private void showPreventionContent() {
        nutrientSymptomsButton.setEnabled(true);
        nutrientReasonsButton.setEnabled(true);
        nutrientFactoryButton.setEnabled(true);
        nutrientCorrectionMethodButton.setEnabled(false);

        nutrientSymptomsNestedScrollView.setVisibility(View.GONE);
        nutrientReasonsNestedScrollView.setVisibility(View.GONE);
        nutrientFactoryNestedScrollView.setVisibility(View.GONE);
        MyAnimationBox.runFadeInAnimation(nutrientCorrectionMethodNestedScrollView, 1000);
    }

    private void setTopButtonsListener() {
        this.nutrientSymptomsButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showDescriptionContent();
            }
        });
        this.nutrientReasonsButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                nutrientSymptomsButton.setEnabled(true);
                buttonView.setEnabled(false);
                nutrientFactoryButton.setEnabled(true);
                nutrientCorrectionMethodButton.setEnabled(true);

                nutrientSymptomsNestedScrollView.setVisibility(View.GONE);
                MyAnimationBox.runFadeInAnimation(nutrientReasonsNestedScrollView, 1000);
                nutrientFactoryNestedScrollView.setVisibility(View.GONE);
                nutrientCorrectionMethodNestedScrollView.setVisibility(View.GONE);
            }
        });
        this.nutrientFactoryButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                nutrientSymptomsButton.setEnabled(true);
                nutrientReasonsButton.setEnabled(true);
                buttonView.setEnabled(false);
                nutrientCorrectionMethodButton.setEnabled(true);

                nutrientSymptomsNestedScrollView.setVisibility(View.GONE);
                nutrientReasonsNestedScrollView.setVisibility(View.GONE);
                MyAnimationBox.runFadeInAnimation(nutrientFactoryNestedScrollView, 1000);
                nutrientCorrectionMethodNestedScrollView.setVisibility(View.GONE);
            }
        });
        this.nutrientCorrectionMethodButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showPreventionContent();
            }
        });
    }

    private void showNutrientDetails() {
        // full name title
        this.nutrientNameTextView.setText(this.currentNutrientModel.getNutrientName());

        // symptoms
        LinearLayout linearLayoutForDescription = view.findViewById(R.id.ll_symptoms_nutrient_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForDescription, this.nutrientSymptomsTextView, this.currentNutrientModel.getNutrientSymptom());

        // reasons
        LinearLayout linearLayoutForSymptom = view.findViewById(R.id.ll_reasons_nutrient_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForSymptom, this.nutrientReasonsTextView, this.currentNutrientModel.getNutrientReason());

        // factory
        LinearLayout linearLayoutForCauses = view.findViewById(R.id.ll_factory_nutrient_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForCauses, this.nutrientFactoryTextView, this.currentNutrientModel.getNutrientFactory());

        // nutrient Correction Method
        LinearLayout linearLayoutForPrevention = view.findViewById(R.id.ll_correction_method_nutrient_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForPrevention, this.nutrientCorrectionMethodTextView, this.currentNutrientModel.getNutrientCorrectionMethod());

        // image
        int nutrientPictureDrawableId = AppResources.getNutrientPictureDrawableId(this.currentNutrientModel.getNutrientId());
        Bitmap nutrientPictureBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), nutrientPictureDrawableId);
        this.nutrientPictureImageView.setImageBitmap(nutrientPictureBitmap);
    }


    private void hideLinearLayoutIfItemIsEmpty(LinearLayout linearLayout, TextView textView, String itemContent) {
        if (itemContent == null || itemContent.isEmpty() || itemContent.trim().equals("")){
            ViewGroup.LayoutParams lp = linearLayout.getLayoutParams();
            lp.width=0;
            lp.height=0;
            linearLayout.setLayoutParams(lp);
        } else {
            textView.setText(itemContent);
        }
    }
}
