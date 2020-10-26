package com.example.virussafeagro.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.virussafeagro.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class DetectInstructionsFragment extends BottomSheetDialogFragment {
    private MainActivity mainActivity;
    private View view;

    public DetectInstructionsFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(Objects.requireNonNull(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
