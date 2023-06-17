package com.example.easy_flow_backend.service.tunstile_services;

import com.example.easy_flow_backend.dto.Models.ForgetTicketModel;
import com.example.easy_flow_backend.dto.Views.StationeryMachineView;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.security.Principal;
import java.util.List;

public interface StationeryTurnstileService extends TurnstileService {
    ResponseMessage outRide(RideModel rideModel) throws BadRequestException, NotFoundException;

    String getMyStationName(Principal principal) throws BadRequestException;

    StationeryMachineView findProjectedByUsername(String username) throws NotFoundException;

    List<StationeryMachineView> getMachines();

    ResponseMessage outRideForgetTicket(ForgetTicketModel forgetTicketModel) throws BadRequestException, NotFoundException;
}
