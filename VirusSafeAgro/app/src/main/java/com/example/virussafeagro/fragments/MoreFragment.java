package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;

import java.util.Objects;

public class MoreFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private LinearLayout aboutAppLinearLayout;
    private LinearLayout disclaimerLinearLayout;
    private LinearLayout contactUsLinearLayout;

    public MoreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_more, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_more);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set more button
        this.mainActivity.setMoreButton(true);
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_MORE);

        // initialize Views
        this.initializeViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // move the calculator and more to right
        this.mainActivity.moveTipAndMoreToRight(getTag(), 200);

        // set all tiles On Click Listener
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.aboutAppLinearLayout = view.findViewById(R.id.ll_about_app_more);
        this.disclaimerLinearLayout = view.findViewById(R.id.ll_disclaimer_more);
        this.contactUsLinearLayout = view.findViewById(R.id.ll_contact_us_more);
    }

    private void allTilesOnClickListener() {
        setAboutAppLinearLayoutOnClickListener();
        setDisclaimerLinearLayoutOnClickListener();
        setContactUsLinearLayoutOnClickListener();
    }

    private void setAboutAppLinearLayoutOnClickListener() {
        this.aboutAppLinearLayout.setOnClickListener(view -> {
            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new AboutAppFragment(), AppResources.FRAGMENT_TAG_ABOUT_APP);
        });
    }

    private void setDisclaimerLinearLayoutOnClickListener() {
        this.disclaimerLinearLayout.setOnClickListener(view -> {
            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new DisclaimerFragment(), AppResources.FRAGMENT_TAG_DISCLAIMER);
        });
    }

    private void setContactUsLinearLayoutOnClickListener() {
        this.contactUsLinearLayout.setOnClickListener(view -> {
            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new ContactUsFragment(), AppResources.FRAGMENT_TAG_CONTACT_US);
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        // set more button
        mainActivity.setMoreButton(false);
        // set default title and remove back button
        this.mainActivity.getTitleTextView().setText(R.string.app_name);
        Objects.requireNonNull(mainActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        Objects.requireNonNull(mainActivity.getSupportActionBar()).setHomeButtonEnabled(false);
    }
}
