package com.teamblue.WeBillv2.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.controller.MapsController;
import com.teamblue.WeBillv2.model.pojo.LocationItem;
import com.teamblue.WeBillv2.model.pojo.LocationModel;
import com.teamblue.WeBillv2.service.MapsService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapsFragment extends Fragment {

    private MapsService mapsService = new MapsService();

    private int ZOOM = 13;  // ranges from 2 to 21; the higher the num, the more zoomed in
    private ClusterManager<LocationItem> clusterManager;
    private LocationItem clickedClusterItem;

    // 4. the map callback
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // 5. when the map is ready

            // set the map coordinates to Boston
            LatLng boston = new LatLng(42.360081, -71.058884);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);    // set to basic map

            // move cam to map coordinates and zoom in
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(boston, ZOOM));

            // initialize the cluster manager with the context and the map
            clusterManager = new ClusterManager<LocationItem>(getContext(), googleMap);

            // point the map's listeners at the listeners implemented by the cluster manager
            googleMap.setOnCameraIdleListener(clusterManager);
            googleMap.setOnMarkerClickListener(clusterManager);

            googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
            clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<LocationItem>() {
                                                             @Override
                                                             public boolean onClusterItemClick(LocationItem item) {
                                                                 clickedClusterItem = item;
                                                                 return false;
                                                             }
                                                         });

                    // read location data and add to map
                    mapsService.getExpenseLocations(getActivity().getApplicationContext(), new MapsController() {
                        @Override
                        public void getExpenseLocations(ArrayList<LocationModel> expLocationList) {
                            List<LocationModel> locationData = expLocationList;
                            List<LocationItem> locationItems = new ArrayList<>();
                            if (!locationData.isEmpty()) {
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