package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.example.virussafeagro.viewModel.VirusCheckResultViewModel;

import java.util.ArrayList;

public class VirusCheckResultFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private SharedPreferenceProcess spp;
    private VirusCheckResultViewModel virusCheckResultViewModel;

    private String resultCheckFeedback;
    private VirusModel resultVirusModel;

    private RelativeLayout allViewRelativeLayout;
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

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_virus_check_result);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

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

        // set check button
//        this.mainActivity.setVirusCheckButton(true);

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.resultCheckFeedback = bundle.getString("resultCheckFeedback");

        // get upload image
        GetCurrentVirusCheckImageAsyncTask getCurrentVirusCheckImageAsyncTask = new GetCurrentVirusCheckImageAsyncTask();
        getCurrentVirusCheckImageAsyncTask.execute();
    }

    private class GetCurrentVirusCheckImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            // get the image data from SharedPreference and set ImageView
            Bitmap uploadedImageBitmap = spp.getCurrentVirusCheckImage();
            return uploadedImageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap uploadedImageBitmap) {
            uploadedImageImageView.setImageBitmap(uploadedImageBitmap);

            // show all views
            if (getFragmentManager().findFragmentByTag(AppResources.FRAGMENT_TAG_VIRUS_CHECK) == null) {
                showAllViews();
            }

            // control the resultCheckFeedback display
            controlResultCheckFeedback();

            // set VirusDetailsButton On Click Listener
            setVirusDetailsButtonOnClickListener();
            // observe Virus List live data
            observeVirusListLD();
        }
    }

    private void initializeViews() {
        this.allViewRelativeLayout = view.findViewById(R.id.rl_all_view_virus_check_result);
        this.uploadedImageImageView = view.findViewById(R.id.img_upload_check_result);
        this.imageCheckErrorFeedbackLinearLayout = view.findViewById(R.id.ll_error_feedback_image_check);
        this.imageCheckHealthyFeedbackLinearLayout = view.findViewById(R.id.ll_healthy_feedback_image_check);
        this.imageCheckIllFeedbackLinearLayout = view.findViewById(R.id.ll_ill_feedback_image_check);
        this.imageCheckIllFeedbackTextView = view.findViewById(R.id.tv_ill_feedback_image_check);
        this.virusDetailsButton = view.findViewById(R.id.btn_virus_details_check_result);
        this.buttonProcessBarLinearLayout = view.findViewById(R.id.ll_btn_progress_bar_check_result);
    }

    private void showAllViews() {
        MyAnimationBox.runFadeInAnimation(allViewRelativeLayout, 1000);
    }

    private void initializeSharedPreference() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireActivity());
    }

    private void initializeVirusCheckResultViewModel() {
        this.virusCheckResultViewModel = new ViewModelProvider(requireActivity()).get(VirusCheckResultViewModel.class);
        this.virusCheckResultViewModel.initiateSharedPreferenceProcess(requireContext());
    }

    private void controlResultCheckFeedback() {
        if (resultCheckFeedback.equals("json error")){
            this.imageCheckErrorFeedbackLinearLayout.setVisibility(View.VISIBLE);
        } else if (resultCheckFeedback.equals("healthy")){
            this.imageCheckHealthyFeedbackLinearLayout.setVisibility(View.VISIBLE);
        } else {
            this.imageCheckIllFeedbackLinearLayout.setVisibility(View.VISIBLE);
            // convert the virus result
            String virusNameString = DataConverter.checkResultVirusRawNameToDisplayFormat(this.resultCheckFeedback);
            this.imageCheckIllFeedbackTextView.setText(virusNameString);
            this.isInfected = true;
            this.buttonProcessBarLinearLayout.setVisibility(View.VISIBLE);

//            if (spp.getVirusModelListFromSP().get(0).getVirusFullName().isEmpty()) {
                // find virus info list in new Thread
            this.findVirusInfoListFromDB();
//            } else {
//                // set virus detail button visible
//                this.virusDetailsButton.setVisibility(View.VISIBLE);
//                this.buttonProcessBarLinearLayout.setVisibility(View.INVISIBLE);

                // get result virus id
//                this.resultVirusModel = getResultVirusModelFromSP(this.resultCheckFeedback);
//            }
        }
    }

    private void findVirusInfoListFromDB() {
        this.virusCheckResultViewModel.processFindingVirusList();
    }

    private void observeVirusListLD() {
        this.virusCheckResultViewModel.getVirusListLD().observe(getViewLifecycleOwner(), resultVirusList -> {
            if (resultVirusList != null && resultVirusList.size() == 9 && isInfected ){
                // set virus detail button visible
//                this.virusDetailsButton.setVisibility(View.VISIBLE);
                this.buttonProcessBarLinearLayout.setVisibility(View.INVISIBLE);
                // get result virus id
//                if ((!this.resultCheckFeedback.equals("json error")) && (!this.resultCheckFeedback.equals("healthy"))){
//                    this.resultVirusModel = getResultVirusModelFromSP(this.resultCheckFeedback);
//                }
            }
        });
    }

    private void setVirusDetailsButtonOnClickListener() {
        this.virusDetailsButton.setOnClickListener(view -> {
            if (resultVirusModel != null){
                Bundle bundle = new Bundle();
                // store the virus model in the bundle
                bundle.putParcelable("currentVirusModel", resultVirusModel);
                // store the message for "prevention" in the bundle
                bundle.putString("prevention", "yes");
            } else {
                String errorMessage = "The result virus is not in the virus list!";
                Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private VirusModel getResultVirusModelFromSP(String resultVirusRawFullName){
//        // parse the name to standard: eg. TARGET SPOT
//        String processedResultName = DataConverter.checkResultVirusRawNameToUpperCaseWithSpace(resultVirusRawFullName);
//
//        // get result virus id
//        int virusId = AppResources.getVirusIdByVirusFullName(processedResultName);
//
//
//        // get the virus list
//        List<VirusModel> virusModelList = spp.getVirusModelListFromSP();
//        return (virusId == 0) ? null : virusModelList.get(virusId - 1);
//    }

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
