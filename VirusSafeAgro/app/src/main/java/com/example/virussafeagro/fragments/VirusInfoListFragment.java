package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.GridVirusInfoAdapter;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.example.virussafeagro.viewModel.VirusInfoListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Fragment with virus list for showing virus details
 * @author Haoyu Yang
 */
public class VirusInfoListFragment extends Fragment {
    private View view;

    private VirusInfoListViewModel virusInfoListViewModel;
//    private SharedPreferenceProcess spp;
    private List<VirusModel> virusModelInfoList;

    private LinearLayout processBarLinearLayout;
    private LinearLayout networkErrorLinearLayout;
    private LinearLayout virusGridViewLinearLayout;
    private GridView virusGridView;
    private GridVirusInfoAdapter gridVirusInfoAdapter;

    // search function
    private EditText searchVirusEditText;
    private ImageButton searchVirusImageButton;

    public VirusInfoListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_info_list, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Virus List");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();
        this.processBarLinearLayout.setVisibility(View.VISIBLE);
        this.virusGridViewLinearLayout.setVisibility(View.GONE);

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // initialize virus list
        virusModelInfoList = new ArrayList<>();
        // initialize view model
        this.initializeVirusInfoViewModel();
        // initialize SharedPreferenceProcess
//        this.initializeSharedPreferenceProcess();

//        if (spp.getVirusModelListFromSP().get(0).getVirusFullName().isEmpty()) {
            // find virus info list in new Thread
        this.findVirusInfoListFromDB();
//        } else {
//            GetVirusModelListFromSPAsyncTask getVirusModelListFromSPAsyncTask = new GetVirusModelListFromSPAsyncTask();
//            getVirusModelListFromSPAsyncTask.execute();
//        }

        // observe VirusModel Info List Live Data
        this.observeVirusInfoListLD();
    }

//    private class GetVirusModelListFromSPAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//            // get the virus list from spp
//            virusModelInfoList = spp.getVirusModelListFromSP();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            // show the virus list
//            displayVirusCardList();
//        }
//    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_info);
        this.virusGridViewLinearLayout = view.findViewById(R.id.ll_list_virus_info_list);
        this.virusGridView = view.findViewById(R.id.gv_list_virus_info_list);
        this.networkErrorLinearLayout = view.findViewById(R.id.ll_fail_network_virus_quiz_question);
        this.searchVirusEditText = view.findViewById(R.id.et_search_virus_info);
        this.searchVirusImageButton = view.findViewById(R.id.imgbtn_search_virus_info);
    }

    private void initializeVirusInfoViewModel() {
        this.virusInfoListViewModel = new ViewModelProvider(requireActivity()).get(VirusInfoListViewModel.class);
        this.virusInfoListViewModel.initiateSharedPreferenceProcess(requireContext());
    }

//    private void initializeSharedPreferenceProcess() {
//        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireContext());
//    }

    private void findVirusInfoListFromDB() {
        this.virusInfoListViewModel.processFindingVirusInfoList();
    }

    private void observeVirusInfoListLD() {
        this.virusInfoListViewModel.getVirusInfoListLD().observe(getViewLifecycleOwner(), resultVirusInfoList -> {
            if ((resultVirusInfoList != null) && (resultVirusInfoList.size() != 0)) {
                // check network connection
                if (resultVirusInfoList.get(0).getVirusFullName().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                    // show network error image
                    MyAnimationBox.runFadeInAnimation(networkErrorLinearLayout, 1000);
                } else {
                    virusModelInfoList.clear();
                    virusModelInfoList = resultVirusInfoList;

                    // show the virus list
                    displayVirusCardList();
                }
            }
        });
    }

    private void displayVirusCardList() {
        // set recycler view linear layout visible and process bar invisible
        processBarLinearLayout.setVisibility(View.GONE);
        MyAnimationBox.runFadeInAnimation(virusGridViewLinearLayout, 1000);

        // show grid view
        gridVirusInfoAdapter = new GridVirusInfoAdapter(requireActivity(), virusModelInfoList);
        virusGridView.setAdapter(gridVirusInfoAdapter);
        // set GridView Item VirusCard Click Listener
        setGridViewItemVirusCardClickListener();
        // set SearchEditText On Change Listener
        setSearchEditOnTextChangeListener();
        // set search image button on click listener
        setSearchImageButtonOnClickListener();
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

    private void setSearchImageButtonOnClickListener(){
        searchVirusImageButton.setOnClickListener(view -> {
            // get the virus list by input keyword and display
            displayVirusModelListBySearching(searchVirusEditText.getText().toString());
        });
    }

    // get the virus model list by search input keyword
    private void displayVirusModelListBySearching(String searchInput) {
        List<VirusModel> searchedVirusModelList = new ArrayList<>();
        if (!searchInput.isEmpty()) {
            List<String> VirusStringInfoList = DataConverter.virusModelInfoListToVirusStringInfoList(virusModelInfoList);
            for (String VirusString : VirusStringInfoList) {
                if (VirusString.toLowerCase().contains(searchInput.toLowerCase())) {
                    int virusId = Integer.parseInt(VirusString.substring(0, 1));
                    for (VirusModel virusModel : virusModelInfoList) {
                        if (virusModel.getVirusId() == virusId) {
                            searchedVirusModelList.add(virusModel);
                            break;
                        }
                    }
                }
            }
        } else {
            searchedVirusModelList = virusModelInfoList;
        }
        // show grid view
        gridVirusInfoAdapter = new GridVirusInfoAdapter(requireActivity(), searchedVirusModelList);
        virusGridView.setAdapter(gridVirusInfoAdapter);
        // set GridView Item VirusCard Click Listener
        setGridViewItemVirusCardClickListener();
    }

    // set card on click listener
    private void setGridViewItemVirusCardClickListener(){
        gridVirusInfoAdapter.setOnVirusCardClickListener(position -> {
            Bundle bundle = new Bundle();
            VirusModel currentVirusModel = virusModelInfoList.get(position);
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
            virusDetailFragment.setArguments(bundle);
            FragmentOperator.replaceFragment(requireActivity(), virusDetailFragment, AppResources.FRAGMENT_TAG_VIRUS_DETAIL);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.virusInfoListViewModel.getVirusInfoListLD().removeObservers(requireActivity());
        this.virusInfoListViewModel.setVirusInfoListLD(new ArrayList<>());
    }
}
