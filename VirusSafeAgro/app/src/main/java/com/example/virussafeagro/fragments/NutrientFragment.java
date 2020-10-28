package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
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
    private LinearLayout networkErrorLinearLayout;
    private LinearLayout nutrientsGridViewLinearLayout;
    private GridView nutrientsGridView;
    private GridNutrientAdapter gridNutrientAdapter;

    // search function
    private com.example.virussafeagro.uitilities.ExtendedEditText searchVirusEditText;

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
            // show progress bar
            this.processBarLinearLayout.setVisibility(View.VISIBLE);
            this.nutrientsGridViewLinearLayout.setVisibility(View.GONE);
            // observe NutrientModel  List Live Data
            this.observeNutrientListLD();
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
        this.networkErrorLinearLayout = view.findViewById(R.id.ll_fail_network_nutrient);
        this.searchVirusEditText = this.mainActivity.getDoSearchEditText();
    }

    private void initializeNutrientViewModel() {
        this.nutrientViewModel = new ViewModelProvider(requireActivity()).get(NutrientViewModel.class);
    }

    private void observeNutrientListLD() {
        mainActivity.getNutrientViewModel().getNutrientListLD().observe(mainActivity, resultNutrientList -> {
            if (!MainActivity.nutrientModelList.isEmpty()) {
                // show the virus list
                displayNutrientsCardList();
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

        this.mainActivity.setLearnButton(false);
    }
}
