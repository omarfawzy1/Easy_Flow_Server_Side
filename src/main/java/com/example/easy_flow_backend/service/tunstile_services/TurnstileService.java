package com.example.easy_flow_backend.service.tunstile_services;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Turnstile;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.util.List;

public interface TurnstileService {

    ResponseMessage inRide(RideModel rideModel) throws BadRequestException, NotFoundException;

    List<Turnstile> getAllMachines();

    ResponseMessage deletMachine(String username) throws NotFoundException;

}
