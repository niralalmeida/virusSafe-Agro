package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;

public class TimingOfCauseFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    // views
    private MotionLayout containerMotionLayout;
    private ImageButton card1ImageButton;
    private ImageButton card2ImageButton;
    private View line1View;
    private View line2View;
    private RelativeLayout card1hideRelativeLayout;
    private RelativeLayout card2hideRelativeLayout;

    // tools
    private boolean isCard1Open;
    private boolean isCard2Open;

    public TimingOfCauseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_timing_of_cause, container, false);

        // get main activity
        this.mainActivity = (MainActivity) getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_timing_of_cause);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity) requireActivity());

        // initialize Views
        this.initializeViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show all cards
        this.setCardButtonOnClickListeners();
    }

    private void initializeViews() {
        this.containerMotionLayout = view.findViewById(R.id.ml_container_timing);
        this.card1ImageButton = view.findViewById(R.id.imgbtn_card1_timing);
        this.card2ImageButton = view.findViewById(R.id.imgbtn_card2_timing);
        this.line1View = view.findViewById(R.id.v_horizontal_line1_timing);
        this.line2View = view.findViewById(R.id.v_horizontal_line2_timing);
        this.card1hideRelativeLayout = view.findViewById(R.id.rl_card1_hide_timing);
        this.card2hideRelativeLayout = view.findViewById(R.id.rl_card2_hide_timing);
    }

    private void setCardButtonOnClickListeners() {
        this.card1ImageButton.setOnClickListener(card1ImageButtonView -> {
            if (isCard1Open) {
                isCard1Open = false;
                line1View.setVisibility(View.GONE);
                card1hideRelativeLayout.setVisibility(View.GONE);
                card1ImageButton.setImageResource(R.drawable.arrow_down);
//                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_card, R.id.end_show_card, 500);
            } else {
                isCard1Open = true;
                line1View.setVisibility(View.VISIBLE);
                card1hideRelativeLayout.setVisibility(View.VISIBLE);
                card1ImageButton.setImageResource(R.drawable.arrow_up);
//                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_card, R.id.end_show_card, 500);
            }
        });

        this.card2ImageButton.setOnClickListener(card2ImageButtonView -> {
            if (isCard2Open) {
                isCard2Open = false;
                line2View.setVisibility(View.GONE);
                card2hideRelativeLayout.setVisibility(View.GONE);
                card2ImageButton.setImageResource(R.drawable.arrow_down);
//                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_card, R.id.end_show_card, 500);
            } else {
                isCard2Open = true;
                line2View.setVisibility(View.VISIBLE);
                card2hideRelativeLayout.setVisibility(View.VISIBLE);
                card2ImageButton.setImageResource(R.drawable.arrow_up);
//                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_card, R.id.end_show_card, 500);
            }
        });
    }

}
