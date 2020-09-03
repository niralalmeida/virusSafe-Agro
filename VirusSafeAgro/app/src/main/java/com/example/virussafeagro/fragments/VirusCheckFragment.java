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

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.viewModel.VirusCheckViewModel;

import java.io.FileNotFoundException;

/**
 * Fragment for uploading tomato pictures to identify whether they are infected by some viruses
 * @author Haoyu Yang
 */
public class VirusCheckFragment extends Fragment {
    private View view;

    private VirusCheckViewModel virusCheckViewModel;

    private Button selectImageButton;
    private ImageView uploadImageImageView;
    private Button uploadImageButton;
    private LinearLayout imageCheckFeedbackLinearLayout;
    private TextView imageCheckFeedbackTextView;

    private final int RESULT_OK = -1;

    public VirusCheckFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_check, container, false);

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // initialize VirusCheckViewModel
        this.initializeVirusCheckViewModel();

        // set selectImageButton on click listener
        this.setSelectImageButtonOnClickListener();
        // set uploadImageButton on click listener
        this.setUploadImageButtonOnClickListener();
    }

    private void initializeViews() {
        this.selectImageButton = view.findViewById(R.id.btn_select_image);
        this.uploadImageImageView = view.findViewById(R.id.img_upload_check);
        this.uploadImageButton = view.findViewById(R.id.btn_upload_image);
        this.imageCheckFeedbackLinearLayout = view.findViewById(R.id.ll_feedback_image_check);
        this.imageCheckFeedbackTextView = view.findViewById(R.id.tv_feedback_image_check);
    }

    private void initializeVirusCheckViewModel() {
        this.virusCheckViewModel = new ViewModelProvider(requireActivity()).get(VirusCheckViewModel.class);
        this.virusCheckViewModel.initiateTheContext(requireActivity());
    }

    private void setSelectImageButtonOnClickListener() {
        this.selectImageButton.setOnClickListener(view -> {
            imageCheckFeedbackLinearLayout.setVisibility(View.INVISIBLE);
            imageCheckFeedbackTextView.setText("");
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
            Log.i("VirusCheck", "operation error");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUploadImageButtonOnClickListener() {
        this.uploadImageButton.setOnClickListener(view -> {
            // upload Tomato Image
            this.uploadTomatoImage();
            // observe checkFeedback live data
            this.observeCheckFeedbackLD();
        });
    }

    private void uploadTomatoImage() {
        Bitmap uploadImageBitmap = ((BitmapDrawable) this.uploadImageImageView.getDrawable()).getBitmap();
        this.virusCheckViewModel.processUploadingTomatoImage(uploadImageBitmap);
    }

    private void observeCheckFeedbackLD() {
        this.virusCheckViewModel.getCheckFeedbackLD().observe(getViewLifecycleOwner(), resultCheckFeedback -> {
            if (!resultCheckFeedback.isEmpty()){
                imageCheckFeedbackLinearLayout.setVisibility(View.VISIBLE);
                if (resultCheckFeedback.equals("error")){
                    Toast.makeText(requireActivity(), "Your image should include tomato leaf!! Please select image again!!!", Toast.LENGTH_SHORT).show();
                } else {
                    imageCheckFeedbackTextView.setText(resultCheckFeedback);
                }
            }
        });
    }

}
