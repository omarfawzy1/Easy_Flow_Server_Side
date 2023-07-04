package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.*;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Views.SubscriptionView;
import com.example.easy_flow_backend.dto.Views.TripId;
import com.example.easy_flow_backend.dto.Views.TripView;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.passenger_services.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    @PutMapping("updateProfile")
    public ResponseMessage updateProfile(Principal principal, @Valid @RequestBody UpdateProfileModel profileModel) {
        return passengerService.updateProfile(principal, profileModel);
    }

    @PutMapping("password")
    public ResponseMessage updatePassword(Principal principal, @Valid @RequestBody UpdatePassword updatePassword) {
        return passengerService.updatePassword(principal.getName(), updatePassword);
    }
    // Dummy
    @PutMapping("recharge/{amount}")
    @Operation(summary = "Passenger Wallet Recharge", description = "Passenger Wallet Recharge")
    public void recharge(@PathVariable double amount, Principal principal) {
        passengerService.rechargePassenger(principal.getName(), amount);
    }

    @GetMapping("Trips")
    public List<TripId> getTrips(Principal principal) throws NotFoundException {
        return passengerService.getOpenTrips(5, principal.getName());
    }

    @PostMapping("subscribe")
    public ResponseMessage makeSubscription(@RequestBody SubscriptionModel subscriptionModel) {
        return passengerService.makeSubscription(subscriptionModel.getOwner_name(), subscriptionModel.getPlan_name());
    }

    @GetMapping("subscriptions")
    public List<SubscriptionView> getMySubscriptions() throws NotFoundException {
        return passengerService.getMySubscriptions();
    }

    @PutMapping("subscription/repurchase/{owner-name}/{plan-name}")
    public ResponseMessage reverseSubscriptionRepurchase(Principal principal, @PathVariable("owner-name") String ownerName, @PathVariable("plan-name") String planName) {
        return passengerService.reverseSubscriptionRepurchase(principal, ownerName, planName);
    }

    @PutMapping("set-pin")
    public ResponseMessage setPin(Principal principal, @Valid @RequestBody PinModel pinModel) {
        return passengerService.setPin(principal, pinModel.getPin());
    }

    @GetMapping("owner/image/{owner-name}")
    public byte[] getOwnerImage(@PathVariable("owner-name") String ownerName) throws NotFoundException {
        return passengerService.getOwnerImage(ownerName);
    }
}
