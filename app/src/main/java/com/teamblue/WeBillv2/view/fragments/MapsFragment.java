package com.teamblue.WeBillv2.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamblue.WeBillv2.R;

public class MapsFragment extends Fragment {

    // TODO add heatmap and map clusters
    private int ZOOM = 15;  // ranges from 2 to 21; the higher the num, the more zoomed in

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


            // when the map is clicked
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    // initialize marker options
                    MarkerOptions markerOptions = new MarkerOptions();

                    // set position of marker to where the user clicked on the map
                    markerOptions.position(latLng);

                    // set title of marker
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                    // remove any and all existing markers on the map
                    googleMap.clear();

                    // animating to zoom in on the marker
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));

                    // add marker on map
                    googleMap.addMarker(markerOptions);
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

}