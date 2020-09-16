package com.example.virussafeagro.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.GridVirusInfoAdapter;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.example.virussafeagro.viewModel.NutrientViewModel;
import com.example.virussafeagro.viewModel.VirusInfoListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NutrientFragment extends Fragment {
    private View view;

    private NutrientViewModel nutrientViewModel;
//    private SharedPreferenceProcess spp;
    private List<NutrientModel> nutrientModelList;
//
//    private LinearLayout processBarLinearLayout;
//
//    private LinearLayout virusGridViewLinearLayout;
//    private GridView virusGridView;
//    private GridVirusInfoAdapter gridVirusInfoAdapter;

    public NutrientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_nutrient, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Nutrient Deficiencies");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();
//        this.processBarLinearLayout.setVisibility(View.VISIBLE);
//        this.virusGridViewLinearLayout.setVisibility(View.GONE);

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        // initialize nutrient list
        nutrientModelList = new ArrayList<>();
        // initialize view model
        this.initializeVirusInfoViewModel();
        // find virus info list in new Thread
            this.findNutrientListFromDB();

//        // initialize SharedPreferenceProcess
//        this.initializeSharedPreferenceProcess();
//
//        if (spp.getVirusModelListFromSP().get(0).getVirusFullName().isEmpty()) {
//            // find virus info list in new Thread
//            this.findVirusInfoListFromDB();
//        } else {
//            VirusInfoListFragment.GetVirusModelListFromSPAsyncTask getVirusModelListFromSPAsyncTask = new VirusInfoListFragment.GetVirusModelListFromSPAsyncTask();
//            getVirusModelListFromSPAsyncTask.execute();
//        }
//
        // observe VirusModel Info List Live Data
        this.observeNutrientListLD();
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
//        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_virus_info);
//        this.virusGridViewLinearLayout = view.findViewById(R.id.ll_list_virus_info_list);
//        this.virusGridView = view.findViewById(R.id.gv_list_virus_info_list);
    }

    private void initializeVirusInfoViewModel() {
        this.nutrientViewModel = new ViewModelProvider(requireActivity()).get(NutrientViewModel.class);
//        this.nutrientViewModel.initiateSharedPreferenceProcess(requireContext());
    }

//    private void initializeSharedPreferenceProcess() {
//        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(requireContext());
//    }

    private void findNutrientListFromDB() {
        this.nutrientViewModel.processFindingNutrientList();
    }

    private void observeNutrientListLD() {
        this.nutrientViewModel.getNutrientListLD().observe(getViewLifecycleOwner(), resultNutrientList -> {
            if ((resultNutrientList != null) && (resultNutrientList.size() != 0)) {

                nutrientModelList.clear();
                nutrientModelList = resultNutrientList;

                // show the virus list
//                displayNutrientsCardList();
            }
        });
    }

    private void displayNutrientsCardList() {
//         //set recycler view linear layout visible and process bar invisible
//        processBarLinearLayout.setVisibility(View.GONE);
//        MyAnimationBox.runFadeInAnimation(virusGridViewLinearLayout, 1000);
//
//        // show grid view
//        gridVirusInfoAdapter = new GridVirusInfoAdapter(requireActivity(), virusModelInfoList);
//        virusGridView.setAdapter(gridVirusInfoAdapter);
//        // set GridView Item VirusCard Click Listener
//        setGridViewItemVirusCardClickListener();
    }

    // set card on click listener
    private void setGridViewItemVirusCardClickListener(){
//        gridVirusInfoAdapter.setOnVirusCardClickListener(position -> {
//            Bundle bundle = new Bundle();
//            VirusModel currentVirusModel = virusModelInfoList.get(position);
//            bundle.putParcelable("currentVirusModel", currentVirusModel);
//            VirusDetailFragment virusDetailFragment = new VirusDetailFragment();
//            virusDetailFragment.setArguments(bundle);
//            FragmentOperator.replaceFragmentWithFadeAnimation(requireActivity(), virusDetailFragment, AppResources.FRAGMENT_TAG_VIRUS_DETAIL);
//        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.nutrientViewModel.getNutrientListLD().removeObservers(requireActivity());
        this.nutrientViewModel.setNutrientListLD(new ArrayList<>());
    }
}
