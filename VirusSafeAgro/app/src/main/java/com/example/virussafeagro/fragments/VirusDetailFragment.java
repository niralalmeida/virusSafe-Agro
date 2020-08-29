package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;
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
    private Button backButton;


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
        // set back button on click listener
        this.setBackButtonOnClickListener();

        showVirusDetails();
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
        this.backButton = view.findViewById(R.id.btn_back_virus_detail);
    }

    private void showVirusDetails() {
        this.virusFullNameTextView.setText(this.currentVirusModel.getVirusFullName());
        this.virusAbbreviationTextView.setText(this.currentVirusModel.getVirusAbbreviation());
        this.symptomsTextView.setText(this.currentVirusModel.getSymptoms());
        this.spreadTextView.setText(this.currentVirusModel.getSpread());
        this.preventionTextView.setText(this.currentVirusModel.getPrevention());
        this.distributionTextView.setText(this.currentVirusModel.getDistribution());

//        this.virusPictureImageView.setImageBitmap();
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

    private void setBackButtonOnClickListener() {
        this.backButton.setOnClickListener(view -> {
            FragmentOperator.backToLastFragment(requireActivity());
        });
    }
}
