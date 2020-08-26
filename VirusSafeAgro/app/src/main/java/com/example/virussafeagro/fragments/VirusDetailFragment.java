package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.R;
import com.example.virussafeagro.entities.Virus;

public class VirusDetailFragment extends Fragment {
    private View view;

    private Virus currentVirus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_detail, container, false);

        // get passed bundle and the Virus within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirus = bundle.getParcelable("currentVirus");

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
