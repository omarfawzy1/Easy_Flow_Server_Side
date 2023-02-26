package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.ResponseMessage;

public interface TurnstileService {

    ResponseMessage inRide(RideModel rideModel) throws BadRequestException;

}
