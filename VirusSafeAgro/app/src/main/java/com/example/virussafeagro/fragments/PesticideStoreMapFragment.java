package com.example.virussafeagro.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.models.PesticideStoreModel;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PesticideStoreMapFragment extends Fragment implements OnMapReadyCallback {
    private MainActivity mainActivity;
    private View view;
    private MapView mapView;
    private GoogleMap googleMap;

    // for user location
    private LocationManager locationManager;
    private String provider;
    private LatLng userLocationLatLng;

    private static final int PERMISSIONS_REQUEST_LOCATION_REQUEST_CODE = 99;

    // for pesticide store location
    private List<PesticideStoreModel> pesticideStoreList;

//    private GeoCodingAPIViewModel geoCodingAPIViewModel;
//    private CurrentMovieInfoFromSPViewModel currentMovieInfoFromSPViewModel;
//    private MainMovieMemoirDBViewModel mainMovieMemoirDBViewModel;

    private Bitmap userMarkerBitmap;
    private Bitmap pesticideStoreMarkerBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_pesticide_store_map, container, false);

        // get main activity
        this.mainActivity = (MainActivity) getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_pesticide_store_map);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity) requireActivity());

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
        // ask for location permission and show the user location if getting permission
        this.checkLocationPermission();

//        this.getCinemaAddressesFromDB(); // call the cinema address to DB
//
//        this.observeHomeAddressStrLiveData(); // get home address from SP --> call GeoCoding API
//        this.observeHomeAddressLagLngLiveData(); // get home address LagLng
//        this.observeAllCinemaListLiveData(); // get cinema addresses from SP --> call GeoCoding API
//        this.observeCinemaAddressesLagLngLiveData();// get cinema addresses LagLng
    }

    // user location listener
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        }

        @Override
        public void onProviderEnabled(String arg0) {
        }

        @Override
        public void onProviderDisabled(String arg0) {
        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                userLocationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        }
    };

    // ask for getting user's current location permission
    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(
                            new String[]{
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                    android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_LOCATION_REQUEST_CODE);

        } else { // grant the permission
            // get and listen user current location
            getUserCurrentLocation();
            // show user current location in the map
            showUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults){
        if (requestCode == PERMISSIONS_REQUEST_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        mainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    // get and listen user current location
                    getUserCurrentLocation();
                    // show user current location in the map
                    showUserLocation();
                }
            } else {
                Toast.makeText(mainActivity, "Location Permission Denied", Toast.LENGTH_SHORT).show();
                // close this page
                FragmentOperator.backToLastFragment(mainActivity);
            }
        }
    }

    // get user current location
    private void getUserCurrentLocation() {
        // get location service
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        // get current available location controller
        assert locationManager != null;
        List<String> list = locationManager.getProviders(true);

        // location by GPS
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) { // location by network
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(mainActivity, "Please check your network or GPS!", Toast.LENGTH_LONG).show();
            return;
        }
        // check the location permission
        if (ActivityCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // get current location
            userLocationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            // bind location event, listen whether the location changed
            // parameter 1 : location provider type (GPS or Network)
            // parameter 2 : time interval
            // parameter 3 : location interval
            // parameter 4 : location listener
            locationManager.requestLocationUpdates(provider, 2000, 2, locationListener);
        }
    }

    // show user location in the map
    private void showUserLocation() {
        if (userLocationLatLng != null) {
            googleMap.addMarker(
                    new MarkerOptions()
                            .position(userLocationLatLng)
                            .title("Current Location")
//                            .snippet(currentUserAddress)
                            .icon(BitmapDescriptorFactory.fromBitmap(userMarkerBitmap))
            );
            CameraPosition cameraPosition = new CameraPosition.Builder().target(userLocationLatLng).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            Toast.makeText(requireActivity(), "Can not get your current address!!!", Toast.LENGTH_LONG).show();
        }
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
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if (ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (locationManager != null) {
                locationManager.removeUpdates(locationListener);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
