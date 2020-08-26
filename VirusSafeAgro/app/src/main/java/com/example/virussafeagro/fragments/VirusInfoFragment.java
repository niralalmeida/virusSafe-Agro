package com.example.virussafeagro.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.VirusInfoListAdapter;
import com.example.virussafeagro.model.VirusModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment with virus list for showing virus details
 * @author Haoyu Yang
 */
public class VirusInfoFragment extends Fragment {
    private View view;
    private RecyclerView.LayoutManager layoutManager;
    private VirusInfoListAdapter virusInfoListAdapter;
    private RecyclerView recyclerViewForVirusInfoList;
    private List<VirusModel> virusInfoList;

    public VirusInfoFragment() {
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

        this.setVirusDataTest();
        this.showVirusInfoListRecyclerView();
    }

    private void showVirusInfoListRecyclerView() {
        virusInfoListAdapter = new VirusInfoListAdapter(virusInfoList, requireActivity());
        recyclerViewForVirusInfoList = view.findViewById(R.id.rv_virus_info);
        recyclerViewForVirusInfoList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForVirusInfoList.setAdapter(virusInfoListAdapter);
        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForVirusInfoList.setLayoutManager(layoutManager);
    }

    // test
    public void setVirusDataTest() {
        this.virusInfoList = new ArrayList<>();
        VirusModel vm1 = new VirusModel();
        VirusModel vm2 = new VirusModel();
        VirusModel vm3 = new VirusModel();
        vm1.setVirusId(1);
        vm1.setVirusFullName("virus no 1");
        vm2.setVirusId(2);
        vm2.setVirusFullName("virus no 2");
        vm3.setVirusId(3);
        vm3.setVirusFullName("virus no 3");
        this.virusInfoList.add(vm1);
        this.virusInfoList.add(vm2);
        this.virusInfoList.add(vm3);


    }

}
