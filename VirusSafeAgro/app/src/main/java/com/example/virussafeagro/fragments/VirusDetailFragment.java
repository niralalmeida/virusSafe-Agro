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
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class VirusDetailFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;

    private RelativeLayout virusDetailRelativeLayout;

    private TextView virusFullNameTextView;
    private TextView virusAbbreviationTextView;
    private ImageView virusPictureImageView;

    private Button descriptionButton;
    private Button symptomsButton;
    private Button causesButton;
    private Button preventionButton;

    private NestedScrollView descriptionNestedScrollView;
    private NestedScrollView symptomsNestedScrollView;
    private NestedScrollView causesNestedScrollView;
    private NestedScrollView preventionNestedScrollView;

    private TextView descriptionTextView;
    private TextView symptomsTextView;
    private TextView causesTextView;
    private TextView preventionTextView;
    
    private Button takeQuizButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_detail, container, false);
        this.initializeViews();

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Virus Details");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

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

        // show virus details
        MyAnimationBox.runFadeInAnimation(this.virusDetailRelativeLayout, 1000);
        this.showVirusDetails();

        // initialize description content
        this.initializeDescriptionContent();

        // set top buttons listener
        this.setTopButtonsListener();
    }

    private void initializeViews() {
        this.virusDetailRelativeLayout = view.findViewById(R.id.rl_virus_detail);

        this.virusFullNameTextView = view.findViewById(R.id.tv_full_name_virus_detail);
        this.virusAbbreviationTextView = view.findViewById(R.id.tv_abbreviation_virus_detail);
        this.virusPictureImageView = view.findViewById(R.id.img_top_pic_virus_detail);

        this.descriptionButton = view.findViewById(R.id.btn_description_virus_detail);
        this.symptomsButton = view.findViewById(R.id.btn_symptom_virus_detail);
        this.causesButton = view.findViewById(R.id.btn_causes_virus_detail);
        this.preventionButton = view.findViewById(R.id.btn_prevention_virus_detail);

        this.descriptionNestedScrollView = view.findViewById(R.id.nsv_description_virus_detail);
        this.symptomsNestedScrollView = view.findViewById(R.id.nsv_symptom_virus_detail);
        this.causesNestedScrollView = view.findViewById(R.id.nsv_causes_virus_detail);
        this.preventionNestedScrollView = view.findViewById(R.id.nsv_prevention_virus_detail);

        this.descriptionTextView = view.findViewById(R.id.tv_description_virus_detail);
        this.symptomsTextView = view.findViewById(R.id.tv_symptoms_virus_detail);
        this.causesTextView = view.findViewById(R.id.tv_causes_virus_detail);
        this.preventionTextView = view.findViewById(R.id.tv_prevention_virus_detail);
        this.takeQuizButton = view.findViewById(R.id.btn_take_quiz_virus_detail);
    }

    private void initializeDescriptionContent() {
        descriptionButton.setEnabled(false);
        symptomsButton.setEnabled(true);
        causesButton.setEnabled(true);
        preventionButton.setEnabled(true);

        MyAnimationBox.runFadeInAnimation(descriptionNestedScrollView, 1000);
        symptomsNestedScrollView.setVisibility(View.GONE);
        causesNestedScrollView.setVisibility(View.GONE);
        preventionNestedScrollView.setVisibility(View.GONE);
    }

    private void setTopButtonsListener() {
        this.descriptionButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                initializeDescriptionContent();
            }
        });
        this.symptomsButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                descriptionButton.setEnabled(true);
                buttonView.setEnabled(false);
                causesButton.setEnabled(true);
                preventionButton.setEnabled(true);

                descriptionNestedScrollView.setVisibility(View.GONE);
                MyAnimationBox.runFadeInAnimation(symptomsNestedScrollView, 1000);
                causesNestedScrollView.setVisibility(View.GONE);
                preventionNestedScrollView.setVisibility(View.GONE);
            }
        });
        this.causesButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                descriptionButton.setEnabled(true);
                symptomsButton.setEnabled(true);
                buttonView.setEnabled(false);
                preventionButton.setEnabled(true);

                descriptionNestedScrollView.setVisibility(View.GONE);
                symptomsNestedScrollView.setVisibility(View.GONE);
                MyAnimationBox.runFadeInAnimation(causesNestedScrollView, 1000);
                preventionNestedScrollView.setVisibility(View.GONE);
            }
        });
        this.preventionButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                descriptionButton.setEnabled(true);
                symptomsButton.setEnabled(true);
                causesButton.setEnabled(true);
                buttonView.setEnabled(false);

                descriptionNestedScrollView.setVisibility(View.GONE);
                symptomsNestedScrollView.setVisibility(View.GONE);
                causesNestedScrollView.setVisibility(View.GONE);
                MyAnimationBox.runFadeInAnimation(preventionNestedScrollView, 1000);
            }
        });
    }

    private void showVirusDetails() {
        // full name title
        this.virusFullNameTextView.setText(this.currentVirusModel.getVirusFullName());
        // abbreviation title
        if (!this.currentVirusModel.getVirusAbbreviation().isEmpty()) {
            String abbreviationString = " (" + this.currentVirusModel.getVirusAbbreviation() + ")";
            this.virusAbbreviationTextView.setText(abbreviationString);
        } else {
            this.virusAbbreviationTextView.setVisibility(View.GONE);
        }

        // description
        LinearLayout linearLayoutForDescription = view.findViewById(R.id.ll_description_virus_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForDescription, this.descriptionTextView, this.currentVirusModel.getVirusDescription());

        // symptom
        LinearLayout linearLayoutForSymptom = view.findViewById(R.id.ll_symptoms_virus_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForSymptom, this.symptomsTextView, this.currentVirusModel.getSymptoms());

        // causes
        LinearLayout linearLayoutForCauses = view.findViewById(R.id.ll_causes_virus_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForCauses, this.causesTextView, this.currentVirusModel.getCauses());

        // prevention
        LinearLayout linearLayoutForPrevention = view.findViewById(R.id.ll_prevention_virus_detail);
        this.hideLinearLayoutIfItemIsEmpty(linearLayoutForPrevention, this.preventionTextView, this.currentVirusModel.getPrevention());

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
            FragmentOperator.replaceFragment(requireActivity(), virusQuizQuestionFragment, AppResources.FRAGMENT_TAG_VIRUS_QUIZ_QUESTION);
        });
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
