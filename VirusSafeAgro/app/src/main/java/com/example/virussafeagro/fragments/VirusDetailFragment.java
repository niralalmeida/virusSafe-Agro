package com.example.virussafeagro.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusCauseModel;
import com.example.virussafeagro.models.VirusDescriptionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.models.VirusPreventionModel;
import com.example.virussafeagro.models.VirusSymptomModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class VirusDetailFragment extends Fragment {
    private View view;
    private VirusModel currentVirusModel;
    private String preventionMessage;

    // all view except quiz button
    private LinearLayout virusDetailLinearLayout;
    // top views
    private TextView virusFullNameTextView;
    private TextView virusAbbreviationTextView;
    private ImageView virusPictureImageView;
    // top buttons
    private Button descriptionButton;
    private Button symptomsButton;
    private Button causesButton;
    private Button preventionButton;
    // middle views
    private NestedScrollView middleContentNestedScrollView;
    private LinearLayout middleContentLinearLayout;
    private TextView middleContentTextView;
    // bottom quiz button views
    private LinearLayout takeQuizLinearLayout;
    private Button takeQuizButton;

    // coordinate
    private float startX;
    private float startY;
    private float currentX;
    private float currentY;

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
        this.preventionMessage = bundle.getString("prevention");



        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // set take quiz button on click listener
        this.setTakeQuizButtonOnClickListener();

        // show virus details
        MyAnimationBox.runFadeInAnimation(this.virusDetailLinearLayout, 1000);
        MyAnimationBox.runFadeInAnimation(this.takeQuizLinearLayout, 1000);
        this.showVirusDetails();

        // initialize description content / or prevention content
        if (this.preventionMessage != null){
            // show prevention
            this.showPreventionContent();
        } else {
            // show description
            this.showDescriptionContent();
        }

        // set top buttons listener
        this.setTopButtonsListener();

        setOnSwipeUpListener();
    }

    private void initializeViews() {
        this.virusDetailLinearLayout = view.findViewById(R.id.ll_virus_detail);
        this.virusFullNameTextView = view.findViewById(R.id.tv_full_name_virus_detail);
        this.virusAbbreviationTextView = view.findViewById(R.id.tv_abbreviation_virus_detail);
        this.virusPictureImageView = view.findViewById(R.id.img_top_pic_virus_detail);
        this.descriptionButton = view.findViewById(R.id.btn_description_virus_detail);
        this.symptomsButton = view.findViewById(R.id.btn_symptom_virus_detail);
        this.causesButton = view.findViewById(R.id.btn_causes_virus_detail);
        this.preventionButton = view.findViewById(R.id.btn_prevention_virus_detail);
        this.middleContentNestedScrollView = view.findViewById(R.id.nsv_middle_content_virus_detail);
        this.middleContentLinearLayout= view.findViewById(R.id.ll_middle_content_virus_detail);
        this.middleContentTextView = view.findViewById(R.id.tv_middle_content_virus_detail);
        this.takeQuizLinearLayout = view.findViewById(R.id.ll_take_quiz_virus_detail);
        this.takeQuizButton = view.findViewById(R.id.btn_take_quiz_virus_detail);
    }

    private void showDescriptionContent() {
        // set buttons
        descriptionButton.setEnabled(false);
        symptomsButton.setEnabled(true);
        causesButton.setEnabled(true);
        preventionButton.setEnabled(true);
        // show description
        StringBuilder virusDescriptionStringBuilder = new StringBuilder();
        for (VirusDescriptionModel virusDescriptionModel : this.currentVirusModel.getVirusDescriptionModelList()) {
            virusDescriptionStringBuilder.append("- ").append(virusDescriptionModel.getDesContent()).append("\n");
        }
        this.hideLinearLayoutIfItemIsEmpty(virusDescriptionStringBuilder.toString());
        // show the fade in animation
        MyAnimationBox.runFadeInAnimation(middleContentNestedScrollView, 1000);
    }

    private void showSymptomContent() {
        // set buttons
        descriptionButton.setEnabled(true);
        symptomsButton.setEnabled(false);
        causesButton.setEnabled(true);
        preventionButton.setEnabled(true);
        // show symptom
        StringBuilder virusSymptomStringBuilder = new StringBuilder();
        for (VirusSymptomModel virusSymptomModel : this.currentVirusModel.getVirusSymptomModelList()) {
            // without type
            virusSymptomStringBuilder.append("- ").append(virusSymptomModel.getSymContent()).append("\n");
        }
        this.hideLinearLayoutIfItemIsEmpty(virusSymptomStringBuilder.toString());
        // show the fade in animation
        MyAnimationBox.runFadeInAnimation(middleContentNestedScrollView, 1000);
    }

    private void showCauseContent() {
        // set buttons
        descriptionButton.setEnabled(true);
        symptomsButton.setEnabled(true);
        causesButton.setEnabled(false);
        preventionButton.setEnabled(true);
        // causes
        StringBuilder virusCauseStringBuilder = new StringBuilder();
        for (VirusCauseModel virusCauseModel : this.currentVirusModel.getVirusCauseModelList()) {
            virusCauseStringBuilder.append("- ").append(virusCauseModel.getCauseContent()).append("\n");
        }
        this.hideLinearLayoutIfItemIsEmpty(virusCauseStringBuilder.toString());
        // show the fade in animation
        MyAnimationBox.runFadeInAnimation(middleContentNestedScrollView, 1000);
    }

    private void showPreventionContent() {
        // set buttons
        descriptionButton.setEnabled(true);
        symptomsButton.setEnabled(true);
        causesButton.setEnabled(true);
        preventionButton.setEnabled(false);
        // prevention
        StringBuilder virusPreventionStringBuilder = new StringBuilder();
        for (VirusPreventionModel virusPreventionModel : this.currentVirusModel.getVirusPreventionModelList()) {
            virusPreventionStringBuilder.append("- ").append(virusPreventionModel.getPreContent()).append("\n");
        }
        this.hideLinearLayoutIfItemIsEmpty(virusPreventionStringBuilder.toString());
        // show the fade in animation
        MyAnimationBox.runFadeInAnimation(middleContentNestedScrollView, 1000);
    }

    private void setTopButtonsListener() {
        this.descriptionButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showDescriptionContent();
            }
        });
        this.symptomsButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showSymptomContent();
            }
        });
        this.causesButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showCauseContent();
            }
        });
        this.preventionButton.setOnClickListener(buttonView -> {
            if (buttonView.isEnabled()){
                showPreventionContent();
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
        // virus image
        int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(this.currentVirusModel.getVirusId());
        Bitmap virusPictureBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), virusPictureDrawableId);
        this.virusPictureImageView.setImageBitmap(virusPictureBitmap);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnSwipeUpListener() {
        view.setOnTouchListener((v, event) -> {
            // when press
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                startX = event.getX();
                startY = event.getY();
            }
            // when move
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                currentX = event.getX();
                currentY = event.getY();
                if (currentY < startY) {
                    System.out.println("up!!!!");
                }
            }
            return true;
        });
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

    private void hideLinearLayoutIfItemIsEmpty(String itemContent) {
        if (itemContent == null || itemContent.isEmpty() || itemContent.trim().equals("")){
            ViewGroup.LayoutParams lp = middleContentLinearLayout.getLayoutParams();
            lp.width=0;
            lp.height=0;
            middleContentLinearLayout.setLayoutParams(lp);
        } else {
            middleContentTextView.setText(itemContent);
        }
    }
}
