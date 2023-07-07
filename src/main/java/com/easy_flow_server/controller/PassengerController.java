package com.easy_flow_server.controller;

import com.easy_flow_server.dto.Models.SubscriptionModel;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.dto.Models.PinModel;
import com.easy_flow_server.dto.Models.UpdatePassword;
import com.easy_flow_server.dto.Models.UpdateProfileModel;
import com.easy_flow_server.dto.Views.PassagnerDetails;
import com.easy_flow_server.dto.Views.SubscriptionView;
import com.easy_flow_server.dto.Views.TripId;
import com.easy_flow_server.dto.Views.TripView;
import com.easy_flow_server.service.passenger_services.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("passenger")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

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

    @PutMapping("subscribe/{owner-name}/{plan-name}")
    public ResponseMessage renewSubscription(Principal principal, @PathVariable("owner-name") String ownerName, @PathVariable("plan-name") String planName) {
        return passengerService.renewSubscription(principal.getName(), ownerName, planName);
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
