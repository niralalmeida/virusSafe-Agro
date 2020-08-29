package com.example.virussafeagro.fragments;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.R;
import com.example.virussafeagro.viewModel.VirusIdentificationViewModel;

/**
 * Fragment for uploading tomato pictures to identify whether they are infected by some viruses
 * @author Haoyu Yang
 */
public class VirusIdentificationFragment extends Fragment {
    private View view;

    private VirusIdentificationViewModel virusIdentificationViewModel;

    private Button selectImageButton;
    private ImageView uploadImageImageView;
    private Button uploadImageButton;

    public VirusIdentificationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_identification, container, false);
        this.initializeViews();
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // initialize VirusIdentificationViewModel
        this.initializeVirusIdentificationViewModel();


    }

    private void initializeViews() {
        this.selectImageButton = view.findViewById(R.id.btn_select_image);
        this.uploadImageImageView = view.findViewById(R.id.img_upload_identification);
        this.uploadImageButton = view.findViewById(R.id.btn_upload_image);
    }

    private void initializeVirusIdentificationViewModel() {
        this.virusIdentificationViewModel = new ViewModelProvider(requireActivity()).get(VirusIdentificationViewModel.class);
        this.virusIdentificationViewModel.initiateTheContext(requireActivity());
    }

}
