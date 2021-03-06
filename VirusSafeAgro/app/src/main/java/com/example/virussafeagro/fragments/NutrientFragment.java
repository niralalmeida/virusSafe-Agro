package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.GridNutrientAdapter;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.KeyboardToggleUtils;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.viewModel.NutrientViewModel;

import java.util.ArrayList;
import java.util.List;

public class NutrientFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    // data
    private NutrientViewModel nutrientViewModel;
    public static Bitmap currentNutrientImageBitmap;

    // views
    private LinearLayout processBarLinearLayout;
    private RelativeLayout networkErrorRelativeLayout;
    private Button networkErrorRetryButton;
    private LinearLayout nutrientsGridViewLinearLayout;
    private GridView nutrientsGridView;
    private GridNutrientAdapter gridNutrientAdapter;

    // search function
    private com.example.virussafeagro.uitilities.ExtendedEditText searchVirusEditText;

    // tools
    private Handler handler1;
    private Handler handler2;
    private Handler handler3;

    public NutrientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_nutrient, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_nutrient);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_NUTRIENT);

        // initialize views
        this.initializeViews();

        // set menu selected item
        if (!this.mainActivity.isLearnIconClicked()) {
            this.mainActivity.setLearnButton(true);
        }

        // initialize view model
        this.initializeNutrientViewModel();
        // find virus info list in new Thread
        if (MainActivity.nutrientModelList.isEmpty()) {
            if (mainActivity.getNutrientViewModel().getCurrentFindNutrientListAsyncTask().isCancelled()){
                // restart the task
                mainActivity.findNutrientListFromDBAndObserveNutrientListLD();
                // control the visibility
                processBarLinearLayout.setVisibility(View.VISIBLE); // show progress bar
                nutrientsGridViewLinearLayout.setVisibility(View.GONE); // hide grid
                networkErrorRelativeLayout.setVisibility(View.GONE); // hide network error
                // wait 15 sec then cancel the task if it fails
                handler1 = new Handler();
                handler1.postDelayed(() -> {
                    if(MainActivity.nutrientModelList.isEmpty()){
                        // cancel the async task
                        mainActivity.cancelCurrentFindNutrientListAsyncTask();
                        // show the error Toast
                        Toast.makeText(mainActivity, "Connection failed! Please check your network!", Toast.LENGTH_SHORT).show();
                        // show the error image
                        processBarLinearLayout.setVisibility(View.GONE);
                        MyAnimationBox.runFadeInAnimation(networkErrorRelativeLayout, 300);
                        // set retry button on click
                        setRetryButtonOnClickListener();
                    } else {
                        // show the virus list
                        displayNutrientsCardList();
                    }
                }, 15000);
            } else {
                // show progress bar
                this.processBarLinearLayout.setVisibility(View.VISIBLE);
                this.nutrientsGridViewLinearLayout.setVisibility(View.GONE);
                // observe NutrientModel  List Live Data
                this.observeNutrientListLD();
                // wait 15 sec then cancel the task if it fails
                handler2 = new Handler();
                handler2.postDelayed(() -> {
                    if (MainActivity.nutrientModelList.isEmpty()) {
                        // cancel the async task
                        mainActivity.cancelCurrentFindNutrientListAsyncTask();
                        // show the error Toast
                        Toast.makeText(mainActivity, "Connection failed! Please check your network!", Toast.LENGTH_SHORT).show();
                        // show the error image
                        processBarLinearLayout.setVisibility(View.GONE);
                        MyAnimationBox.runFadeInAnimation(networkErrorRelativeLayout, 300);
                        // set retry button on click
                        setRetryButtonOnClickListener();
                    }
                }, 15000);
            }
        } else {
            // show the virus list
            this.displayNutrientsCardList();
        }

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentNutrientImageBitmap = null;
    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_nutrient);
        this.nutrientsGridViewLinearLayout = view.findViewById(R.id.ll_list_nutrient_list);
        this.nutrientsGridView = view.findViewById(R.id.gv_list_nutrient_list);
        this.networkErrorRelativeLayout = view.findViewById(R.id.rl_fail_network_nutrient);
        this.networkErrorRetryButton = view.findViewById(R.id.btn_reconnect_nutrient);
        this.searchVirusEditText = this.mainActivity.getDoSearchEditText();
    }

    private void initializeNutrientViewModel() {
        this.nutrientViewModel = new ViewModelProvider(requireActivity()).get(NutrientViewModel.class);
    }

    private void observeNutrientListLD() {
        mainActivity.getNutrientViewModel().getNutrientListLD().observe(mainActivity, resultNutrientList -> {
            if (!(MyJsonParser.isNutrientListTaskFailed || MainActivity.nutrientModelList.isEmpty())) {
                if (nutrientsGridViewLinearLayout.getVisibility() == View.GONE) {
                    // show the virus list
                    displayNutrientsCardList();
                }
            }
        });
    }

    private void displayNutrientsCardList() {
        // show progress bar
        this.processBarLinearLayout.setVisibility(View.GONE);
        this.nutrientsGridViewLinearLayout.setVisibility(View.VISIBLE);
        // show grid view
        gridNutrientAdapter = new GridNutrientAdapter(requireActivity(), MainActivity.nutrientModelList);
        nutrientsGridView.setAdapter(gridNutrientAdapter);
        // set GridView Item NutrientCard Click Listener
        setGridViewItemNutrientCardClickListener(MainActivity.nutrientModelList);

        // display search function
        if (!MainActivity.isLearnOrToolkitIconClickedFromLearnStacks) {
            mainActivity.displaySearch();
        } else {
            MainActivity.isLearnOrToolkitIconClickedFromLearnStacks = false;
        }
        // set SearchEditText On Change Listener
        setSearchEditOnTextChangeListener();
    }

    // set search edit text on change listener
    private void setSearchEditOnTextChangeListener() {
        searchVirusEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // get the nutrient list by input keyword and display
                displayNutrientModelListBySearching(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // get the virus model list by search input keyword
    private void displayNutrientModelListBySearching(String searchInput) {
        List<NutrientModel> searchedNutrientModelList = new ArrayList<>();
        if (!searchInput.isEmpty()) {
            List<String> nutrientStringInfoList = DataConverter.nutrientModelListToNutrientStringList(MainActivity.nutrientModelList);
            for (String nutrientString : nutrientStringInfoList) {
                int nutrientId;
                if (nutrientString.toLowerCase().contains(searchInput.toLowerCase())) {
                    if (nutrientString.substring(1, 2).equals("#")) {
                        nutrientId = Integer.parseInt(nutrientString.substring(0, 1));
                    } else {
                        nutrientId = Integer.parseInt(nutrientString.substring(0, 2));
                    }
                    for (NutrientModel nutrientModel : MainActivity.nutrientModelList) {
                        if (nutrientModel.getNutrientId() == nutrientId) {
                            searchedNutrientModelList.add(nutrientModel);
                            break;
                        }
                    }
                }
            }
        } else {
            searchedNutrientModelList = MainActivity.nutrientModelList;
        }
        // show grid view
        gridNutrientAdapter = new GridNutrientAdapter(requireActivity(), searchedNutrientModelList);
        nutrientsGridView.setAdapter(gridNutrientAdapter);
        // set GridView Item nutrient Card Click Listener
        setGridViewItemNutrientCardClickListener(searchedNutrientModelList);
    }

    // set card on click listener
    private void setGridViewItemNutrientCardClickListener(List<NutrientModel> nutrientModelListForListener){
        gridNutrientAdapter.setOnNutrientCardClickListener(position -> {
            Bundle bundle = new Bundle();
            NutrientModel currentNutrientModel = nutrientModelListForListener.get(position);
            bundle.putParcelable("currentNutrientModel", currentNutrientModel);

            // set image
            int nutrientPictureDrawableId = AppResources.getNutrientPictureDrawableId(currentNutrientModel.getNutrientId());
            currentNutrientImageBitmap = BitmapFactory.decodeResource(mainActivity.getResources(), nutrientPictureDrawableId);
            
            NutrientDetailNewFragment nutrientDetailNewFragment = new NutrientDetailNewFragment();
            nutrientDetailNewFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), nutrientDetailNewFragment, AppResources.FRAGMENT_TAG_NUTRIENT_DETAIL_NEW);
        });
    }

    // set retry button on click
    private void setRetryButtonOnClickListener(){
        this.networkErrorRetryButton.setOnClickListener(nv ->{
            // restart the task
            mainActivity.findNutrientListFromDBAndObserveNutrientListLD();
            // control the visibility
            processBarLinearLayout.setVisibility(View.VISIBLE); // show progress bar
            nutrientsGridViewLinearLayout.setVisibility(View.GONE); // hide grid
            networkErrorRelativeLayout.setVisibility(View.GONE); // hide network error
            // wait 15 sec then cancel the task if it fails
            handler3 = new Handler();
            handler3.postDelayed(() -> {
                if(MainActivity.nutrientModelList.isEmpty()){
                    // cancel the async task
                    mainActivity.cancelCurrentFindNutrientListAsyncTask();
                    // show the error Toast
                    Toast.makeText(mainActivity, "Connection failed! Please check your network!", Toast.LENGTH_SHORT).show();
                    // show the error image
                    processBarLinearLayout.setVisibility(View.GONE);
                    MyAnimationBox.runFadeInAnimation(networkErrorRelativeLayout, 300);
                } else {
                    // show the virus list
                    displayNutrientsCardList();
                }
            }, 15000);
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        // cancel the AsyncTask
        NutrientViewModel.FindNutrientListAsyncTask findNutrientListAsyncTask = this.nutrientViewModel.getCurrentFindNutrientListAsyncTask();
        findNutrientListAsyncTask.cancel(true);

        // remove the observer
        this.nutrientViewModel.getNutrientListLD().removeObservers(requireActivity());
        this.nutrientViewModel.setNutrientListLD(new ArrayList<>());

        // hide keyboard
        KeyboardToggleUtils.hideKeyboard(mainActivity);
        // close search function
        mainActivity.closeSearch();
        // set from nutrient
        MainActivity.FROM_NUTRIENT_PAGE = true;

        // set learn icon
        this.mainActivity.setLearnButton(false);

        // stop the handlers
        if (handler1 != null) {
            handler1.removeCallbacksAndMessages(null);
        }
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
        }
        if (handler3 != null) {
            handler3.removeCallbacksAndMessages(null);
        }
    }
}
