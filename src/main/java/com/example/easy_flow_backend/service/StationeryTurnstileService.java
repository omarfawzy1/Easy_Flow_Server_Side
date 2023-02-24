package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.dto.Models.RideModel;

public interface StationeryTurnstileService extends TurnstileService {
    String outRide(RideModel rideModel) throws BadRequestException;
}
