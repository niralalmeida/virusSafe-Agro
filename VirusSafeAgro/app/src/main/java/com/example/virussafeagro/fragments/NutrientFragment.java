package com.example.virussafeagro.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.GridNutrientAdapter;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.example.virussafeagro.viewModel.NutrientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NutrientFragment extends Fragment {
    private View view;

    private NutrientViewModel nutrientViewModel;
//    private SharedPreferenceProcess spp;
    private List<NutrientModel> nutrientModelList;
//
    private LinearLayout processBarLinearLayout;
    private LinearLayout networkErrorLinearLayout;
    private LinearLayout nutrientsGridViewLinearLayout;
    private GridView nutrientsGridView;
    private GridNutrientAdapter gridNutrientAdapter;

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
        this.processBarLinearLayout.setVisibility(View.VISIBLE);
        this.nutrientsGridViewLinearLayout.setVisibility(View.GONE);

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        // initialize nutrient list
        nutrientModelList = new ArrayList<>();
        // initialize view model
        this.initializeNutrientViewModel();
        // find virus info list in new Thread
        this.findNutrientListFromDB();

//        // initialize SharedPreferenceProcess
//        this.initializeSharedPreferenceProcess();
//
//        if (spp.getNutrientModelListFromSP().get(0).getNutrientFullName().isEmpty()) {
//            // find virus info list in new Thread
//            this.findNutrientListFromDB();
//        } else {
//            NutrientListFragment.GetNutrientModelListFromSPAsyncTask getNutrientModelListFromSPAsyncTask = new NutrientListFragment.GetNutrientModelListFromSPAsyncTask();
//            getNutrientModelListFromSPAsyncTask.execute();
//        }
//
        // observe NutrientModel  List Live Data
        this.observeNutrientListLD();
    }

//    private class GetNutrientModelListFromSPAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//            // get the virus list from spp
//            virusModelList = spp.getNutrientModelListFromSP();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            // show the virus list
//            displayNutrientCardList();
//        }
//    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_nutrient);
        this.nutrientsGridViewLinearLayout = view.findViewById(R.id.ll_list_nutrient_list);
        this.nutrientsGridView = view.findViewById(R.id.gv_list_nutrient_list);
        this.networkErrorLinearLayout = view.findViewById(R.id.ll_fail_network_nutrient);
    }

    private void initializeNutrientViewModel() {
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
                // hide progress bar
                processBarLinearLayout.setVisibility(View.GONE);
                // check network connection
                if (resultNutrientList.get(0).getNutrientReason().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                    // show network error image
                    MyAnimationBox.runFadeInAnimation(networkErrorLinearLayout, 1000);
                } else {
                    nutrientModelList.clear();
                    nutrientModelList = resultNutrientList;
                    //set recycler view linear layout visible
                    MyAnimationBox.runFadeInAnimation(nutrientsGridViewLinearLayout, 1000);
                    // show the virus list
                    displayNutrientsCardList();
                }
            }
        });
    }

    private void displayNutrientsCardList() {


        // show grid view
        gridNutrientAdapter = new GridNutrientAdapter(requireActivity(), nutrientModelList);
        nutrientsGridView.setAdapter(gridNutrientAdapter);
        // set GridView Item NutrientCard Click Listener
        setGridViewItemNutrientCardClickListener();
    }

    // set card on click listener
    private void setGridViewItemNutrientCardClickListener(){
        gridNutrientAdapter.setOnNutrientCardClickListener(position -> {
            Bundle bundle = new Bundle();
            NutrientModel currentNutrientModel = nutrientModelList.get(position);
            bundle.putParcelable("currentNutrientModel", currentNutrientModel);
            NutrientDetailFragment nutrientDetailFragment = new NutrientDetailFragment();
            nutrientDetailFragment.setArguments(bundle);
            FragmentOperator.replaceFragmentWithFadeAnimation(requireActivity(), nutrientDetailFragment, AppResources.FRAGMENT_TAG_NUTRIENT_DETAIL);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.nutrientViewModel.getNutrientListLD().removeObservers(requireActivity());
        this.nutrientViewModel.setNutrientListLD(new ArrayList<>());
    }
}
