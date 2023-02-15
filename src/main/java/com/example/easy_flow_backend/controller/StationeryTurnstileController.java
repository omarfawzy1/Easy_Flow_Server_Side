package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.service.StationeryTurnstileService;
import com.example.easy_flow_backend.view.RideView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("machine")
public class StationeryTurnstileController {
    @Autowired
    StationeryTurnstileService stationeryTurnstileService;

    @PostMapping("in-ride")
    public String inRide(@RequestBody RideView rideView) throws BadRequestException {

        return stationeryTurnstileService.inRide(rideView);

    }

    @PostMapping("out-ride")
    public String outRide(@RequestBody RideView rideView) throws BadRequestException {
        return stationeryTurnstileService.outRide(rideView);
    }
}