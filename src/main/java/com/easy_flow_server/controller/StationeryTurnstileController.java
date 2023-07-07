package com.easy_flow_server.controller;

import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.dto.Models.ForgetTicketModel;
import com.easy_flow_server.dto.Models.RideModel;
import com.easy_flow_server.service.tunstile_services.StationeryTurnstileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("stationery-machine")
public class StationeryTurnstileController {
    private final StationeryTurnstileService stationeryTurnstileService;

    public StationeryTurnstileController(StationeryTurnstileService stationeryTurnstileService) {
        this.stationeryTurnstileService = stationeryTurnstileService;
    }

    @PostMapping("in-ride")
    public ResponseMessage inRide(@Valid @RequestBody RideModel rideModel) {
        return stationeryTurnstileService.inRide(rideModel);
    }

    @PostMapping("out-ride")
    public ResponseMessage outRide(@Valid @RequestBody RideModel rideModel) {
        return stationeryTurnstileService.outRide(rideModel);
    }


    @PostMapping("out-ride/forgetTicket")
    public ResponseMessage outRideForgetTicket(@Valid @RequestBody ForgetTicketModel forgetTicketModel) {
        return stationeryTurnstileService.outRideForgetTicket(forgetTicketModel);
    }

    @GetMapping("station-name")
    public String getMyStationName(Principal principal) throws BadRequestException {
        return stationeryTurnstileService.getMyStationName(principal);
    }
}
