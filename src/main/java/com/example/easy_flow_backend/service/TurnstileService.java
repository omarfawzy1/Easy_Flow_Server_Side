package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.error.BadRequestException;

public interface TurnstileService {

    String inRide(RideModel rideModel) throws BadRequestException;
}
