package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PesticideStoreMapFragment extends Fragment implements OnMapReadyCallback {
    private View view;
//    private GeoCodingAPIViewModel geoCodingAPIViewModel;
//    private CurrentMovieInfoFromSPViewModel currentMovieInfoFromSPViewModel;
//    private MainMovieMemoirDBViewModel mainMovieMemoirDBViewModel;

    private MapView mapView;
    private GoogleMap googleMap;
    private String userAddress;
    private List<String> pesticideStoreAddressList;

    private Bitmap userMarkerBitmap;
    private Bitmap pesticideStoreMarkerBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_pesticide_store_map, container, false);
        this.initiateMap(savedInstanceState);
        this.mapView.getMapAsync(this);

//        this.initiateGeoCodingAPIViewModel();
//        this.initiateMovieInfoFromSPViewModel();
//        this.initiateMainMovieMemoirDBViewModel();

        return view;
    }

    private void initiateMap(Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map_widget);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        userMarkerBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), R.drawable.location_user1);
        pesticideStoreMarkerBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), R.drawable.location_pesticide2);

        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap myMap) {
        this.googleMap = myMap;
//        this.getUserHomeAddressFromSP();// call the home address to SP
//        this.getCinemaAddressesFromDB(); // call the cinema address to DB
//
//        this.observeHomeAddressStrLiveData(); // get home address from SP --> call GeoCoding API
//        this.observeHomeAddressLagLngLiveData(); // get home address LagLng
//        this.observeAllCinemaListLiveData(); // get cinema addresses from SP --> call GeoCoding API
//        this.observeCinemaAddressesLagLngLiveData();// get cinema addresses LagLng

    }

//    private void initiateGeoCodingAPIViewModel(){
//        this.geoCodingAPIViewModel = new ViewModelProvider(requireActivity()).get(GeoCodingAPIViewModel.class);
//    }
//
//    private void initiateMainMovieMemoirDBViewModel() {
//        this.mainMovieMemoirDBViewModel = new ViewModelProvider(requireActivity()).get(MainMovieMemoirDBViewModel.class);
//        this.mainMovieMemoirDBViewModel.initiateTheContext(requireActivity());
//    }
//
//    private void initiateMovieInfoFromSPViewModel() {
//        this.currentMovieInfoFromSPViewModel = new ViewModelProvider(requireActivity()).get(CurrentMovieInfoFromSPViewModel.class);
//        this.currentMovieInfoFromSPViewModel.initiateTheContext(requireActivity());
//    }
//
//    private void getUserHomeAddressFromSP() {
//        this.currentMovieInfoFromSPViewModel.processFindingCurrentUserAddressStr();
//    }
//
//    private void getCinemaAddressesFromDB(){
//        this.mainMovieMemoirDBViewModel.processFindingAllCinemaList();
//    }
//
//    private void observeHomeAddressStrLiveData() {
//        this.currentMovieInfoFromSPViewModel.getCurrentUserAddressStrText().observe(getViewLifecycleOwner(), currentUserAddressStr -> {
//            if ((currentUserAddressStr != null) && (!currentUserAddressStr.isEmpty())){
//                String[] userInfo = currentUserAddressStr.split("\\^");
//                userName = userInfo[0];
//                currentUserAddress = userInfo[1];
//                geoCodingAPIViewModel.getCurrentUserAddressLatLng(currentUserAddress);
//            } else {
//                Toast.makeText(requireActivity(), "Can not get your home address!!!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void observeHomeAddressLagLngLiveData(){
//        this.geoCodingAPIViewModel.getCurrentUserAddressLatLngText().observe(getViewLifecycleOwner(), resultCurrentUserAddress -> {
//            if (resultCurrentUserAddress != null) {
//                googleMap.addMarker(
//                        new MarkerOptions()
//                                .position(resultCurrentUserAddress)
//                                .title(userName + "'s home")
//                                .snippet(currentUserAddress)
//                                .icon(BitmapDescriptorFactory.fromBitmap(homeMarkerBitmap))
//                );
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(resultCurrentUserAddress).zoom(10).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            } else {
//                Toast.makeText(requireActivity(), "Can not parse your home address to LagLng!!!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void observeAllCinemaListLiveData() {
//        this.mainMovieMemoirDBViewModel.getCinemaListMessage().observe(getViewLifecycleOwner(), resultCinemaList -> {
//            if ((resultCinemaList != null) && (resultCinemaList.size() != 0)) {
//                cinemaAddressList = new ArrayList<>();
//                for(Cinema c : resultCinemaList) {
//                    String cinemaName = c.getCinemaName();
//                    String cinemaPostcode = c.getCinemaPostcode();
//                    String cinemaAddress = cinemaName + " " + cinemaPostcode;
//                    cinemaAddressList.add(cinemaAddress);
//                }
//                geoCodingAPIViewModel.getAllCinemaAddressLatLng(cinemaAddressList);
//            } else {
//                Toast.makeText(requireActivity(), "There is no cinema in the DB !!!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void observeCinemaAddressesLagLngLiveData() {
//        this.geoCodingAPIViewModel.getAllCinemaAddressLatLngListText().observe(getViewLifecycleOwner(), resultCinemaLatLngs ->{
//            if ((resultCinemaLatLngs != null) && (resultCinemaLatLngs.size() != 0)) {
//                int listSize = resultCinemaLatLngs.size();
//                for(int i = 0; i< listSize; i++) {
//                    if ((resultCinemaLatLngs.get(i).latitude != 0) && (resultCinemaLatLngs.get(i).longitude != 0)) {
//                        int spaceIndex = cinemaAddressList.get(i).lastIndexOf(" ");
//                        String selectCinemaName = cinemaAddressList.get(i).substring(0, spaceIndex);
//                        String selectCinemaPostcode = cinemaAddressList.get(i).substring(spaceIndex + 1);
//                        googleMap.addMarker(
//                                new MarkerOptions()
//                                        .position(resultCinemaLatLngs.get(i))
//                                        .title(selectCinemaName)
//                                        .snippet(selectCinemaPostcode)
//                                        .icon(BitmapDescriptorFactory.fromBitmap(cinemaMarkerBitmap))
//                        );
//                    }
//                }
//
//            } else{
//                Toast.makeText(requireActivity(), "Something wrong when parsing the address !!!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
}
