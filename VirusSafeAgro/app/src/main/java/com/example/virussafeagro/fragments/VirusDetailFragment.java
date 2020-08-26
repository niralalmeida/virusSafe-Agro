package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.R;
import com.example.virussafeagro.entities.Virus;

public class VirusDetailFragment extends Fragment {
    private View view;
    private Virus currentVirus;

    private TextView virusFullNameTextView;
    private TextView virusAbbreviationTextView;
    private ImageView virusPictureImageView;
    private TextView symptomsTextView;
    private TextView spreadTextView;
    private TextView preventionTextView;
    private TextView distributionTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_detail, container, false);
        this.initializeViews();

        // get passed bundle and the Virus within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirus = bundle.getParcelable("currentVirus");

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

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
    }

    private void showVirusDetails() {
        this.virusFullNameTextView.setText(this.currentVirus.getVirusFullName());
        this.virusAbbreviationTextView.setText(this.currentVirus.getVirusAbbreviation());
        this.symptomsTextView.setText(this.currentVirus.getSymptoms());
        this.spreadTextView.setText(this.currentVirus.getSpread());
        this.preventionTextView.setText(this.currentVirus.getPrevention());
        this.distributionTextView.setText(this.currentVirus.getDistribution());

//        this.virusPictureImageView.setImageBitmap();
    }
}
