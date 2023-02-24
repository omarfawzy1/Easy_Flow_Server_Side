package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.Dto.Models.RideModel;
import com.example.easy_flow_backend.error.BadRequestException;

public interface TurnStileService {

    String inRide(RideModel rideModel) throws BadRequestException;

}
