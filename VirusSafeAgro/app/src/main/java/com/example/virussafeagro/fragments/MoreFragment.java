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
import com.example.virussafeagro.uitilities.FragmentOperator;

import java.util.Objects;

public class MoreFragment extends Fragment {
    private View view;

    private LinearLayout aboutLinearLayout;

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

        // set AboutLinearLayout On Click Listener
        this.setAboutLinearLayoutOnClickListener();
    }

    private void initializeViews() {
        this.aboutLinearLayout = view.findViewById(R.id.ll_about_more);
    }

    private void setAboutLinearLayoutOnClickListener() {
        this.aboutLinearLayout.setOnClickListener(view -> {
            FragmentOperator.replaceFragment(requireActivity(), new AboutFragment());
        });
    }

}
