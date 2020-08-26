package com.example.virussafeagro.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.VirusInfoListAdapter;
import com.example.virussafeagro.entities.Virus;
import com.example.virussafeagro.viewModel.VirusInfoListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment with virus list for showing virus details
 * @author Haoyu Yang
 */
public class VirusInfoListFragment extends Fragment {
    private View view;

    private VirusInfoListViewModel virusInfoListViewModel;

    private RecyclerView.LayoutManager layoutManager;
    private VirusInfoListAdapter virusInfoListAdapter;
    private RecyclerView recyclerViewForVirusInfoList;
    private List<Virus> virusInfoList = new ArrayList<>();

    public VirusInfoListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_info, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // initialize view model
        this.initializeVirusInfoViewModel();
        // find virus info list in new Thread
        this.findVirusInfoListFromDB();
        // observe Virus Info List Live Data
        this.observeVirusInfoListLD();
    }

    private void initializeVirusInfoViewModel() {
        this.virusInfoListViewModel = new ViewModelProvider(requireActivity()).get(VirusInfoListViewModel.class);
        this.virusInfoListViewModel.initiateTheContext(requireActivity());
    }

    private void findVirusInfoListFromDB() {
        this.virusInfoListViewModel.processFindingVirusInfoList();
    }

    private void observeVirusInfoListLD() {
        this.virusInfoListViewModel.getVirusInfoListLD().observe(getViewLifecycleOwner(), resultVirusInfoList -> {
            if ((resultVirusInfoList != null) && (resultVirusInfoList.size() != 0)) {
                virusInfoList.clear();
                virusInfoList = resultVirusInfoList;

                // show RecyclerView
                showVirusInfoListRecyclerView();
            }
        });
    }

    private void showVirusInfoListRecyclerView() {
        virusInfoListAdapter = new VirusInfoListAdapter(virusInfoList, requireActivity());
        recyclerViewForVirusInfoList = view.findViewById(R.id.rv_virus_info);
        recyclerViewForVirusInfoList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForVirusInfoList.setAdapter(virusInfoListAdapter);
        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForVirusInfoList.setLayoutManager(layoutManager);
    }

}
