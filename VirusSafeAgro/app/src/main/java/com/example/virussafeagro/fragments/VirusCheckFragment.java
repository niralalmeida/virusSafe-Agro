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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.example.virussafeagro.viewModel.VirusCheckViewModel;
import com.mindorks.paracamera.Camera;

import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * Fragment for uploading tomato pictures to identify whether they are infected by some viruses
 * @author Haoyu Yang
 */
public class VirusCheckFragment extends Fragment {
    private View view;
    private Camera camera;

    private SharedPreferenceProcess spp;
    private VirusCheckViewModel virusCheckViewModel;

    private ImageButton cameraButton;
    private ImageButton selectImageButton;
    private ImageView uploadImageImageView;
    private Button uploadImageButton;
    private LinearLayout allVirusCheckLinearLayout;
    private LinearLayout uploadingProgressBarLinearLayout;

    private boolean isUploadImageButtonClicked;

    private final int RESULT_OK = -1;

    public VirusCheckFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_check, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Virus Check");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // initialize SharedPreference
        this.initializeSharedPreference();

        // initialize VirusCheckViewModel
        this.initializeVirusCheckViewModel();

        // set camera button
        this.setCameraButtonOnClickListener();
        // set selectImageButton on click listener
        this.setSelectImageButtonOnClickListener();
        // set uploadImageButton on click listener
        this.setUploadImageButtonOnClickListener();

        // observe checkFeedback live data
        this.observeCheckFeedbackLD();
    }

    private void initializeViews() {
        this.isUploadImageButtonClicked = false;
        this.cameraButton = view.findViewById(R.id.btn_camera);
        this.selectImageButton = view.findViewById(R.id.btn_select_image);
        this.uploadImageImageView = view.findViewById(R.id.img_upload_check);
        this.uploadImageButton = view.findViewById(R.id.btn_upload_image);
        this.allVirusCheckLinearLayout = view.findViewById(R.id.ll_all_virus_check);
        this.uploadingProgressBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_check);
    }

    private void initializeSharedPreference() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireActivity());
    }

//    private void showUploadImageViewFromSP() {
//        Bitmap currentVirusCheckImageBitmap = spp.getCurrentVirusCheckImage();
//        if (currentVirusCheckImageBitmap == null) {
//            Bitmap defaultLeafBitmap = DataConverter.drawableImageToBitmap(requireActivity(), R.drawable.default_leaf);
//            this.uploadImageImageView.setImageBitmap(defaultLeafBitmap);
//        } else {
//            this.uploadImageImageView.setImageBitmap(currentVirusCheckImageBitmap);
//        }
//    }

    private void initializeVirusCheckViewModel() {
        this.virusCheckViewModel = new ViewModelProvider(requireActivity()).get(VirusCheckViewModel.class);
    }

    private void setCameraButtonOnClickListener() {
        this.cameraButton.setOnClickListener(view -> {
            openCamera();
        });
    }

    private void openCamera() {
        camera = new Camera.Builder()
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)
                .build(this);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSelectImageButtonOnClickListener() {
        this.selectImageButton.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // for camera result
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = camera.getCameraBitmap();
            if (bitmap != null) {
                this.uploadImageImageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(requireActivity(), "Picture not taken!", Toast.LENGTH_SHORT).show();
            }
        }
        // for album result
        if(data != null){
            if(!data.toString().equals("Intent {  }") && resultCode == RESULT_OK){
                Uri uri = data.getData();
                Log.e("uri", uri.toString());
                ContentResolver cr = requireActivity().getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    this.uploadImageImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(requireActivity(), "The image file you select is corrupted! Please choose another one!!", Toast.LENGTH_SHORT).show();
                    Log.e("Exception", e.getMessage(),e);
                }
            }else{
                Log.i("VirusCheck", "operation error");
            }
        }
    }

    private void setUploadImageButtonOnClickListener() {
        this.uploadImageButton.setOnClickListener(view -> {
            // set isUploadImageButtonClicked true
            isUploadImageButtonClicked = true;
            // get uploadImageImageView BitmapDrawable
            BitmapDrawable uploadImageImageViewBitmapDrawable = (BitmapDrawable) this.uploadImageImageView.getDrawable();
            // check the uploadImageImageView is same as the default leaf image
            if (!DataConverter.isSameImage(uploadImageImageViewBitmapDrawable, requireActivity(), R.drawable.default_leaf)) {
                // save the image into SharedPreference
                Bitmap uploadImageBitmap = uploadImageImageViewBitmapDrawable.getBitmap();
                SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireActivity());
                spp.putCurrentVirusCheckImage(uploadImageBitmap);

                // upload Tomato Image
                this.uploadTomatoImage();

            } else {
                Toast.makeText(requireActivity(), "Please take a photo or select a tomato leaf image by album", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadTomatoImage() {
        // hide this virus check page and show the process bar
        this.allVirusCheckLinearLayout.setVisibility(View.INVISIBLE);
        this.uploadingProgressBarLinearLayout.setVisibility(View.VISIBLE);

        // get the bitmap of the image and start to upload it to waiting for the ML model result
        Bitmap uploadImageBitmap = ((BitmapDrawable) this.uploadImageImageView.getDrawable()).getBitmap();
        this.virusCheckViewModel.processUploadingTomatoImage(uploadImageBitmap);
    }

    private void observeCheckFeedbackLD() {
        this.virusCheckViewModel.getCheckFeedbackLD().observe(getViewLifecycleOwner(), resultCheckFeedback -> {
            // test
            System.out.println("----->>> observeCheckFeedbackLD result: " + resultCheckFeedback);

            if (isUploadImageButtonClicked) {
                if (!resultCheckFeedback.isEmpty()){
                    // test
                    System.out.println("=====>>> Open fragment observeCheckFeedbackLD result: " + resultCheckFeedback);

                    Bundle bundle = new Bundle();
                    bundle.putString("resultCheckFeedback", resultCheckFeedback);
                    VirusCheckResultFragment virusCheckResultFragment = new VirusCheckResultFragment();
                    virusCheckResultFragment.setArguments(bundle);
                    FragmentOperator.replaceFragment(requireActivity(), virusCheckResultFragment);
                } else {
                    // hide this virus check page and show the process bar
                    this.allVirusCheckLinearLayout.setVisibility(View.VISIBLE);
                    this.uploadingProgressBarLinearLayout.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireActivity(), "The remote service stop working!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
