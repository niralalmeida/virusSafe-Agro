package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;

import java.util.Objects;

public class AboutFragment extends Fragment {
    private View view;

    private LinearLayout subtitle1;
    private LinearLayout subtitle2;
    private LinearLayout subtitle3;
    private LinearLayout subtitle4;
    private ImageButton arrow1;
    private ImageButton arrow2;
    private ImageButton arrow3;
    private ImageButton arrow4;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;

    public AboutFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_about, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("About");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // set arrow button on click listener group
        this.arrowOnClickListeners();
    }

    private void initializeViews(){
        this.subtitle1 = view.findViewById(R.id.ll_subtitle1_about);
        this.subtitle2 = view.findViewById(R.id.ll_subtitle2_about);
        this.subtitle3 = view.findViewById(R.id.ll_subtitle3_about);
        this.subtitle4 = view.findViewById(R.id.ll_subtitle4_about);
        this.arrow1 = view.findViewById(R.id.btn_arrow1_about);
        this.arrow2 = view.findViewById(R.id.btn_arrow2_about);
        this.arrow3 = view.findViewById(R.id.btn_arrow3_about);
        this.arrow4 = view.findViewById(R.id.btn_arrow4_about);
        this.linearLayout1 = view.findViewById(R.id.ll_c1_app_description_about);
        this.linearLayout2 = view.findViewById(R.id.ll_c2_updates_about);
        this.linearLayout3 = view.findViewById(R.id.ll_c3_contact_about);
        this.linearLayout4 = view.findViewById(R.id.ll_c4_disclaimer_about);
    }

    private void arrowOnClickListeners() {
        this.setArrowOnClickListener1();
        this.setArrowOnClickListener2();
        this.setArrowOnClickListener3();
        this.setArrowOnClickListener4();
    }

    private void setArrowOnClickListener1() {
        this.subtitle1.setOnClickListener(v -> {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.linearLayout1.getLayoutParams();
            int linearLayout1Height = this.linearLayout1.getHeight();
            if (linearLayout1Height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                this.arrow1.setImageResource(R.drawable.btn_arrow_up_black);
            } else {
                params.height = 0;
                this.arrow1.setImageResource(R.drawable.btn_arrow_down_black);
            }
            this.linearLayout1.setLayoutParams(params);
        });
    }

    private void setArrowOnClickListener2() {
        this.subtitle2.setOnClickListener(v -> {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.linearLayout2.getLayoutParams();
            int linearLayout2Height = this.linearLayout2.getHeight();
            if (linearLayout2Height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                this.arrow2.setImageResource(R.drawable.btn_arrow_up_black);
            } else {
                params.height = 0;
                this.arrow2.setImageResource(R.drawable.btn_arrow_down_black);
            }
            this.linearLayout2.setLayoutParams(params);
        });
    }

    private void setArrowOnClickListener3() {
        this.subtitle3.setOnClickListener(v -> {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.linearLayout3.getLayoutParams();
            int linearLayout3Height = this.linearLayout3.getHeight();
            if (linearLayout3Height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                this.arrow3.setImageResource(R.drawable.btn_arrow_up_black);
            } else {
                params.height = 0;
                this.arrow3.setImageResource(R.drawable.btn_arrow_down_black);
            }
            this.linearLayout3.setLayoutParams(params);
        });
    }

    private void setArrowOnClickListener4() {
        this.subtitle4.setOnClickListener(v -> {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.linearLayout4.getLayoutParams();
            int linearLayout4Height = this.linearLayout4.getHeight();
            if (linearLayout4Height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                this.arrow4.setImageResource(R.drawable.btn_arrow_up_black);
            } else {
                params.height = 0;
                this.arrow4.setImageResource(R.drawable.btn_arrow_down_black);
            }
            this.linearLayout4.setLayoutParams(params);
        });
    }
}
