package com.example.easy_flow_backend.service.tunstile_services;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.security.Principal;

public interface StationeryTurnstileService extends TurnstileService {
    ResponseMessage outRide(RideModel rideModel) throws BadRequestException;

    String getMyStationName(Principal principal) throws BadRequestException;
}
