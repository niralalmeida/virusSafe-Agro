package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.R;

/**
 * Fragment for uploading tomato pictures to identify whether they are infected by some viruses
 * @author Haoyu Yang
 */
public class VirusIdentificationFragment extends Fragment {
    private View view;

    public VirusIdentificationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_identification, container, false);
        return this.view;
    }

}
