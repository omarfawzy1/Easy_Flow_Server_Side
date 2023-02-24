package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.Dto.Models.RideModel;

public interface StationeryTurnstileService extends TurnStileService {
    String outRide(RideModel rideModel) throws BadRequestException;
}
