package com.example.virussafeagro.fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;
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
    private MainActivity mainActivity;
    private View view;
    private Camera camera;

    private SharedPreferenceProcess spp;
    private VirusCheckViewModel virusCheckViewModel;

    private LinearLayout cameraLinearLayout;
    private LinearLayout selectImageLinearLayout;

    private ImageView uploadImageImageView;
    private Button uploadImageButton;
    private LinearLayout allVirusCheckLinearLayout;
    private RelativeLayout uploadingProgressBarRelativeLayout;
    private RelativeLayout virusCheckRelativeLayout;

    private boolean isUploadImageButtonClicked;

    private final int RESULT_OK = -1;
    public final int REQUEST_OPEN_CAMERA = Camera.REQUEST_TAKE_PHOTO;
    public final int REQUEST_CHOOSE_GALLERY = 5678;

    public VirusCheckFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_check, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_virus_check);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

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

        // show All Views
        this.showAllViews();
        // move Calculator And More To Right
        this.mainActivity.moveCalculatorAndMoreToRight(getTag(), 500);

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
        this.cameraLinearLayout = view.findViewById(R.id.ll_camera);
        this.selectImageLinearLayout = view.findViewById(R.id.ll_select_image);
        this.uploadImageImageView = view.findViewById(R.id.img_upload_check);
        this.uploadImageButton = view.findViewById(R.id.btn_upload_image);
        this.allVirusCheckLinearLayout = view.findViewById(R.id.ll_all_virus_check);
        this.uploadingProgressBarRelativeLayout = view.findViewById(R.id.rl_process_bar_virus_check);
        this.virusCheckRelativeLayout = view.findViewById(R.id.rl_virus_check);
    }

    private void initializeSharedPreference() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireActivity());
    }

    private void initializeVirusCheckViewModel() {
        this.virusCheckViewModel = new ViewModelProvider(requireActivity()).get(VirusCheckViewModel.class);
    }

    private void showAllViews() {
        MyAnimationBox.runFadeInAnimation(allVirusCheckLinearLayout, 1000);
    }

    private void setCameraButtonOnClickListener() {
        this.cameraLinearLayout.setOnClickListener(view -> {
            // open camera
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
        this.selectImageLinearLayout.setOnClickListener(view -> {
            // open album
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CHOOSE_GALLERY);
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
        if(requestCode == REQUEST_CHOOSE_GALLERY){
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
                // hide this virus check page and show the process bar
                this.allVirusCheckLinearLayout.setVisibility(View.GONE);
                this.uploadingProgressBarRelativeLayout.setVisibility(View.VISIBLE);
                // save the image into SharedPreference
                Bitmap uploadImageBitmap = uploadImageImageViewBitmapDrawable.getBitmap();
                PutCurrentVirusCheckImageAsyncTask putCurrentVirusCheckImageAsyncTask = new PutCurrentVirusCheckImageAsyncTask();
                putCurrentVirusCheckImageAsyncTask.execute(uploadImageBitmap);
            } else {
                Toast.makeText(requireActivity(), "Please take a photo or select a tomato leaf image by album", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class PutCurrentVirusCheckImageAsyncTask extends AsyncTask<Bitmap, Void, Void> {

        @Override
        protected Void doInBackground(Bitmap... bitmaps) {
            spp.putCurrentVirusCheckImage(bitmaps[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // upload Tomato Image
            uploadTomatoImage();
        }
    }

    private void uploadTomatoImage() {
        // get the bitmap of the image and start to upload it to waiting for the ML model result
        Bitmap uploadImageBitmap = ((BitmapDrawable) this.uploadImageImageView.getDrawable()).getBitmap();
        this.virusCheckViewModel.processUploadingTomatoImage(uploadImageBitmap);
    }

    private void observeCheckFeedbackLD() {
        this.virusCheckViewModel.getCheckFeedbackLD().observe(getViewLifecycleOwner(), resultCheckFeedback -> {
            if (isUploadImageButtonClicked) {
                if (!resultCheckFeedback.isEmpty()){
                    // save the feedback into bundle and send it to the result fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("resultCheckFeedback", resultCheckFeedback);
                    VirusCheckResultFragment virusCheckResultFragment = new VirusCheckResultFragment();
                    virusCheckResultFragment.setArguments(bundle);
                    FragmentOperator.replaceFragment(requireActivity(), virusCheckResultFragment, AppResources.FRAGMENT_TAG_VIRUS_CHECK_RESULT);
                } else {
                    // show this virus check page and hide the process bar
                    this.showAllViews();
                    this.uploadingProgressBarRelativeLayout.setVisibility(View.GONE);
                    Toast.makeText(requireActivity(), "The remote service stop working!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        // clear all the observer for the LiveData
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
        this.virusCheckViewModel.getCheckFeedbackLD().removeObservers(lifecycleOwner);
    }
}
