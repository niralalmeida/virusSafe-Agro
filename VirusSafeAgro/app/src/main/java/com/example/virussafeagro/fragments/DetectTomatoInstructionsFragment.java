package com.example.virussafeagro.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.virussafeagro.TomatoCameraActivity;
import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class DetectTomatoInstructionsFragment extends BottomSheetDialogFragment {
    private TomatoCameraActivity cameraActivity;
    private View view;

    // bottom sheet tools
    private BottomSheetBehavior<FrameLayout> behavior;

    public DetectTomatoInstructionsFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(Objects.requireNonNull(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();

        // get dialog object
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        // set background as transparent
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackground(new ColorDrawable(Color.TRANSPARENT));
        //get dialog's root layout
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            //get root layout's LayoutParams object
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getPeekHeight();
            //modify the max height of the window, do not allow to swipe up (default is allow)
            bottomSheet.setLayoutParams(layoutParams);
            // set the height of the bottom sheet
            behavior = BottomSheetBehavior.from(bottomSheet);
            //peekHeight is the windows's max height
            behavior.setPeekHeight(getResources().getDisplayMetrics().heightPixels);
            // the initial state is an open state
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    protected int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;
        return peekHeight - peekHeight / 8;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_tomato_detect_instruction, container, false);

        // get camera activity
        this.cameraActivity = (TomatoCameraActivity) getActivity();

        return this.view;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
