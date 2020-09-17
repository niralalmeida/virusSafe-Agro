package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class MoreFragment extends Fragment {
    private View view;

    private LinearLayout allViewLinearLayout;
    private LinearLayout aboutAppLinearLayout;
    private LinearLayout updatesLinearLayout;
    private LinearLayout disclaimerLinearLayout;
    private LinearLayout contactUsLinearLayout;

    public MoreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_more, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("More");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize Views
        this.initializeViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show Home Views
        this.showHomeViews();

        // set all tiles On Click Listener
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_more);
        this.aboutAppLinearLayout = view.findViewById(R.id.ll_about_app_more);
        this.updatesLinearLayout = view.findViewById(R.id.ll_updates_more);
        this.disclaimerLinearLayout = view.findViewById(R.id.ll_disclaimer_more);
        this.contactUsLinearLayout = view.findViewById(R.id.ll_contact_us_more);
    }

    // show Home Views
    private void showHomeViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewLinearLayout, 1000);
    }

    private void allTilesOnClickListener() {
        setAboutAppLinearLayoutOnClickListener();
        setUpdatesLinearLayoutOnClickListener();
        setDisclaimerLinearLayoutOnClickListener();
        setContactUsLinearLayoutOnClickListener();
    }

    private void setAboutAppLinearLayoutOnClickListener() {
        this.aboutAppLinearLayout.setOnClickListener(view -> {
            FragmentOperator.replaceFragment(requireActivity(), new AboutAppFragment(), AppResources.FRAGMENT_TAG_ABOUT_APP);
        });
    }

    private void setUpdatesLinearLayoutOnClickListener() {
        this.updatesLinearLayout.setOnClickListener(view -> {
//            FragmentOperator.replaceFragment(requireActivity(), new AboutFragment(), AppResources.FRAGMENT_TAG_ABOUT);
        });
    }

    private void setDisclaimerLinearLayoutOnClickListener() {
        this.disclaimerLinearLayout.setOnClickListener(view -> {
//            FragmentOperator.replaceFragment(requireActivity(), new AboutFragment(), AppResources.FRAGMENT_TAG_ABOUT);
        });
    }

    private void setContactUsLinearLayoutOnClickListener() {
        this.contactUsLinearLayout.setOnClickListener(view -> {
//            FragmentOperator.replaceFragment(requireActivity(), new AboutFragment(), AppResources.FRAGMENT_TAG_ABOUT);
        });
    }

}
