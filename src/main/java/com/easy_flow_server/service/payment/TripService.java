package com.easy_flow_server.service.payment;

import com.easy_flow_server.dto.model.RideModel;
import com.easy_flow_server.dto.view.TripId;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;

import java.util.List;

public interface TripService {


    List<TripId> getOpenTrips(int numOfTrips, String passengerUsername) throws NotFoundException;

    ResponseMessage makePendingTrip(RideModel rideModel, String machineUsername);//stationeryTurnStile

    ResponseMessage makeFinalTrip(RideModel rideModel, String machineUsername);//stationeryTurnStile

    ResponseMessage makeTrip(RideModel rideModel, String machineUsername);//MovingTurnstile

}
