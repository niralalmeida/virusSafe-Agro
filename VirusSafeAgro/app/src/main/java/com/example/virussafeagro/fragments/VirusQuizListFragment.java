package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.VirusInfoListAdapter;
import com.example.virussafeagro.adapters.VirusQuizListAdapter;
import com.example.virussafeagro.entities.Virus;
import com.example.virussafeagro.uitilities.FragmentOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment with virus list for quiz
 * @author Haoyu Yang
 */
public class VirusQuizListFragment extends Fragment {
    private View view;

    private RecyclerView.LayoutManager layoutManager;
    private VirusQuizListAdapter virusQuizListAdapter;
    private RecyclerView recyclerViewForVirusQuizList;
    private List<Virus> virusQuizList = new ArrayList<>();

    public VirusQuizListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_quiz_list, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showVirusQuizListRecyclerView() {
        virusQuizListAdapter = new VirusQuizListAdapter(virusQuizList, requireActivity());
        recyclerViewForVirusQuizList = view.findViewById(R.id.rv_virus_quiz);
        recyclerViewForVirusQuizList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForVirusQuizList.setAdapter(virusQuizListAdapter);
        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForVirusQuizList.setLayoutManager(layoutManager);
    }

    private void setRecyclerViewItemTakeQuizButtonClickListener(){
        virusQuizListAdapter.setOnVirusQuizButtonClickListener(position -> {
            Bundle bundle = new Bundle();
            Virus currentVirus = virusQuizList.get(position);
            bundle.putParcelable("currentVirus", currentVirus);
//            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
//            virusDetailFragment.setArguments(bundle);
//            FragmentOperator.replaceFragmentNoBackStack(requireActivity(), virusDetailFragment);
        });
    }

    private void setRecyclerViewItemViewContentButtonClickListener(){
        virusQuizListAdapter.setOnVirusViewContentButtonClickListener(position -> {
            Bundle bundle = new Bundle();
            Virus currentVirus = virusQuizList.get(position);
            bundle.putParcelable("currentVirus", currentVirus);
            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
            virusDetailFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentNoBackStack(requireActivity(), virusDetailFragment);
        });
    }

}

