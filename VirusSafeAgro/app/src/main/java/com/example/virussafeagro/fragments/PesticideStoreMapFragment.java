package com.example.virussafeagro.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.MapInfoWindowAdapter;
import com.example.virussafeagro.models.PesticideStoreModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.viewModel.PesticideStoreMapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static final String USER_MARKER_NAME_FOR_HASH_MAP = "Current Location";

    // for pesticide store location
    private List<PesticideStoreModel> pesticideStoreList = new ArrayList<>();
    private PesticideStoreMapViewModel pesticideStoreMapViewModel;
    private Map<Marker, PesticideStoreModel> pesticideStoreMarkerMap = new HashMap<>();

    // marker icon bitmap
    private Bitmap userMarkerBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_pesticide_store_map, container, false);

        // get main activity
        this.mainActivity = (MainActivity) getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_pesticide_store_map);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity) requireActivity());
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_PESTICIDE_STORES);

        // initialize PesticideStoreMapViewModel
        this.initializePesticideStoreMapViewModel();

        // initialize the map
        this.initiateMap(savedInstanceState);
        this.mapView.getMapAsync(this);

        // set menu selected item
        if (!this.mainActivity.isToolkitIconClicked()) {
            this.mainActivity.setToolkitButton(true);
        }

        return view;
    }

    private void initiateMap(Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map_widget);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        userMarkerBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), R.drawable.location_user2);

        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap myMap) {
        this.googleMap = myMap;
        // get and listen user current location
        getUserCurrentLocation();
        // show user current location in the map
        showUserLocation();
    }

    private void initializePesticideStoreMapViewModel() {
        this.pesticideStoreMapViewModel = new ViewModelProvider(requireActivity()).get(PesticideStoreMapViewModel.class);
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
                // remove the listener
                locationManager.removeUpdates(locationListener);
                // find the pesticide stores by Place API
                findPesticideStoreList();
                // observe PesticideStoreList live data
                observePesticideStoreListLD();
            }
        }
    };

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
            // remove the listener
            locationManager.removeUpdates(locationListener);

            // find the pesticide stores by Place API
            this.findPesticideStoreList();
            // observe PesticideStoreList live data
            this.observePesticideStoreListLD();
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
        googleMap.clear();
        if (userLocationLatLng != null) {
            Marker userMarker = googleMap.addMarker(
                new MarkerOptions()
                        .position(userLocationLatLng)
                        .title("Current Location")
                        .icon(BitmapDescriptorFactory.fromBitmap(userMarkerBitmap))
            );
            PesticideStoreModel pesticideStoreModel = new PesticideStoreModel(USER_MARKER_NAME_FOR_HASH_MAP);
            pesticideStoreMarkerMap.put(userMarker, pesticideStoreModel);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(userLocationLatLng).zoom(13).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            Toast.makeText(requireActivity(), "Can not get your current address!!!", Toast.LENGTH_LONG).show();
        }
    }

    // find Pesticide Store List by view model
    private void findPesticideStoreList() {
        // get radius
        double radius = 3500;

        // get location
        if (userLocationLatLng != null) {
            double latitude = userLocationLatLng.latitude;
            double longitude = userLocationLatLng.longitude;

            // find by view model
            this.pesticideStoreMapViewModel.processFindingPesticideStoreList(latitude, longitude, radius);
        }
    }

    // observe PesticideStoreList live data
    private void observePesticideStoreListLD() {
        this.pesticideStoreMapViewModel.getPesticideStoreListLD().observe(getViewLifecycleOwner(), resultPesticideStoreList -> {
            // check network connection
            if (resultPesticideStoreList.get(0).getStoreName().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
            } // check result status
            else if (resultPesticideStoreList.get(0).getStoreName().equals(MyJsonParser.PLACE_API_ERROR_MESSAGE)) {
                Toast.makeText(requireActivity(),MyJsonParser.PLACE_API_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
            } else {
                pesticideStoreList.clear();
                pesticideStoreList = resultPesticideStoreList;

                showPesticideStoreLocations();
            }
        });
    }

    // show Pesticide Store Locations
    private void showPesticideStoreLocations() {
        for (PesticideStoreModel pesticideStoreModel : pesticideStoreList){
            Bitmap pesticideStoreMarkerBitmap;
            String snippet = "";
            if (pesticideStoreModel.isHasOpeningHours()){
                snippet = pesticideStoreModel.isOpenNow() ? "is open now : yes" : "is open now : no";
                if (pesticideStoreModel.isOpenNow()) {
                    pesticideStoreMarkerBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), R.drawable.location_pesticide8);
                } else {
                    pesticideStoreMarkerBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), R.drawable.location_pesticide9);
                }
            } else {
                snippet = "business status : " + pesticideStoreModel.getBusinessStatus();
                pesticideStoreMarkerBitmap = BitmapFactory.decodeResource(requireActivity().getResources(), R.drawable.location_pesticide8);
            }

            Marker pesticideStoreMarker = googleMap.addMarker(
                new MarkerOptions()
                    .position(pesticideStoreModel.getLocationLatLng())
                    .title(pesticideStoreModel.getStoreName())
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromBitmap(pesticideStoreMarkerBitmap))
            );

            pesticideStoreMarkerMap.put(pesticideStoreMarker, pesticideStoreModel);
        }

        // set the bottom right toolbar enabled
        googleMap.getUiSettings().setMapToolbarEnabled(true);

        // set a custom info window adapter for the google map
        MapInfoWindowAdapter mapInfoWindowAdapter = new MapInfoWindowAdapter(mainActivity, pesticideStoreMarkerMap);
        googleMap.setInfoWindowAdapter(mapInfoWindowAdapter);

        // set info window on click listener
        googleMap.setOnInfoWindowClickListener(Marker::hideInfoWindow);

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

        // cancel the AsyncTask
        PesticideStoreMapViewModel.FindPesticideStoreListAsyncTask findPesticideStoreListAsyncTask = this.pesticideStoreMapViewModel.getCurrentFindPesticideStoreListAsyncTask();
        findPesticideStoreListAsyncTask.cancel(true);

        if (ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (locationManager != null) {
                locationManager.removeUpdates(locationListener);
            }
        }

        this.mainActivity.setToolkitButton(false);
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
