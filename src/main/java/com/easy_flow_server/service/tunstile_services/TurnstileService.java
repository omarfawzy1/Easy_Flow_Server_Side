package com.easy_flow_server.service.tunstile_services;

import com.easy_flow_server.dto.Models.RideModel;
import com.easy_flow_server.entity.Turnstile;
import com.easy_flow_server.error.ResponseMessage;

import java.util.List;

public interface TurnstileService {

    ResponseMessage inRide(RideModel rideModel);

    List<Turnstile> getAllMachines();

    ResponseMessage deletMachine(String username);

}
