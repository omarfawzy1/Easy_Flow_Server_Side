package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.service.StationeryTurnstileService;
import com.example.easy_flow_backend.dto.Models.RideModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("stationery-machine")
public class StationeryTurnstileController {
    @Autowired
    StationeryTurnstileService stationeryTurnstileService;

    @PostMapping("in-ride")
    public String inRide(@Valid @RequestBody RideModel rideModel) throws BadRequestException {

        return stationeryTurnstileService.inRide(rideModel);

    }

    @PostMapping("out-ride")
    public String outRide(@Valid @RequestBody RideModel rideModel) throws BadRequestException {
        return stationeryTurnstileService.outRide(rideModel);
    }
}
