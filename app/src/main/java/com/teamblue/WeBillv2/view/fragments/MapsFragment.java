package com.teamblue.WeBillv2.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.clustering.ClusterManager;
import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.controller.MapsController;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.LocationItem;
import com.teamblue.WeBillv2.model.pojo.LocationModel;
import com.teamblue.WeBillv2.service.MapsService;
import com.teamblue.WeBillv2.view.MenuView;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    private MapsService mapsService = new MapsService();
    private FusedLocationProviderClient fusedLocationProvider;
    private final int LOCATION_REQUEST_CODE = 7002;
    private LatLng initialLoc = new LatLng(42.360081, -71.058884); // initially set to Boston

    private int ZOOM = 13;  // the zoom for map; ranges from 2 to 21; the higher the num, the more zoomed in
    private ClusterManager<LocationItem> clusterManager;
    private LocationItem clickedClusterItem;

    // 4. the map callback
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // 5. when the map is ready

            // set the map coordinates, move cam to coordinates, and zoom in
            // if permissions were granted, coordinates set to user's current location;
            // otherwise, coordinates set to Boston
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);    // set to basic map type
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLoc, ZOOM));

            // initialize the cluster manager with the context and the map
            clusterManager = new ClusterManager<LocationItem>(getContext(), googleMap);

            // point the map's listeners at the listeners implemented by the cluster manager
            googleMap.setOnCameraIdleListener(clusterManager);
            googleMap.setOnMarkerClickListener(clusterManager);
            googleMap.setOnInfoWindowLongClickListener(clusterManager.getMarkerManager());

            googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
            clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<LocationItem>() {
                @Override
                public boolean onClusterItemClick(LocationItem item) {
                    clickedClusterItem = item;
                    return false;
                }
            });

            //Info Window long click listener set to cluster manager
            clusterManager.setOnClusterItemInfoWindowLongClickListener(new ClusterManager.OnClusterItemInfoWindowLongClickListener<LocationItem>() {
                @Override
                public void onClusterItemInfoWindowLongClick(LocationItem item) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.TEMP_PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong(Constants.MAP_LAT,Double.doubleToRawLongBits(item.getLatitude()));
                    editor.putLong(Constants.MAP_LNG,Double.doubleToRawLongBits(item.getLongitude()));
                    editor.apply();
                    Bundle result = new Bundle();
                    result.putDouble("clickedItemLat", item.getLatitude());
                    result.putDouble("clickedItemLng", item.getLongitude());
                    getParentFragmentManager().setFragmentResult("mapsRequestKey", result);
                    MenuView menuView = (MenuView) getActivity();
                    menuView.fragmentSwitcher(R.id.receipt);
                }
            });

            // read location data and add to map
            mapsService.getExpenseLocations(getActivity().getApplicationContext(), new MapsController() {
                @Override
                public void getExpenseLocations(ArrayList<LocationModel> expLocationList) {
                    List<LocationModel> locationData = expLocationList;
                    List<LocationItem> locationItems = new ArrayList<>();
                    if (!locationData.isEmpty()) {
                        // for each location, grab the info for the info window that shows when
                        // a marker is clicked
                        for (LocationModel location : locationData) {
                            locationItems.add(new LocationItem(
                                    location.getLatitude(), location.getLongitude(),
                                    location.getLocation_name() + System.lineSeparator(),
                                    "Total: " + location.getTotal_amount()
                                            + System.lineSeparator()
                                            + "Visits: " + location.getVisits()
                            ));
                        }
                        clusterManager.addItems(locationItems);
                        clusterManager.getMarkerCollection().setInfoWindowAdapter(new CustomInfoWindowAdapter());
                        clusterManager.cluster();
                    }
                }
            });
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // using FLPC to get the device current location and set it as the "default" location
        // when we load the map fragment
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(getContext());

        // requesting permissions
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 1. initialize the view
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 2. initialize the map fragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        // 3. as long as mapFragment isn't null then call getMapAsync
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            // if the request for permission is cancelled, grantResults is empty
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationProvider.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                initialLoc = new LatLng(location.getLatitude(), location.getLongitude());
            }
        });
    }

    // a custom info window since the default default info window is too small to hold the
    // necessary info
    public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View customContentsView;

        CustomInfoWindowAdapter() {
            customContentsView = getLayoutInflater().inflate(R.layout.maps_info_window, null);
        }

        @Nullable
        @Override
        public View getInfoContents(@NonNull Marker marker) {
            return null;
        }

        @Nullable
        @Override
        public View getInfoWindow(@NonNull Marker marker) {
            TextView infoWindowTitle = (TextView) customContentsView.findViewById(R.id.infoWindowTitle);
            TextView infoWindowSnippet = (TextView) customContentsView.findViewById(R.id.infoWindowSnippet);

            infoWindowTitle.setText(clickedClusterItem.getTitle());
            infoWindowSnippet.setText(clickedClusterItem.getSnippet());
            return customContentsView;
        }
    }


}