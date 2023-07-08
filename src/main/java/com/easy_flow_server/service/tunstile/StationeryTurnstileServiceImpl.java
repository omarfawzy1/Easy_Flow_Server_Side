package com.easy_flow_server.service.tunstile;

import com.easy_flow_server.dto.model.ForgetTicketModel;
import com.easy_flow_server.dto.model.RideModel;
import com.easy_flow_server.dto.view.StationeryMachineView;
import com.easy_flow_server.entity.*;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repository.PassengerRepo;
import com.easy_flow_server.repository.StationaryTurnstileRepo;
import com.easy_flow_server.repository.TripRepo;
import com.easy_flow_server.service.UserService;
import com.easy_flow_server.service.payment.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StationeryTurnstileServiceImpl implements StationeryTurnstileService {
    private final TripRepo tripRepo;
    private final PassengerRepo passengerRepo;
    private final StationaryTurnstileRepo stationaryTurnstileRepo;
    private final TripService tripService;
    private final UserService userService;

    public StationeryTurnstileServiceImpl(TripRepo tripRepo, PassengerRepo passengerRepo, StationaryTurnstileRepo stationaryTurnstileRepo, TripService tripService, UserService userService) {
        this.tripRepo = tripRepo;
        this.passengerRepo = passengerRepo;
        this.stationaryTurnstileRepo = stationaryTurnstileRepo;
        this.tripService = tripService;
        this.userService = userService;
    }

    private void rideValidation(String machineUsername) throws BadRequestException {

        //validate authintication
        if (machineUsername == null || machineUsername.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }

        // validate that the machine is Stationary turnstile machine
        if (!stationaryTurnstileRepo.existsByUsernameIgnoreCase(machineUsername)) {
            throw new BadRequestException("Access Denied");
        }

    }


    @Override
    public ResponseMessage inRide(RideModel rideModel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();

        try {
            rideValidation(machineUsername);
        } catch (BadRequestException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return tripService.makePendingTrip(rideModel, machineUsername);
    }


    @Override
    public ResponseMessage outRide(RideModel rideModel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();
        //validate
        try {
            rideValidation(machineUsername);
        } catch (BadRequestException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return tripService.makeFinalTrip(rideModel, machineUsername);
    }


    @Override
    public String getMyStationName(Principal principal) throws BadRequestException {
        if (principal == null)
            throw new BadRequestException("The principal must not be null, may be you are not authenticated");

        StationaryTurnstile stationaryTurnstile = stationaryTurnstileRepo.findUserByUsername(principal.getName());
        if (stationaryTurnstile == null)
            throw new BadRequestException("Machine not found");
        Station station = stationaryTurnstile.getStation();
        if (station == null) return null;
        return station.getStationName();
    }

    @Override
    public StationeryMachineView findProjectedByUsername(String username) throws NotFoundException {
        if (!stationaryTurnstileRepo.existsByUsername(username))
            throw new NotFoundException("Machine not found");
        return stationaryTurnstileRepo.findProjectedByUsername(username);
    }

    @Override
    public List<StationeryMachineView> getMachines() {
        return stationaryTurnstileRepo.findAllProjectedBy();
    }

    @Override
    public ResponseMessage outRideForgetTicket(ForgetTicketModel forgetTicketModel) {
        Passenger passenger = passengerRepo.findPassengerByPhoneNumberAndPin(forgetTicketModel.getPhoneNumber(), forgetTicketModel.getPin());
        if (passenger == null)
            return new ResponseMessage("Invalid phone number or pin", HttpStatus.BAD_REQUEST);
        Trip trip = tripRepo.outRideForgetTicket(passenger.getId());
        if (trip == null)
            return new ResponseMessage("No pending trips found", HttpStatus.NOT_FOUND);

        RideModel rideModel = new RideModel
                (trip.getId(), trip.getStartStation(), null, forgetTicketModel.getTime());
        return outRide(rideModel);

    }

    @Override
    public List<Turnstile> getAllMachines() {
        return new ArrayList<>(stationaryTurnstileRepo.findAll());
    }

    @Override
    public ResponseMessage deleteMachine(String username) {
        if (!stationaryTurnstileRepo.existsByUsername(username)) {
            return new ResponseMessage("Invalid Username", HttpStatus.NOT_FOUND);

        }
        return userService.deleteUser(username);
    }


}
