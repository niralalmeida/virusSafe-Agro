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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;

public class VirusDetailFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private TextView virusFullNameTextView;
    private TextView virusAbbreviationTextView;
    private ImageView virusPictureImageView;
    private TextView symptomsTextView;
    private TextView spreadTextView;
    private TextView preventionTextView;
    private TextView distributionTextView;
    private Button takeQuizButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_detail, container, false);
        this.initializeViews();

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirusModel = bundle.getParcelable("currentVirusModel");

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // set take quiz button on click listener
        this.setTakeQuizButtonOnClickListener();

        this.showVirusDetails();
    }

    private void initializeViews() {
        this.virusFullNameTextView = view.findViewById(R.id.tv_title_virus_full_name_virus_detail);
        this.virusAbbreviationTextView = view.findViewById(R.id.tv_sub_title_abbreviation_virus_detail);
        this.virusPictureImageView = view.findViewById(R.id.img_virus_picture_virus_detail);
        this.symptomsTextView = view.findViewById(R.id.tv_symptoms_virus_detail);
        this.spreadTextView = view.findViewById(R.id.tv_spread_virus_detail);
        this.preventionTextView = view.findViewById(R.id.tv_prevention_virus_detail);
        this.distributionTextView = view.findViewById(R.id.tv_distribution_virus_detail);
        this.takeQuizButton = view.findViewById(R.id.btn_take_quiz_virus_detail);
    }

    private void showVirusDetails() {
        this.virusFullNameTextView.setText(this.currentVirusModel.getVirusFullName());
        // abbreviation
        if (!this.currentVirusModel.getVirusAbbreviation().isEmpty()) {
            LinearLayout linearLayoutForSubtitle = view.findViewById(R.id.ll_sub_title_abbreviation_virus_detail);
            linearLayoutForSubtitle.setVisibility(View.VISIBLE);
            this.virusAbbreviationTextView.setText(this.currentVirusModel.getVirusAbbreviation());
        }
        // symptom
        LinearLayout linearLayoutForSymptom = view.findViewById(R.id.ll_symptoms_virus_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForSymptom, this.symptomsTextView, this.currentVirusModel.getSymptoms());

        // spread
        LinearLayout linearLayoutForSpread = view.findViewById(R.id.ll_spread_virus_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForSpread, this.spreadTextView, this.currentVirusModel.getSpread());

        // prevention
        LinearLayout linearLayoutForPrevention = view.findViewById(R.id.ll_prevention_virus_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForPrevention, this.preventionTextView, this.currentVirusModel.getPrevention());

        // distribution
        LinearLayout linearLayoutForDistribution = view.findViewById(R.id.ll_distribution_virus_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForDistribution, this.distributionTextView, this.currentVirusModel.getDistribution());

        int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(this.currentVirusModel.getVirusId());
        Bitmap virusPictureBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), virusPictureDrawableId);
        this.virusPictureImageView.setImageBitmap(virusPictureBitmap);
    }

    private void setTakeQuizButtonOnClickListener() {
        this.takeQuizButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            VirusQuizQuestionFragment virusQuizQuestionFragment = new VirusQuizQuestionFragment();
            virusQuizQuestionFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentNoBackStack(requireActivity(), virusQuizQuestionFragment);
        });
    }

    private void hideLinearLayoutIfItemIsEmpty(LinearLayout linearLayout, TextView textView, String itemContent) {
        if (itemContent.isEmpty()){
            ViewGroup.LayoutParams lp = linearLayout.getLayoutParams();
            lp.width=0;
            lp.height=0;
            linearLayout.setLayoutParams(lp);
        } else {
            textView.setText(itemContent);
        }
    }
}
