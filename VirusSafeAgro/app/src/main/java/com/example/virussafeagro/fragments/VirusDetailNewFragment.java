package com.example.virussafeagro.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.L;
import com.example.virussafeagro.GalleryActivity;
import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.QuizActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.models.VirusCauseModel;
import com.example.virussafeagro.models.VirusDescriptionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.models.VirusSymptomModel;
import com.example.virussafeagro.uitilities.DataConverter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionLayout;

public class VirusDetailNewFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    private VirusModel currentVirusModel;

    // views
    private Button quizButton;
    private ImageView virusPictureImageView;
    private CollapsingToolbarLayout virusDetailCollapsingToolbarLayout;
    private androidx.appcompat.widget.Toolbar virusDetailToolbar;
    private NestedScrollView virusDetailNestedScrollView;
    private TextView virusFullNameTextView;
    private LinearLayout virusDescriptionLinearLayout;
    private LinearLayout virusSymptomLeafLinearLayout;
    private LinearLayout virusSymptomAppearanceLinearLayout;
    private LinearLayout virusSymptomFruitLinearLayout;
    private LinearLayout virusSymptomLeafItemsLinearLayout;
    private LinearLayout virusSymptomAppearanceItemsLinearLayout;
    private LinearLayout virusSymptomFruitItemsLinearLayout;
    private LinearLayout virusCauseTimingLinearLayout;
    private LinearLayout virusCauseECLinearLayout;
    private LinearLayout virusCauseInfectionLinearLayout;
    private LinearLayout virusCauseTimingItemsLinearLayout;
    private LinearLayout virusCauseECItemsLinearLayout;
    private LinearLayout virusCauseInfectionItemsLinearLayout;
    private FloatingActionLayout virusGalleryFloatingActionLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_detail_new, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_virus_detail_new);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();
        // show quiz button in toolbar
        this.quizButton.setVisibility(View.VISIBLE);

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirusModel = bundle.getParcelable("currentVirusModel");

        // set menu selected item
        if (!this.mainActivity.isLearnIconClicked()) {
            this.mainActivity.setLearnButton(true);
        }

        // show views
        this.showViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // set take quiz button on click listener
        this.setTakeQuizButtonOnClickListener();
        // set VirusGalleryFAB On Click Listener
        this.setVirusGalleryFABOnClickListener();
        // show quiz button
        MyAnimationBox.runFadeInAnimation(this.quizButton, 200);
        new Handler().postDelayed(()->{
            this.mainActivity.moveTipAndMoreToRight(getTag(), 200);
        },200);
    }

    private void initializeViews() {
        this.quizButton = this.mainActivity.getQuizButton();
        this.virusPictureImageView = view.findViewById(R.id.img_virus_detail_new);
        this.virusDetailCollapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout_virus_detail);
        this.virusDetailToolbar = view.findViewById(R.id.toolbar_virus_detail);
        this.virusDetailNestedScrollView = view.findViewById(R.id.scrollView_virus_detail);
        this.virusFullNameTextView = view.findViewById(R.id.tv_full_name_virus_detail);
        this.virusDescriptionLinearLayout = view.findViewById(R.id.ll_description_virus_detail);
        this.virusSymptomLeafLinearLayout = view.findViewById(R.id.ll_symptom_leaves_virus_detail);
        this.virusSymptomAppearanceLinearLayout = view.findViewById(R.id.ll_symptom_appearance_virus_detail);
        this.virusSymptomFruitLinearLayout = view.findViewById(R.id.ll_symptom_fruit_virus_detail);
        this.virusSymptomLeafItemsLinearLayout = view.findViewById(R.id.ll_symptom_leaves_items_virus_detail);
        this.virusSymptomAppearanceItemsLinearLayout = view.findViewById(R.id.ll_symptom_appearance_items_virus_detail);
        this.virusSymptomFruitItemsLinearLayout = view.findViewById(R.id.ll_symptom_fruit_items_virus_detail);
        this.virusCauseTimingLinearLayout = view.findViewById(R.id.ll_cause_timing_virus_detail);
        this.virusCauseECLinearLayout = view.findViewById(R.id.ll_cause_ec_virus_detail);
        this.virusCauseInfectionLinearLayout = view.findViewById(R.id.ll_cause_infection_virus_detail);
        this.virusCauseTimingItemsLinearLayout = view.findViewById(R.id.ll_cause_timing_items_virus_detail);
        this.virusCauseECItemsLinearLayout = view.findViewById(R.id.ll_cause_ec_items_virus_detail);
        this.virusCauseInfectionItemsLinearLayout = view.findViewById(R.id.ll_cause_infection_items_virus_detail);
        this.virusGalleryFloatingActionLayout = view.findViewById(R.id.fabl_gallery_virus_detail);
    }

    private void showViews() {
        // set image view
        this.virusPictureImageView.setImageBitmap(VirusInfoListFragment.currentVirusImageBitmap);
        // set virusDetailCollapsingToolbarLayout
        this.virusDetailCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        this.virusDetailCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        // set title -- virus abb
        this.virusDetailToolbar.setTitle(currentVirusModel.getVirusAbbreviation());
        this.virusDetailToolbar.setSubtitleTextColor(getResources().getColor(R.color.colorPrimarySubTitle));

        // virus full name
        this.virusFullNameTextView.setText(currentVirusModel.getVirusFullName());

        // virus description
        for(VirusDescriptionModel virusDescriptionModel : currentVirusModel.getVirusDescriptionModelList()){
            // set horizontal linear layout
            LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
            horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
            this.virusDescriptionLinearLayout.addView(horizontalLinearLayout);
            LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
            horizontalLinearLayoutLayoutParams.topMargin = 10;
            horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

            // set point
            View pointView = new View(mainActivity);
            pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
            horizontalLinearLayout.addView(pointView);
            LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
            pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity,20));
            pointLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            pointLayoutParams.width = DataConverter.dip2px(mainActivity, 5);
            pointView.setLayoutParams(pointLayoutParams);
            // set description
            TextView descriptionTextView = new TextView(mainActivity);
            descriptionTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            descriptionTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            descriptionTextView.setText(virusDescriptionModel.getDesContent());
            horizontalLinearLayout.addView(descriptionTextView);
        }

        // virus symptom
        for(VirusSymptomModel virusSymptomModel : currentVirusModel.getVirusSymptomModelList()){
            // show linear layout
            if(virusSymptomModel.getSymObjectType().equals("l")){
                // set visibility
                virusSymptomLeafLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.virusSymptomLeafItemsLinearLayout.addView(horizontalLinearLayout);
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
                symptomTextView.setText(virusSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if(virusSymptomModel.getSymObjectType().equals("a")){
                // set visibility
                virusSymptomAppearanceLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.virusSymptomAppearanceItemsLinearLayout.addView(horizontalLinearLayout);
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
                symptomTextView.setText(virusSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if(virusSymptomModel.getSymObjectType().equals("f")){
                // set visibility
                virusSymptomFruitLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.virusSymptomFruitItemsLinearLayout.addView(horizontalLinearLayout);
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
                symptomTextView.setText(virusSymptomModel.getSymContent());
                horizontalLinearLayout.addView(symptomTextView);
            }
        }

        // virus cause
        for(VirusCauseModel virusCauseModel : currentVirusModel.getVirusCauseModelList()) {
            // show linear layout
            if (virusCauseModel.getCauseType().equals("t")) {
                // set visibility
                virusCauseTimingLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.virusCauseTimingItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity, 20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(virusCauseModel.getCauseContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if (virusCauseModel.getCauseType().equals("e")) {
                // set visibility
                virusCauseECLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.virusCauseECItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity, 20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(virusCauseModel.getCauseContent());
                horizontalLinearLayout.addView(symptomTextView);
            } else if (virusCauseModel.getCauseType().equals("m")) {
                // set visibility
                virusCauseInfectionLinearLayout.setVisibility(View.VISIBLE);
                // set contents
                // set horizontal linear layout
                LinearLayout horizontalLinearLayout = new LinearLayout(mainActivity);
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.virusCauseInfectionItemsLinearLayout.addView(horizontalLinearLayout);
                LinearLayout.LayoutParams horizontalLinearLayoutLayoutParams = (LinearLayout.LayoutParams) horizontalLinearLayout.getLayoutParams();
                horizontalLinearLayoutLayoutParams.topMargin = 10;
                horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutLayoutParams);

                // set point
                View pointView = new View(mainActivity);
                pointView.setBackgroundResource(R.drawable.shape_green_point_virus_detail);
                horizontalLinearLayout.addView(pointView);
                LinearLayout.LayoutParams pointLayoutParams = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                pointLayoutParams.setMarginEnd(DataConverter.dip2px(mainActivity, 20));
                pointLayoutParams.height = 20;
                pointLayoutParams.width = 20;
                pointView.setLayoutParams(pointLayoutParams);
                // set symptom
                TextView symptomTextView = new TextView(mainActivity);
                symptomTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                symptomTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                symptomTextView.setText(virusCauseModel.getCauseContent());
                horizontalLinearLayout.addView(symptomTextView);

            }
        }
    }

    private void setTakeQuizButtonOnClickListener() {
        this.quizButton.setOnClickListener(quizButtonView -> {
            Intent intent = new Intent(mainActivity, QuizActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            intent.putExtras(bundle);
            // animation
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mainActivity, virusPictureImageView, ViewCompat.getTransitionName(virusPictureImageView));
            mainActivity.startActivity(intent, options.toBundle());
        });
    }

    private void setVirusGalleryFABOnClickListener() {
        this.virusGalleryFloatingActionLayout.setOnClickListener(fabView ->{
            Intent intent = new Intent(mainActivity, GalleryActivity.class);
            mainActivity.startActivity(intent);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        // hide quiz button
        this.quizButton.setVisibility(View.GONE);

        this.mainActivity.setLearnButton(false);
    }
}
