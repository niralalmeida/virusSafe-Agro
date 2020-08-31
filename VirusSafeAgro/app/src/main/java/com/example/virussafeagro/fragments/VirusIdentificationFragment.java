package com.example.virussafeagro.fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.R;
import com.example.virussafeagro.viewModel.VirusIdentificationViewModel;

import java.io.FileNotFoundException;

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
    private LinearLayout imageIdentificationFeedbackLinearLayout;
    private TextView imageIdentificationFeedbackTextView;

    private final int RESULT_OK = -1;

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

        // set selectImageButton on click listener
        this.setSelectImageButtonOnClickListener();
        // set uploadImageButton on click listener
        this.setUploadImageButtonOnClickListener();
    }

    private void initializeViews() {
        this.selectImageButton = view.findViewById(R.id.btn_select_image);
        this.uploadImageImageView = view.findViewById(R.id.img_upload_identification);
        this.uploadImageButton = view.findViewById(R.id.btn_upload_image);
        this.imageIdentificationFeedbackLinearLayout = view.findViewById(R.id.ll_feedback_image_identification);
        this.imageIdentificationFeedbackTextView = view.findViewById(R.id.tv_feedback_image_identification);
    }

    private void initializeVirusIdentificationViewModel() {
        this.virusIdentificationViewModel = new ViewModelProvider(requireActivity()).get(VirusIdentificationViewModel.class);
        this.virusIdentificationViewModel.initiateTheContext(requireActivity());
    }

    private void setSelectImageButtonOnClickListener() {
        this.selectImageButton.setOnClickListener(view -> {
            imageIdentificationFeedbackLinearLayout.setVisibility(View.INVISIBLE);
            imageIdentificationFeedbackTextView.setText("");
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = requireActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                this.uploadImageImageView.setImageBitmap(bitmap);
                this.uploadImageButton.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }else{
            Log.i("VirusIdentification", "operation error");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUploadImageButtonOnClickListener() {
        this.uploadImageButton.setOnClickListener(view -> {
            // upload Tomato Image
            this.uploadTomatoImage();
            // observe identificationFeedback live data
            this.observeIdentificationFeedbackLD();
        });
    }

    private void uploadTomatoImage() {
        Bitmap uploadImageBitmap = ((BitmapDrawable) this.uploadImageImageView.getDrawable()).getBitmap();
        this.virusIdentificationViewModel.processUploadingTomatoImage(uploadImageBitmap);
    }

    private void observeIdentificationFeedbackLD() {
        this.virusIdentificationViewModel.getIdentificationFeedbackLD().observe(getViewLifecycleOwner(), resultIdentificationFeedback -> {
            if (!resultIdentificationFeedback.isEmpty()){
                imageIdentificationFeedbackLinearLayout.setVisibility(View.VISIBLE);
                if (resultIdentificationFeedback.equals("error")){
                    Toast.makeText(requireActivity(), "Your image should include tomato leaf!! Please select image again!!!", Toast.LENGTH_SHORT).show();
                } else {
                    imageIdentificationFeedbackTextView.setText(resultIdentificationFeedback);
                }
            }
        });
    }

}
