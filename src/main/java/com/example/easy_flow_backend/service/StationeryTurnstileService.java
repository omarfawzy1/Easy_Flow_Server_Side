package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.view.RideModel;

public interface StationeryTurnstileService {
    String inRide(RideModel rideModel) throws BadRequestException;
    String outRide(RideModel rideModel) throws BadRequestException;
}
