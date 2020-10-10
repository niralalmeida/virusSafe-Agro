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
import com.example.virussafeagro.models.NutrientCorrectionMethodModel;
import com.example.virussafeagro.models.NutrientFactorModel;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.models.NutrientReasonModel;
import com.example.virussafeagro.models.NutrientSymptomModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class NutrientDetailFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    private NutrientModel currentNutrientModel;

    private RelativeLayout nutrientDetailRelativeLayout;

    private TextView nutrientNameTextView;
    private ImageView nutrientPictureImageView;

    private Button nutrientSymptomsButton;
    private Button nutrientReasonsButton;
    private Button nutrientFactorsButton;
    private Button nutrientCorrectionMethodButton;

    private NestedScrollView nutrientSymptomsNestedScrollView;
    private NestedScrollView nutrientReasonsNestedScrollView;
    private NestedScrollView nutrientFactorsNestedScrollView;
    private NestedScrollView nutrientCorrectionMethodNestedScrollView;

    private TextView nutrientSymptomsTextView;
    private TextView nutrientReasonsTextView;
    private TextView nutrientFactorsTextView;
    private TextView nutrientCorrectionMethodTextView;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_nutrient_detail, container, false);
        this.initializeViews();

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_nutrient_detail);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // get passed bundle and the NutrientModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentNutrientModel = bundle.getParcelable("currentNutrientModel");

        // set menu selected item
        if (!this.mainActivity.isLearnIconClicked()) {
            this.mainActivity.setLearnButton(true);
        }

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show nutrient details
        MyAnimationBox.runFadeInAnimation(this.nutrientDetailRelativeLayout, 1000);
        this.mainActivity.moveTipAndMoreToRight(getTag(), 500);
        this.showNutrientDetails();
        this.showSymptomsContent();

        // set top buttons listener
        this.setTopButtonsListener();
    }

    private void initializeViews() {
        this.nutrientDetailRelativeLayout = view.findViewById(R.id.rl_nutrient_detail);

        this.nutrientNameTextView = view.findViewById(R.id.tv_name_nutrient_detail);
        this.nutrientPictureImageView = view.findViewById(R.id.img_top_pic_nutrient_detail);

        this.nutrientSymptomsButton = view.findViewById(R.id.btn_symptoms_nutrient_detail);
        this.nutrientReasonsButton = view.findViewById(R.id.btn_reasons_nutrient_detail);
        this.nutrientFactorsButton = view.findViewById(R.id.btn_factors_nutrient_detail);
        this.nutrientCorrectionMethodButton = view.findViewById(R.id.btn_correction_method_nutrient_detail);

        this.nutrientSymptomsNestedScrollView = view.findViewById(R.id.nsv_symptoms_nutrient_detail);
        this.nutrientReasonsNestedScrollView = view.findViewById(R.id.nsv_reasons_nutrient_detail);
        this.nutrientFactorsNestedScrollView = view.findViewById(R.id.nsv_factors_nutrient_detail);
        this.nutrientCorrectionMethodNestedScrollView = view.findViewById(R.id.nsv_correction_method_nutrient_detail);

        this.nutrientSymptomsTextView = view.findViewById(R.id.tv_symptoms_nutrient_detail);
        this.nutrientReasonsTextView = view.findViewById(R.id.tv_reasons_nutrient_detail);
        this.nutrientFactorsTextView = view.findViewById(R.id.tv_factors_nutrient_detail);
        this.nutrientCorrectionMethodTextView = view.findViewById(R.id.tv_correction_method_nutrient_detail);
    }

    private void setTopButtonsListener() {
        this.nutrientSymptomsButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showSymptomsContent();
            }
        });
        this.nutrientReasonsButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showReasonContent();
            }
        });
        this.nutrientFactorsButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showFactorContent();
            }
        });
        this.nutrientCorrectionMethodButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showCorrectionMethodContent();
            }
        });
    }

    private void showSymptomsContent() {
        nutrientSymptomsButton.setEnabled(false);
        nutrientReasonsButton.setEnabled(true);
        nutrientFactorsButton.setEnabled(true);
        nutrientCorrectionMethodButton.setEnabled(true);

        MyAnimationBox.runFadeInAnimation(nutrientSymptomsNestedScrollView, 1000);
        nutrientReasonsNestedScrollView.setVisibility(View.GONE);
        nutrientFactorsNestedScrollView.setVisibility(View.GONE);
        nutrientCorrectionMethodNestedScrollView.setVisibility(View.GONE);
    }

    private void showReasonContent() {
        nutrientSymptomsButton.setEnabled(true);
        nutrientReasonsButton.setEnabled(false);
        nutrientFactorsButton.setEnabled(true);
        nutrientCorrectionMethodButton.setEnabled(true);

        nutrientSymptomsNestedScrollView.setVisibility(View.GONE);
        MyAnimationBox.runFadeInAnimation(nutrientReasonsNestedScrollView, 1000);
        nutrientFactorsNestedScrollView.setVisibility(View.GONE);
        nutrientCorrectionMethodNestedScrollView.setVisibility(View.GONE);
    }

    private void showFactorContent() {
        nutrientSymptomsButton.setEnabled(true);
        nutrientReasonsButton.setEnabled(true);
        nutrientFactorsButton.setEnabled(false);
        nutrientCorrectionMethodButton.setEnabled(true);

        nutrientSymptomsNestedScrollView.setVisibility(View.GONE);
        nutrientReasonsNestedScrollView.setVisibility(View.GONE);
        MyAnimationBox.runFadeInAnimation(nutrientFactorsNestedScrollView, 1000);
        nutrientCorrectionMethodNestedScrollView.setVisibility(View.GONE);
    }

    private void showCorrectionMethodContent() {
        nutrientSymptomsButton.setEnabled(true);
        nutrientReasonsButton.setEnabled(true);
        nutrientFactorsButton.setEnabled(true);
        nutrientCorrectionMethodButton.setEnabled(false);

        nutrientSymptomsNestedScrollView.setVisibility(View.GONE);
        nutrientReasonsNestedScrollView.setVisibility(View.GONE);
        nutrientFactorsNestedScrollView.setVisibility(View.GONE);
        MyAnimationBox.runFadeInAnimation(nutrientCorrectionMethodNestedScrollView, 1000);
    }

    private void showNutrientDetails() {
        // full name title
        this.nutrientNameTextView.setText(this.currentNutrientModel.getNutrientName());

        // symptoms
        LinearLayout linearLayoutForDescription = view.findViewById(R.id.ll_symptoms_nutrient_detail);
        StringBuilder nutrientSymptomStringBuilder = new StringBuilder();
        for (NutrientSymptomModel nutrientSymptomModel : this.currentNutrientModel.getNutrientSymptomList()) {
            // without type
            nutrientSymptomStringBuilder.append("- ").append(nutrientSymptomModel.getSymContent()).append("\n");
        }
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForDescription, this.nutrientSymptomsTextView, nutrientSymptomStringBuilder.toString());

        // reasons
        LinearLayout linearLayoutForSymptom = view.findViewById(R.id.ll_reasons_nutrient_detail);
        StringBuilder nutrientReasonStringBuilder = new StringBuilder();
        for (NutrientReasonModel nutrientReasonModel : this.currentNutrientModel.getNutrientReasonList()) {
            nutrientReasonStringBuilder.append("- ").append(nutrientReasonModel.getReasonContent()).append("\n");
        }
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForSymptom, this.nutrientReasonsTextView, nutrientReasonStringBuilder.toString());

        // factors
        LinearLayout linearLayoutForCauses = view.findViewById(R.id.ll_factors_nutrient_detail);
        StringBuilder nutrientFactorStringBuilder = new StringBuilder();
        for (NutrientFactorModel nutrientFactorModel : this.currentNutrientModel.getNutrientFactorList()) {
            nutrientFactorStringBuilder.append("- ").append(nutrientFactorModel.getFactorContent()).append("\n");
        }
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForCauses, this.nutrientFactorsTextView, nutrientFactorStringBuilder.toString());

        // nutrient Correction Method
        LinearLayout linearLayoutForPrevention = view.findViewById(R.id.ll_correction_method_nutrient_detail);
        StringBuilder nutrientCorrectionMethodStringBuilder = new StringBuilder();
        for (NutrientCorrectionMethodModel nutrientCorrectionMethodModel : this.currentNutrientModel.getNutrientCorrectionMethodList()) {
            nutrientCorrectionMethodStringBuilder.append("- ").append(nutrientCorrectionMethodModel.getMethodContent()).append("\n");
        }
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForPrevention, this.nutrientCorrectionMethodTextView, nutrientCorrectionMethodStringBuilder.toString());

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

    @Override
    public void onPause() {
        super.onPause();
        this.mainActivity.setLearnButton(false);
    }
}
