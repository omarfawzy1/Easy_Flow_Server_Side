package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.tunstile_services.StationeryTurnstileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("stationery-machine")
public class StationeryTurnstileController {
    @Autowired
    StationeryTurnstileService stationeryTurnstileService;

    @PostMapping("in-ride")
    public ResponseMessage inRide(@Valid @RequestBody RideModel rideModel) throws BadRequestException, NotFoundException {
        return stationeryTurnstileService.inRide(rideModel);
    }
    @PostMapping("out-ride")
    public ResponseMessage outRide(@Valid @RequestBody RideModel rideModel) throws BadRequestException, NotFoundException {
        return stationeryTurnstileService.outRide(rideModel);
    }

    @GetMapping("station-name")
    public String getMyStationName(Principal principal) throws BadRequestException {
        return stationeryTurnstileService.getMyStationName(principal);
    }
}
