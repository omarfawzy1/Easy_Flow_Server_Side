package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.service.passenger_services.PassengerService;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Views.TripView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("passenger")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @GetMapping("history")
    public List<TripView> getMytrips(Principal principal) throws BadRequestException {
        return passengerService.getMytrips(principal.getName());
    }

    @GetMapping("history/{date}")
    public List<TripView> getMytrips(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Principal principal) throws BadRequestException {

        return passengerService.getMytrips(date, principal.getName());
    }

    @GetMapping("profile")
    public PassagnerDetails getMyProfile() throws BadRequestException {
        return passengerService.getMyProfile();
    }
    // Dummy
    @PutMapping("recharge/{amount}")
    @Operation(summary = "Passenger Wallet Recharge", description = "Passenger Wallet Recharge")
    public void recharge(@PathVariable double amount, Principal principal){

        passengerService.rechargePassenger(principal.getName(), amount);
    }

}
