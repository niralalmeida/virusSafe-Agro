package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.VirusQuizListAdapter;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.viewModel.VirusQuizListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment with virus list for quiz
 * @author Haoyu Yang
 */
public class VirusQuizListFragment extends Fragment {
    private View view;

    private VirusQuizListViewModel virusQuizListViewModel;

    private RecyclerView.LayoutManager layoutManager;
    private VirusQuizListAdapter virusQuizListAdapter;
    private RecyclerView recyclerViewForVirusQuizList;
    private List<VirusModel> virusModelQuizList = new ArrayList<>();

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

        // initialize view model
        this.initializeVirusQuizViewModel();
        // find virus quiz list in new Thread
        this.findVirusQuizListFromDB();
        // observe VirusModel Quiz List Live Data
        this.observeVirusQuizListLD();
    }

    private void initializeVirusQuizViewModel() {
        this.virusQuizListViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizListViewModel.class);
        this.virusQuizListViewModel.initiateTheContext(requireActivity());
    }

    private void findVirusQuizListFromDB() {
        this.virusQuizListViewModel.processFindingVirusQuizList();
    }

    private void observeVirusQuizListLD() {
        this.virusQuizListViewModel.getVirusQuizListLD().observe(getViewLifecycleOwner(), resultVirusQuizList -> {
            if ((resultVirusQuizList != null) && (resultVirusQuizList.size() != 0)) {
                virusModelQuizList.clear();
                virusModelQuizList = resultVirusQuizList;

                // show RecyclerView
                showVirusQuizListRecyclerView();
                // set RecyclerView item take quiz button click listener
                setRecyclerViewItemTakeQuizButtonClickListener();
                // set RecyclerView item view content button click listener
                setRecyclerViewItemViewContentButtonClickListener();
            }
        });
    }

    private void showVirusQuizListRecyclerView() {
        virusQuizListAdapter = new VirusQuizListAdapter(virusModelQuizList, requireActivity());
        recyclerViewForVirusQuizList = view.findViewById(R.id.rv_virus_quiz);
        recyclerViewForVirusQuizList.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForVirusQuizList.setAdapter(virusQuizListAdapter);
        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForVirusQuizList.setLayoutManager(layoutManager);
    }

    private void setRecyclerViewItemTakeQuizButtonClickListener(){
        virusQuizListAdapter.setOnVirusQuizButtonClickListener(position -> {
            Bundle bundle = new Bundle();
            VirusModel currentVirusModel = virusModelQuizList.get(position);
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            VirusQuizQuestionFragment virusQuizQuestionFragment = new VirusQuizQuestionFragment();
            virusQuizQuestionFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentNoBackStack(requireActivity(), virusQuizQuestionFragment);
        });
    }

    private void setRecyclerViewItemViewContentButtonClickListener(){
        virusQuizListAdapter.setOnVirusViewContentButtonClickListener(position -> {
            Bundle bundle = new Bundle();
            VirusModel currentVirusModel = virusModelQuizList.get(position);
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
            virusDetailFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentNoBackStack(requireActivity(), virusDetailFragment);
        });
    }

}

