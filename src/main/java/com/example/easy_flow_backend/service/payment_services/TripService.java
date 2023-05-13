package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;

public interface TripService {
    ResponseMessage makePendingTrip(RideModel rideModel, String machineUsername) throws NotFoundException;//stationeryTurnStile

    ResponseMessage makeFinalTrip(RideModel rideModel, String machineUsername) throws NotFoundException;//stationeryTurnStile

    ResponseMessage makeTrip(RideModel rideModel, String machineUsername) throws NotFoundException;//MovingTurnstile

}
