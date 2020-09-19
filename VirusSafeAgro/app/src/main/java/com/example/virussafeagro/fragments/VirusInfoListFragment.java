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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.GridVirusInfoAdapter;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
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
    private SharedPreferenceProcess spp;
    private List<VirusModel> virusModelInfoList;

    private LinearLayout processBarLinearLayout;

    private LinearLayout virusGridViewLinearLayout;
    private GridView virusGridView;
    private GridVirusInfoAdapter gridVirusInfoAdapter;

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
        this.initializeSharedPreferenceProcess();

        if (spp.getVirusModelListFromSP().get(0).getVirusFullName().isEmpty()) {
            // find virus info list in new Thread
            this.findVirusInfoListFromDB();
        } else {
            GetVirusModelListFromSPAsyncTask getVirusModelListFromSPAsyncTask = new GetVirusModelListFromSPAsyncTask();
            getVirusModelListFromSPAsyncTask.execute();
        }

        // observe VirusModel Info List Live Data
        this.observeVirusInfoListLD();
    }

    private class GetVirusModelListFromSPAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // get the virus list from spp
            virusModelInfoList = spp.getVirusModelListFromSP();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // show the virus list
            displayVirusCardList();
        }
    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_info);
        this.virusGridViewLinearLayout = view.findViewById(R.id.ll_list_virus_info_list);
        this.virusGridView = view.findViewById(R.id.gv_list_virus_info_list);
    }

    private void initializeVirusInfoViewModel() {
        this.virusInfoListViewModel = new ViewModelProvider(requireActivity()).get(VirusInfoListViewModel.class);
        this.virusInfoListViewModel.initiateSharedPreferenceProcess(requireContext());
    }

    private void initializeSharedPreferenceProcess() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireContext());
    }

    private void findVirusInfoListFromDB() {
        this.virusInfoListViewModel.processFindingVirusInfoList();
    }

    private void observeVirusInfoListLD() {
        this.virusInfoListViewModel.getVirusInfoListLD().observe(getViewLifecycleOwner(), resultVirusInfoList -> {
            if ((resultVirusInfoList != null) && (resultVirusInfoList.size() != 0)) {
                // check network connection
                if (resultVirusInfoList.get(0).getVirusDescription().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
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
