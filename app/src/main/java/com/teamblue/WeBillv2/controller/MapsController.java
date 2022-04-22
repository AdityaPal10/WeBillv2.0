package com.teamblue.WeBillv2.controller;

import android.content.Context;

import com.teamblue.WeBillv2.model.pojo.LocationModel;
import com.teamblue.WeBillv2.service.MapsService;

import java.util.ArrayList;

public interface MapsController {

    void getExpenseLocations(ArrayList<LocationModel> expLocationList);

}
