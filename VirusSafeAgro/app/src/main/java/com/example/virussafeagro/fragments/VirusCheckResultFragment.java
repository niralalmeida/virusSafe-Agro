package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;

import java.util.Objects;

public class VirusCheckResultFragment extends Fragment {
    private View view;

    private String resultCheckFeedback;

    private LinearLayout imageCheckErrorFeedbackLinearLayout;
    private LinearLayout imageCheckHealthyFeedbackLinearLayout;
    private LinearLayout imageCheckIllFeedbackLinearLayout;
    private TextView imageCheckIllFeedbackTextView;


    public VirusCheckResultFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_check_result, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Virus Check Result");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.resultCheckFeedback = bundle.getString("resultCheckFeedback");

        // initialize views
        this.initializeViews();

        // control the resultCheckFeedback display
        this.controlResultCheckFeedback();
    }

    private void initializeViews() {
        this.imageCheckErrorFeedbackLinearLayout = view.findViewById(R.id.ll_error_feedback_image_check);
        this.imageCheckHealthyFeedbackLinearLayout = view.findViewById(R.id.ll_healthy_feedback_image_check);
        this.imageCheckIllFeedbackLinearLayout = view.findViewById(R.id.ll_ill_feedback_image_check);
        this.imageCheckIllFeedbackTextView = view.findViewById(R.id.tv_ill_feedback_image_check);
    }

    private void controlResultCheckFeedback() {
        if (resultCheckFeedback.equals("json error")){
            this.imageCheckErrorFeedbackLinearLayout.setVisibility(View.VISIBLE);
        } else if (resultCheckFeedback.equals("healthy")){
            this.imageCheckHealthyFeedbackLinearLayout.setVisibility(View.VISIBLE);
        } else {
            this.imageCheckIllFeedbackLinearLayout.setVisibility(View.VISIBLE);
            this.imageCheckIllFeedbackTextView.setText(this.resultCheckFeedback);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        this.imageCheckHealthyFeedbackLinearLayout.setVisibility(View.INVISIBLE);
        this.imageCheckIllFeedbackLinearLayout.setVisibility(View.INVISIBLE);
        this.imageCheckIllFeedbackTextView.setText("");
    }
}
