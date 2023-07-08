package com.easy_flow_server.service.tunstile;

import com.easy_flow_server.dto.model.ForgetTicketModel;
import com.easy_flow_server.dto.model.RideModel;
import com.easy_flow_server.dto.view.StationeryMachineView;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;

import java.security.Principal;
import java.util.List;

public interface StationeryTurnstileService extends TurnstileService {
    ResponseMessage outRide(RideModel rideModel);

    String getMyStationName(Principal principal) throws BadRequestException;

    StationeryMachineView findProjectedByUsername(String username) throws NotFoundException;

    List<StationeryMachineView> getMachines();

    ResponseMessage outRideForgetTicket(ForgetTicketModel forgetTicketModel);
}
