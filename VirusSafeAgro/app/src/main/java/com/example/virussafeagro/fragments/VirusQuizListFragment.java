package com.example.virussafeagro.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.VirusQuizListAdapter;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.example.virussafeagro.viewModel.VirusQuizListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Fragment with virus list for quiz
 * @author Haoyu Yang
 */
public class VirusQuizListFragment extends Fragment {
    private View view;

    private VirusQuizListViewModel virusQuizListViewModel;
    private SharedPreferenceProcess spp;

    private LinearLayout processBarLinearLayout;
    private NestedScrollView recyclerViewNestedScrollView;

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

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Quiz List");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();
        this.processBarLinearLayout.setVisibility(View.VISIBLE);
        this.recyclerViewNestedScrollView.setVisibility(View.INVISIBLE);

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // initialize view model
        this.initializeVirusQuizViewModel();
        // initialize SharedPreferenceProcess
        this.initializeSharedPreferenceProcess();

        if (spp.getVirusModelListFromSP().get(0).getVirusFullName().isEmpty()) {
            // find virus quiz list in new Thread
            this.findVirusQuizListFromDB();
        } else {
            GetVirusModelListFromSPAsyncTask getVirusModelListFromSPAsyncTask = new GetVirusModelListFromSPAsyncTask();
            getVirusModelListFromSPAsyncTask.execute();
        }

        // observe VirusModel Quiz List Live Data
        this.observeVirusQuizListLD();
    }

    private class GetVirusModelListFromSPAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // get the virus list from spp
            virusModelQuizList = spp.getVirusModelListFromSP();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // show the virus list
            displayVirusQuizList();
        }
    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_quiz);
        this.recyclerViewNestedScrollView = view.findViewById(R.id.nsv_list_virus_quiz);
    }

    private void initializeVirusQuizViewModel() {
        this.virusQuizListViewModel = new ViewModelProvider(requireActivity()).get(VirusQuizListViewModel.class);
        this.virusQuizListViewModel.initiateSharedPreferenceProcess(requireContext());
    }

    private void initializeSharedPreferenceProcess() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireContext());
    }

    private void findVirusQuizListFromDB() {
        this.virusQuizListViewModel.processFindingVirusQuizList();
    }

    private void observeVirusQuizListLD() {
        this.virusQuizListViewModel.getVirusQuizListLD().observe(getViewLifecycleOwner(), resultVirusQuizList -> {
            if ((resultVirusQuizList != null) && (resultVirusQuizList.size() != 0)) {
                virusModelQuizList.clear();
                virusModelQuizList = resultVirusQuizList;

                // show the virus quiz list
                displayVirusQuizList();
            }
        });
    }

    private void displayVirusQuizList() {
        // set recycler view linear layout visible and process bar invisible
        processBarLinearLayout.setVisibility(View.INVISIBLE);
        recyclerViewNestedScrollView.setVisibility(View.VISIBLE);

        // show RecyclerView
        showVirusQuizListRecyclerView();
        // set RecyclerView item take quiz button click listener
        setRecyclerViewItemTakeQuizButtonClickListener();
        // set RecyclerView item view content button click listener
        setRecyclerViewItemViewContentButtonClickListener();
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
            FragmentOperator.replaceFragment(requireActivity(), virusQuizQuestionFragment, AppResources.FRAGMENT_TAG_VIRUS_QUIZ_QUESTION);
        });
    }

    private void setRecyclerViewItemViewContentButtonClickListener(){
        virusQuizListAdapter.setOnVirusViewContentButtonClickListener(position -> {
            Bundle bundle = new Bundle();
            VirusModel currentVirusModel = virusModelQuizList.get(position);
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
            virusDetailFragment.setArguments(bundle);
            FragmentOperator.replaceFragment(requireActivity(), virusDetailFragment, AppResources.FRAGMENT_TAG_VIRUS_DETAIL);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.virusQuizListViewModel.getVirusQuizListLD().removeObservers(getViewLifecycleOwner());
        this.virusQuizListViewModel.setVirusQuizListLD(new ArrayList<>());
    }
}

