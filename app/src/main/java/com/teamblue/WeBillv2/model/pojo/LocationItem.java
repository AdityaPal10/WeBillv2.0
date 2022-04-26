package com.teamblue.WeBillv2.model.pojo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * last updated: 2022/04/15
 * A class to represent a location to be denoted by a marker in the map fragment
 */
public class LocationItem implements ClusterItem {

    private final LatLng position;  // lat,lng coordinates of the location
    private final String title;     // name of the location i.e. business name
    private final String snippet;   // description of the location
    private double latitude;
    private double longitude;

    public LocationItem(double lat, double lng, String title, String snippet) {
        position = new LatLng(lat, lng);
        this.title = title;
        this.snippet = snippet;
        this.latitude = lat;
        this.longitude = lng;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
