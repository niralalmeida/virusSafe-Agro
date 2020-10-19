package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;

public class TomatoTipFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    // views
    private ImageButton card1ImageButton;
    private ImageButton card2ImageButton;
    private ImageButton card3ImageButton;
    private ImageButton card4ImageButton;
    private ImageButton card5ImageButton;
    private ImageButton card6ImageButton;
    private ImageButton card7ImageButton;
    private ImageButton card8ImageButton;
    private View line1View;
    private View line2View;
    private View line3View;
    private View line4View;
    private View line5View;
    private View line6View;
    private View line7View;
    private View line8View;
    private RelativeLayout card1hideRelativeLayout;
    private RelativeLayout card2hideRelativeLayout;
    private RelativeLayout card3hideRelativeLayout;
    private RelativeLayout card4hideRelativeLayout;
    private RelativeLayout card5hideRelativeLayout;
    private RelativeLayout card6hideRelativeLayout;
    private RelativeLayout card7hideRelativeLayout;
    private RelativeLayout card8hideRelativeLayout;

    // tools
    private boolean isCard1Open;
    private boolean isCard2Open;
    private boolean isCard3Open;
    private boolean isCard4Open;
    private boolean isCard5Open;
    private boolean isCard6Open;
    private boolean isCard7Open;
    private boolean isCard8Open;

    public TomatoTipFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_tomato_tip, container, false);

        // get main activity
        this.mainActivity = (MainActivity) getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_tomato_tip);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity) requireActivity());
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_TOMATO_GROWING_TIP);

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
        this.card1ImageButton = view.findViewById(R.id.imgbtn_card1_tomato_tip);
        this.card2ImageButton = view.findViewById(R.id.imgbtn_card2_tomato_tip);
        this.card3ImageButton = view.findViewById(R.id.imgbtn_card3_tomato_tip);
        this.card4ImageButton = view.findViewById(R.id.imgbtn_card4_tomato_tip);
        this.card5ImageButton = view.findViewById(R.id.imgbtn_card5_tomato_tip);
        this.card6ImageButton = view.findViewById(R.id.imgbtn_card6_tomato_tip);
        this.card7ImageButton = view.findViewById(R.id.imgbtn_card7_tomato_tip);
        this.card8ImageButton = view.findViewById(R.id.imgbtn_card8_tomato_tip);
        this.line1View = view.findViewById(R.id.v_horizontal_line1_tomato_tip);
        this.line2View = view.findViewById(R.id.v_horizontal_line2_tomato_tip);
        this.line3View = view.findViewById(R.id.v_horizontal_line3_tomato_tip);
        this.line4View = view.findViewById(R.id.v_horizontal_line4_tomato_tip);
        this.line5View = view.findViewById(R.id.v_horizontal_line5_tomato_tip);
        this.line6View = view.findViewById(R.id.v_horizontal_line6_tomato_tip);
        this.line7View = view.findViewById(R.id.v_horizontal_line7_tomato_tip);
        this.line8View = view.findViewById(R.id.v_horizontal_line8_tomato_tip);
        this.card1hideRelativeLayout = view.findViewById(R.id.rl_card1_hide_tomato_tip);
        this.card2hideRelativeLayout = view.findViewById(R.id.rl_card2_hide_tomato_tip);
        this.card3hideRelativeLayout = view.findViewById(R.id.rl_card3_hide_tomato_tip);
        this.card4hideRelativeLayout = view.findViewById(R.id.rl_card4_hide_tomato_tip);
        this.card5hideRelativeLayout = view.findViewById(R.id.rl_card5_hide_tomato_tip);
        this.card6hideRelativeLayout = view.findViewById(R.id.rl_card6_hide_tomato_tip);
        this.card7hideRelativeLayout = view.findViewById(R.id.rl_card7_hide_tomato_tip);
        this.card8hideRelativeLayout = view.findViewById(R.id.rl_card8_hide_tomato_tip);
    }

    private void setCardButtonOnClickListeners() {
        // card 1
        this.card1ImageButton.setOnClickListener(card1ImageButtonView -> {
            if (isCard1Open) {
                isCard1Open = false;
                line1View.setVisibility(View.GONE);
                card1hideRelativeLayout.setVisibility(View.GONE);
                card1ImageButton.setImageResource(R.drawable.arrow_down);
            } else {
                isCard1Open = true;
                line1View.setVisibility(View.VISIBLE);
                card1hideRelativeLayout.setVisibility(View.VISIBLE);
                card1ImageButton.setImageResource(R.drawable.arrow_up);
            }
        });

        // card 2
        this.card2ImageButton.setOnClickListener(card2ImageButtonView -> {
            if (isCard2Open) {
                isCard2Open = false;
                line2View.setVisibility(View.GONE);
                card2hideRelativeLayout.setVisibility(View.GONE);
                card2ImageButton.setImageResource(R.drawable.arrow_down);
            } else {
                isCard2Open = true;
                line2View.setVisibility(View.VISIBLE);
                card2hideRelativeLayout.setVisibility(View.VISIBLE);
                card2ImageButton.setImageResource(R.drawable.arrow_up);
            }
        });

        // card 3
        this.card3ImageButton.setOnClickListener(card3ImageButtonView -> {
            if (isCard3Open) {
                isCard3Open = false;
                line3View.setVisibility(View.GONE);
                card3hideRelativeLayout.setVisibility(View.GONE);
                card3ImageButton.setImageResource(R.drawable.arrow_down);
            } else {
                isCard3Open = true;
                line3View.setVisibility(View.VISIBLE);
                card3hideRelativeLayout.setVisibility(View.VISIBLE);
                card3ImageButton.setImageResource(R.drawable.arrow_up);
            }
        });

        // card 4
        this.card4ImageButton.setOnClickListener(card4ImageButtonView -> {
            if (isCard4Open) {
                isCard4Open = false;
                line4View.setVisibility(View.GONE);
                card4hideRelativeLayout.setVisibility(View.GONE);
                card4ImageButton.setImageResource(R.drawable.arrow_down);
            } else {
                isCard4Open = true;
                line4View.setVisibility(View.VISIBLE);
                card4hideRelativeLayout.setVisibility(View.VISIBLE);
                card4ImageButton.setImageResource(R.drawable.arrow_up);
            }
        });

        // card 5
        this.card5ImageButton.setOnClickListener(card5ImageButtonView -> {
            if (isCard5Open) {
                isCard5Open = false;
                line5View.setVisibility(View.GONE);
                card5hideRelativeLayout.setVisibility(View.GONE);
                card5ImageButton.setImageResource(R.drawable.arrow_down);
            } else {
                isCard5Open = true;
                line5View.setVisibility(View.VISIBLE);
                card5hideRelativeLayout.setVisibility(View.VISIBLE);
                card5ImageButton.setImageResource(R.drawable.arrow_up);
            }
        });

        // card 6
        this.card6ImageButton.setOnClickListener(card6ImageButtonView -> {
            if (isCard6Open) {
                isCard6Open = false;
                line6View.setVisibility(View.GONE);
                card6hideRelativeLayout.setVisibility(View.GONE);
                card6ImageButton.setImageResource(R.drawable.arrow_down);
            } else {
                isCard6Open = true;
                line6View.setVisibility(View.VISIBLE);
                card6hideRelativeLayout.setVisibility(View.VISIBLE);
                card6ImageButton.setImageResource(R.drawable.arrow_up);
            }
        });

        // card 7
        this.card7ImageButton.setOnClickListener(card7ImageButtonView -> {
            if (isCard7Open) {
                isCard7Open = false;
                line7View.setVisibility(View.GONE);
                card7hideRelativeLayout.setVisibility(View.GONE);
                card7ImageButton.setImageResource(R.drawable.arrow_down);
            } else {
                isCard7Open = true;
                line7View.setVisibility(View.VISIBLE);
                card7hideRelativeLayout.setVisibility(View.VISIBLE);
                card7ImageButton.setImageResource(R.drawable.arrow_up);
            }
        });

        // card 8
        this.card8ImageButton.setOnClickListener(card8ImageButtonView -> {
            if (isCard8Open) {
                isCard8Open = false;
                line8View.setVisibility(View.GONE);
                card8hideRelativeLayout.setVisibility(View.GONE);
                card8ImageButton.setImageResource(R.drawable.arrow_down);
            } else {
                isCard8Open = true;
                line8View.setVisibility(View.VISIBLE);
                card8hideRelativeLayout.setVisibility(View.VISIBLE);
                card8ImageButton.setImageResource(R.drawable.arrow_up);
            }
        });
    }
}
