package com.easy_flow_server.service.tunstile;

import com.easy_flow_server.dto.model.RideModel;
import com.easy_flow_server.entity.Turnstile;
import com.easy_flow_server.error.ResponseMessage;

import java.util.List;

public interface TurnstileService {

    ResponseMessage inRide(RideModel rideModel);

    List<Turnstile> getAllMachines();

    ResponseMessage deleteMachine(String username);

}
