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
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.example.virussafeagro.viewModel.VirusCheckResultViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirusCheckResultFragment extends Fragment {
    private View view;

    private SharedPreferenceProcess spp;
    private VirusCheckResultViewModel virusCheckResultViewModel;

    private String resultCheckFeedback;
    private VirusModel resultVirusModel;

    private ImageView uploadedImageImageView;
    private LinearLayout imageCheckErrorFeedbackLinearLayout;
    private LinearLayout imageCheckHealthyFeedbackLinearLayout;
    private LinearLayout imageCheckIllFeedbackLinearLayout;
    private TextView imageCheckIllFeedbackTextView;
    private Button virusDetailsButton;
    private LinearLayout buttonProcessBarLinearLayout;

    private boolean isInfected = false;


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
        // initialize view model
        this.initializeVirusCheckResultViewModel();

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
        // observe Virus List live data
        this.observeVirusListLD();
    }

    private void initializeViews() {
        this.uploadedImageImageView = view.findViewById(R.id.img_upload_check_result);
        this.imageCheckErrorFeedbackLinearLayout = view.findViewById(R.id.ll_error_feedback_image_check);
        this.imageCheckHealthyFeedbackLinearLayout = view.findViewById(R.id.ll_healthy_feedback_image_check);
        this.imageCheckIllFeedbackLinearLayout = view.findViewById(R.id.ll_ill_feedback_image_check);
        this.imageCheckIllFeedbackTextView = view.findViewById(R.id.tv_ill_feedback_image_check);
        this.virusDetailsButton = view.findViewById(R.id.btn_virus_details_check_result);
        this.buttonProcessBarLinearLayout = view.findViewById(R.id.ll_btn_progress_bar_check_result);
    }

    private void initializeSharedPreference() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireActivity());
    }

    private void initializeVirusCheckResultViewModel() {
        this.virusCheckResultViewModel = new ViewModelProvider(requireActivity()).get(VirusCheckResultViewModel.class);
        this.virusCheckResultViewModel.initiateSharedPreferenceProcess(requireContext());
    }

    private void findVirusInfoListFromDB() {
        this.virusCheckResultViewModel.processFindingVirusList();
    }

    private void controlResultCheckFeedback() {
        if (resultCheckFeedback.equals("json error")){
            this.imageCheckErrorFeedbackLinearLayout.setVisibility(View.VISIBLE);
        } else if (resultCheckFeedback.equals("healthy")){
            this.imageCheckHealthyFeedbackLinearLayout.setVisibility(View.VISIBLE);
        } else {
            this.imageCheckIllFeedbackLinearLayout.setVisibility(View.VISIBLE);
            this.imageCheckIllFeedbackTextView.setText(this.resultCheckFeedback);
            this.isInfected = true;
            this.buttonProcessBarLinearLayout.setVisibility(View.VISIBLE);

            if (spp.getVirusModelListFromSP().get(0).getVirusFullName().isEmpty()) {
                // find virus info list in new Thread
                this.findVirusInfoListFromDB();
            } else {
                // set virus detail button visible
                this.virusDetailsButton.setVisibility(View.VISIBLE);
                this.buttonProcessBarLinearLayout.setVisibility(View.INVISIBLE);
                // get result virus id
                this.resultVirusModel = getResultVirusModelFromSP(this.resultCheckFeedback);
            }
        }
    }

    private void observeVirusListLD() {
        this.virusCheckResultViewModel.getVirusListLD().observe(getViewLifecycleOwner(), resultVirusList -> {
            if (resultVirusList != null && resultVirusList.size() == 9 && isInfected ){
                // set virus detail button visible
                this.virusDetailsButton.setVisibility(View.VISIBLE);
                this.buttonProcessBarLinearLayout.setVisibility(View.INVISIBLE);
                // get result virus id
                if ((!this.resultCheckFeedback.equals("json error")) && (!this.resultCheckFeedback.equals("healthy"))){
                    this.resultVirusModel = getResultVirusModelFromSP(this.resultCheckFeedback);
                }
            }
        });
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
        // parse the name to standard: eg. TARGET SPOT
        String processedResultName = DataConverter.checkResultVirusRawNameToUpperCaseWithSpace(resultVirusRawFullName);
        // get result virus id
        int virusId = AppResources.getVirusIdByVirusFullName(processedResultName);
        // get the virus list
        List<VirusModel> virusModelList = spp.getVirusModelListFromSP();
        return virusModelList.get(virusId - 1);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.virusCheckResultViewModel.getVirusListLD().removeObservers(requireActivity());
        this.virusCheckResultViewModel.setVirusListLD(new ArrayList<>());
    }

    @Override
    public void onStop() {
        super.onStop();

        this.imageCheckHealthyFeedbackLinearLayout.setVisibility(View.INVISIBLE);
        this.imageCheckIllFeedbackLinearLayout.setVisibility(View.INVISIBLE);
        this.imageCheckIllFeedbackTextView.setText("");
    }
}
