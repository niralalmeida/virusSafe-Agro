package com.example.virussafeagro.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.DetectActivity;
import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.TomatoCameraActivity;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;

import org.jetbrains.annotations.NotNull;

public class ToolkitFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    // top
    private LinearLayout virusCheckLinearLayout;
    private LinearLayout tomatoDetectLinearLayout;
    private LinearLayout tomatoDetectIconLinearLayout;
    private LinearLayout learnLinearLayout;
    // middle
    private LinearLayout controlStrategiesLinearLayout;
    private LinearLayout pesticideStoresLinearLayout;
    private LinearLayout factorsLinearLayout;
    private LinearLayout insightsLinearLayout;
    // tools
    private static final int PERMISSIONS_REQUEST_CAMERA_REQUEST_CODE = 99;
    private static final int PERMISSIONS_REQUEST_LOCATION_REQUEST_CODE = 66;

    public ToolkitFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_toolkit, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_toolkit);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // pop all fragment in the stack
        FragmentOperator.popAllFragmentsInStack(getChildFragmentManager());
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_TOOLKIT);

        // initialize Views
        this.initializeViews();

        // set menu selected item
        if (!this.mainActivity.isToolkitIconClicked()) {
            this.mainActivity.setToolkitButton(true);
        }

        // move Calculator And More To Right
        this.mainActivity.moveTipAndMoreToRight(getTag(), 200);

        // control all tiles on click listeners
        this.allTilesOnClickListener();

        return this.view;
    }

    private void initializeViews() {
        this.virusCheckLinearLayout = view.findViewById(R.id.ll_virus_check_toolkit);
        this.tomatoDetectLinearLayout = view.findViewById(R.id.ll_tomato_detect_toolkit);
        this.tomatoDetectIconLinearLayout = view.findViewById(R.id.ll_tomato_detect_icon_toolkit);
        this.learnLinearLayout = view.findViewById(R.id.ll_learn_toolkit);
        this.controlStrategiesLinearLayout = view.findViewById(R.id.ll_control_strategies_toolkit);
        this.pesticideStoresLinearLayout = view.findViewById(R.id.ll_pesticide_store_toolkit);
        this.factorsLinearLayout = view.findViewById(R.id.ll_factors_toolkit);
        this.insightsLinearLayout = view.findViewById(R.id.ll_insights_toolkit);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void allTilesOnClickListener() {

        // virus check
        this.setVirusCheckTileOnClickListener();
        // tomato detect
        this.setTomatoDetectTileOnClickListener();
        // virus info
        this.setLearnTileOnClickListener();

        // control strategies
        this.setControlStrategiesOnClickListener();
        // pesticide stores
        this.setPesticideStoresTileOnClickListener();
        // factors
        this.setFactorsTileOnClickListener();
        // insights
        this.setInsightsTileOnClickListener();
    }

    // [menu] virus check
    private void setVirusCheckTileOnClickListener() {
        this.virusCheckLinearLayout.setOnClickListener(llView -> {
            mainActivity.showVirusCheckFragment();
        });
    }

    // [menu] tomato detect
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTomatoDetectTileOnClickListener() {
        this.tomatoDetectLinearLayout.setOnClickListener(llView -> {
            checkCameraPermissions();
        });
    }

    // ask for getting user's current location permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkCameraPermissions() {
        if (ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    PERMISSIONS_REQUEST_CAMERA_REQUEST_CODE);

        } else { // grant the permission
            // start camera activity
            openCameraForTomatoDetect();
        }
    }

    private void openCameraForTomatoDetect() {
        Intent intent = new Intent(mainActivity, TomatoCameraActivity.class);
        mainActivity.startActivity(intent);
        mainActivity.overridePendingTransition(R.anim.activity_slide_in_top, 0);
    }

    // [menu] learn
    private void setLearnTileOnClickListener() {
        this.learnLinearLayout.setOnClickListener(llView -> {
            // remove all fragment in stack
            FragmentOperator.popAllFragmentsInStack(getChildFragmentManager());
            FragmentOperator.replaceFragmentWithSlideFromBottomAnimation(requireActivity(), new LearnFragment(), AppResources.FRAGMENT_TAG_LEARN);
        });
    }

    // control strategies
    private void setControlStrategiesOnClickListener() {
        this.controlStrategiesLinearLayout.setOnClickListener(llView -> {
            ControlStrategiesFragment controlStrategiesFragment = new ControlStrategiesFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                controlStrategiesFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(controlStrategiesLinearLayout, ViewCompat.getTransitionName(controlStrategiesLinearLayout))
                    .replace(R.id.fl_fragments, controlStrategiesFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    // pesticide stores
    private void setPesticideStoresTileOnClickListener() {
        this.pesticideStoresLinearLayout.setOnClickListener(llView -> {
            checkLocationPermission();
        });
    }

    // ask for getting user's current location permission
    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION_REQUEST_CODE);

        } else { // grant the permission
            // get and listen user current location
            // open Pesticide Store Map
            openPesticideStoreMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults){
        if (requestCode == PERMISSIONS_REQUEST_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        mainActivity,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // start camera activity
                    openCameraForTomatoDetect();
                }
            } else {
                Toast.makeText(mainActivity, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PERMISSIONS_REQUEST_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        mainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // open Pesticide Store Map
                    openPesticideStoreMap();
                }
            } else {
                Toast.makeText(mainActivity, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // open Pesticide Store Map
    private void openPesticideStoreMap() {
        PesticideStoreMapFragment pesticideStoreMapFragment = new PesticideStoreMapFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pesticideStoreMapFragment.setSharedElementEnterTransition(
                    TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
        mainActivity.getSupportFragmentManager().beginTransaction()
                .addSharedElement(pesticideStoresLinearLayout, ViewCompat.getTransitionName(pesticideStoresLinearLayout))
                .replace(R.id.fl_fragments, pesticideStoreMapFragment)
                .addToBackStack(null)
                .commit();
    }

    // factors
    private void setFactorsTileOnClickListener() {
        this.factorsLinearLayout.setOnClickListener(llView -> {
//            FactorsFragment factorsFragment = new FactorsFragment();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                factorsFragment.setSharedElementEnterTransition(
//                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
//            }
//            mainActivity.getSupportFragmentManager().beginTransaction()
//                    .addSharedElement(factorsLinearLayout, ViewCompat.getTransitionName(factorsLinearLayout))
//                    .replace(R.id.fl_fragments, factorsFragment)
//                    .addToBackStack(null)
//                    .commit();

            EnvironmentConditionFragment environmentConditionFragment = new EnvironmentConditionFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                environmentConditionFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(factorsLinearLayout, ViewCompat.getTransitionName(factorsLinearLayout))
                    .replace(R.id.fl_fragments, environmentConditionFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    // insights
    private void setInsightsTileOnClickListener() {
        this.insightsLinearLayout.setOnClickListener(llView -> {
            InsightsFragment insightsFragment = new InsightsFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                insightsFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(insightsLinearLayout, ViewCompat.getTransitionName(insightsLinearLayout))
                    .replace(R.id.fl_fragments, insightsFragment)
                    .addToBackStack(null)
                    .commit();
//            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new InsightsFragment(), AppResources.FRAGMENT_TAG_INSIGHTS);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mainActivity.setToolkitButton(false);
    }
}
