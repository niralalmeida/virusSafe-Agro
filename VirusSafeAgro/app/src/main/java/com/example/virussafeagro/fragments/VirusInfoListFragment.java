package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.GridVirusInfoAdapter;
import com.example.virussafeagro.adapters.ListImageGalleryAdapter;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.ImageStorage;
import com.example.virussafeagro.uitilities.KeyboardToggleUtils;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.viewModel.VirusInfoListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment with virus list for showing virus details
 * @author Haoyu Yang
 */
public class VirusInfoListFragment extends Fragment {
    private MainActivity mainActivity;
    private VirusInfoListFragment virusInfoListFragment;
    private View view;

    // data
    public static Bitmap currentVirusImageBitmap;

    private LinearLayout processBarLinearLayout;
    private LinearLayout networkErrorLinearLayout;
    private LinearLayout virusGridViewLinearLayout;
    private GridView virusGridView;
    private GridVirusInfoAdapter gridVirusInfoAdapter;

    // search function
    private com.example.virussafeagro.uitilities.ExtendedEditText searchVirusEditText;

    public VirusInfoListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_info_list, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        this.virusInfoListFragment = this;
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_virus_info);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_VIRUS_INFO);

        // initialize views
        this.initializeViews();

        // set menu selected item
        if (!this.mainActivity.isLearnIconClicked()) {
            this.mainActivity.setLearnButton(true);
        }

        // show the virus grid list
        if (MainActivity.virusModelInfoList.isEmpty()) {
            // wait for virus info list in new Thread
            this.processBarLinearLayout.setVisibility(View.VISIBLE);
            this.virusGridViewLinearLayout.setVisibility(View.GONE);
            // observe VirusModel Info List Live Data
            this.observeVirusInfoListLD();
            // wait 15 sec then cancel the task if it fails
            new Handler().postDelayed(() -> {
                if(MainActivity.virusModelInfoList.isEmpty()){
                    // cancel the async task
                    mainActivity.cancelCurrentFindVirusInfoListAsyncTask();
                    // show the error Toast
                    Toast.makeText(mainActivity, "Connection failed! Please check your network!", Toast.LENGTH_SHORT).show();
                    // show the error image
                    processBarLinearLayout.setVisibility(View.GONE);
                    MyAnimationBox.runFadeInAnimation(networkErrorLinearLayout, 300);
                }
            }, 15000);
        } else {
            // show the virus list
            displayVirusCardList();
        }

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentVirusImageBitmap = null;
    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_info);
        this.virusGridViewLinearLayout = view.findViewById(R.id.ll_list_virus_info_list);
        this.virusGridView = view.findViewById(R.id.gv_list_virus_info_list);
        this.networkErrorLinearLayout = view.findViewById(R.id.ll_fail_network_virus_info_list);
        this.searchVirusEditText = this.mainActivity.getDoSearchEditText();
    }

    private void observeVirusInfoListLD() {
        mainActivity.getVirusInfoListViewModel().getVirusInfoListLD().observe(mainActivity, resultVirusInfoList -> {
            if (!(MyJsonParser.isVirusInfoListTaskFailed || MainActivity.virusModelInfoList.isEmpty())) {
                // show the virus list
                displayVirusCardList();
            }
        });
    }

    private void displayVirusCardList() {
        // set recycler view linear layout visible and process bar invisible
        processBarLinearLayout.setVisibility(View.GONE);
        MyAnimationBox.runFadeInAnimation(virusGridViewLinearLayout, 200);

        // show grid view
        gridVirusInfoAdapter = new GridVirusInfoAdapter(requireActivity(), MainActivity.virusModelInfoList);
        virusGridView.setAdapter(gridVirusInfoAdapter);
        // set GridView Item VirusCard Click Listener
        setGridViewItemVirusCardClickListener(MainActivity.virusModelInfoList);

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
                // get the virus list by input keyword and display
                displayVirusModelListBySearching(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // get the virus model list by search input keyword
    private void displayVirusModelListBySearching(String searchInput) {
        List<VirusModel> searchedVirusModelList = new ArrayList<>();
        if (!searchInput.isEmpty()) {
            List<String> VirusStringInfoList = DataConverter.virusModelInfoListToVirusStringInfoList(MainActivity.virusModelInfoList);
            for (String VirusString : VirusStringInfoList) {
                if (VirusString.toLowerCase().contains(searchInput.toLowerCase())) {
                    int virusId = Integer.parseInt(VirusString.substring(0, 1));
                    for (VirusModel virusModel : MainActivity.virusModelInfoList) {
                        if (virusModel.getVirusId() == virusId) {
                            searchedVirusModelList.add(virusModel);
                            break;
                        }
                    }
                }
            }
        } else {
            searchedVirusModelList = MainActivity.virusModelInfoList;
        }
        // show grid view
        gridVirusInfoAdapter = new GridVirusInfoAdapter(requireActivity(), searchedVirusModelList);
        virusGridView.setAdapter(gridVirusInfoAdapter);
        // set GridView Item VirusCard Click Listener
        setGridViewItemVirusCardClickListener(searchedVirusModelList);
    }

    // set card on click listener
    private void setGridViewItemVirusCardClickListener(List<VirusModel> virusModelListForListener){
        gridVirusInfoAdapter.setOnVirusCardClickListener(position -> {
            Bundle bundle = new Bundle();
            VirusModel currentVirusModel = virusModelListForListener.get(position);
            bundle.putParcelable("currentVirusModel", currentVirusModel);

            // set image
            int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(currentVirusModel.getVirusId());
            currentVirusImageBitmap = BitmapFactory.decodeResource(mainActivity.getResources(), virusPictureDrawableId);

            VirusDetailNewFragment virusDetailNewFragment = new VirusDetailNewFragment();
            virusDetailNewFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), virusDetailNewFragment, AppResources.FRAGMENT_TAG_VIRUS_DETAIL_NEW);

        });
    }

    @Override
    public void onPause() {

        super.onPause();

        // hide keyboard
        KeyboardToggleUtils.hideKeyboard(mainActivity);
        // close search function
        mainActivity.closeSearch();
        // set from virus info
        MainActivity.FROM_VIRUS_INFO_PAGE = true;

        this.mainActivity.setLearnButton(false);

    }
}
