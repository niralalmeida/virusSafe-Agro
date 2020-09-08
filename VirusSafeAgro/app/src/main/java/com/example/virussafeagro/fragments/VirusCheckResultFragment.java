package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;

import java.util.Objects;

public class VirusCheckResultFragment extends Fragment {
    private View view;

    private String resultCheckFeedback;
    private SharedPreferenceProcess spp;
    private VirusModel resultVirusModel;

    private ImageView uploadedImageImageView;
    private LinearLayout imageCheckErrorFeedbackLinearLayout;
    private LinearLayout imageCheckHealthyFeedbackLinearLayout;
    private LinearLayout imageCheckIllFeedbackLinearLayout;
    private TextView imageCheckIllFeedbackTextView;
    private Button virusDetailsButton;


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

        // initialize views
        this.initializeViews();

        // initialize SharedPreference
        this.initializeSharedPreference();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.resultCheckFeedback = bundle.getString("resultCheckFeedback");

        // get the image data from SharedPreference and set ImageView
        Bitmap uploadedImageBitmap = spp.getCurrentVirusCheckImage();
        this.uploadedImageImageView.setImageBitmap(uploadedImageBitmap);

        // control the resultCheckFeedback display
        this.controlResultCheckFeedback();

        // set VirusDetailsButton On Click Listener
        this.setVirusDetailsButtonOnClickListener();
    }

    private void initializeViews() {
        this.uploadedImageImageView = view.findViewById(R.id.img_upload_check_result);
        this.imageCheckErrorFeedbackLinearLayout = view.findViewById(R.id.ll_error_feedback_image_check);
        this.imageCheckHealthyFeedbackLinearLayout = view.findViewById(R.id.ll_healthy_feedback_image_check);
        this.imageCheckIllFeedbackLinearLayout = view.findViewById(R.id.ll_ill_feedback_image_check);
        this.imageCheckIllFeedbackTextView = view.findViewById(R.id.tv_ill_feedback_image_check);
        this.virusDetailsButton = view.findViewById(R.id.btn_virus_details_check_result);
    }

    private void initializeSharedPreference() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireActivity());
    }

    private void controlResultCheckFeedback() {
        if (resultCheckFeedback.equals("json error")){
            this.imageCheckErrorFeedbackLinearLayout.setVisibility(View.VISIBLE);
        } else if (resultCheckFeedback.equals("healthy")){
            this.imageCheckHealthyFeedbackLinearLayout.setVisibility(View.VISIBLE);
        } else {
            this.imageCheckIllFeedbackLinearLayout.setVisibility(View.VISIBLE);
            this.imageCheckIllFeedbackTextView.setText(this.resultCheckFeedback);
            // set virus detail button visible
            this.virusDetailsButton.setVisibility(View.VISIBLE);
            // get result virus id
            this.resultVirusModel = getResultVirusModelFromSP(this.resultCheckFeedback);
        }
    }

    private void setVirusDetailsButtonOnClickListener() {
        this.virusDetailsButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("currentVirusModel", resultVirusModel);
            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
            virusDetailFragment.setArguments(bundle);
            FragmentOperator.replaceFragment(requireActivity(), virusDetailFragment);
        });
    }

    private VirusModel getResultVirusModelFromSP(String resultVirusRawFullName){
        VirusModel virusModel = new VirusModel();
        // change the name : eg. TARGET SPOT
        String processedResultName = DataConverter.checkResultVirusRawNameToUpperCaseWithSpace(resultVirusRawFullName);
        int virusId = AppResources.getVirusIdByVirusFullName(processedResultName);

        virusModel.setVirusId(virusId);
        virusModel.setVirusFullName(spp.getSPVirusFullName(virusId));
        virusModel.setVirusAbbreviation(spp.getSPVirusAbbreviation(virusId));
        virusModel.setVirusDescription(spp.getSPVirusDescription(virusId));
        virusModel.setSymptoms(spp.getSPVirusSymptoms(virusId));
        virusModel.setCauses(spp.getSPVirusCauses(virusId));
        virusModel.setSpread(spp.getSPVirusSpread(virusId));
        virusModel.setPrevention(spp.getSPVirusPrevention(virusId));
        virusModel.setDistribution(spp.getSPDistribution(virusId));
        return virusModel;
    }

    @Override
    public void onStop() {
        super.onStop();

        this.imageCheckHealthyFeedbackLinearLayout.setVisibility(View.INVISIBLE);
        this.imageCheckIllFeedbackLinearLayout.setVisibility(View.INVISIBLE);
        this.imageCheckIllFeedbackTextView.setText("");
    }
}
