package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.service.PassengerService;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Views.TripView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
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
    public List<TripView> getMytrips() throws BadRequestException {
        return passengerService.getMytrips();
    }

    @GetMapping("history/{date}")
    public List<TripView> getMytrips(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws BadRequestException {

        return passengerService.getMytrips(date);
    }

    @GetMapping("profile")
    public PassagnerDetails getMyProfile() throws BadRequestException {

        return passengerService.getMyProfile();
    }
    // Dummy
    @PutMapping("recharge/{amount}")
    public void recharge(@PathVariable double amount, Principal principal){
        passengerService.rechargePassenger(principal.getName(), amount);
    }

}
