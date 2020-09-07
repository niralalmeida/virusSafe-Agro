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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
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

    private VirusCheckViewModel virusCheckViewModel;

    private ImageButton cameraButton;
    private ImageButton selectImageButton;
    private ImageView uploadImageImageView;
    private Button uploadImageButton;

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

        // initialize VirusCheckViewModel
        this.initializeVirusCheckViewModel();

        // set camera button
        this.setCameraButtonOnClickListener();
        // set selectImageButton on click listener
        this.setSelectImageButtonOnClickListener();
        // set uploadImageButton on click listener
        this.setUploadImageButtonOnClickListener();
    }

    private void initializeViews() {
        this.cameraButton = view.findViewById(R.id.btn_camera);
        this.selectImageButton = view.findViewById(R.id.btn_select_image);
        this.uploadImageImageView = view.findViewById(R.id.img_upload_check);
        this.uploadImageButton = view.findViewById(R.id.btn_upload_image);
    }

    private void initializeVirusCheckViewModel() {
        this.virusCheckViewModel = new ViewModelProvider(requireActivity()).get(VirusCheckViewModel.class);
        this.virusCheckViewModel.initiateTheContext(requireActivity());
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
        if(!data.toString().equals("Intent {  }") && resultCode == RESULT_OK){
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
            // test
//            Bundle bundle = new Bundle();
//            String resultCheckFeedback = "json error";
//            bundle.putString("resultCheckFeedback", resultCheckFeedback);
//            VirusCheckResultFragment virusCheckResultFragment = new VirusCheckResultFragment();
//            virusCheckResultFragment.setArguments(bundle);
//            FragmentOperator.replaceFragment(requireActivity(), virusCheckResultFragment);

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
                Bitmap uploadImageBitmap = ((BitmapDrawable) this.uploadImageImageView.getDrawable()).getBitmap();
                String uploadImageString = DataConverter.bitmapToStringConverter(uploadImageBitmap);
                Bundle bundle = new Bundle();
                bundle.putString("resultCheckFeedback", resultCheckFeedback);
                bundle.putString("uploadImageString", uploadImageString);
                VirusCheckResultFragment virusCheckResultFragment = new VirusCheckResultFragment();
                virusCheckResultFragment.setArguments(bundle);
                FragmentOperator.replaceFragment(requireActivity(), virusCheckResultFragment);
            }
        });
    }

}
