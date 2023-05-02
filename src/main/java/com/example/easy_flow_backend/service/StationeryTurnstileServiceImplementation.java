package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.StationaryTurnstileRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import com.example.easy_flow_backend.service.graph.GraphWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StationeryTurnstileServiceImplementation implements StationeryTurnstileService, TurnstileService {
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private StationaryTurnstileRepo stationaryTurnstileRepo;
    @Autowired
    private GraphWeightService graphWeightService;
    @Autowired
    WalletService walletService;

    @Autowired
    TicketService ticketService;

    @Override
    public ResponseMessage inRide(RideModel rideModel) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();
        if (machineUsername == null || machineUsername.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }


        if (tripRepo.existsByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending)) {
            throw new BadRequestException("You Can not make Ride as you have pending Request");
        } else if (!passengersRepo.existsByUsernameIgnoreCase(rideModel.getUsername())) {
            throw new BadRequestException("Passenger Not found!");
        } else if (!stationaryTurnstileRepo.existsByUsernameIgnoreCase(machineUsername)) {
            throw new BadRequestException("The machine user name is invalid!");
        }

        Passenger passenger = passengersRepo.findByUsernameIgnoreCase(rideModel.getUsername());

        StationaryTurnstile machine = stationaryTurnstileRepo.findUserByUsername(machineUsername);
        String ownerId = machine.getOwner().getId();

        double minPrice = ticketService.getMinPrice(ownerId);
        boolean can = walletService.canWithdraw(passenger.getWallet(), minPrice);
        if (can) {
            Trip pendingTrip = new Trip(passenger, machine, machine.getStation().getStationName(), TransportationType.METRO, rideModel.getTime(), Status.Pending);
            tripRepo.save(pendingTrip);
        } else {
            return new ResponseMessage("No enough money", HttpStatus.OK);
        }


        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage outRide(RideModel rideModel) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();
        if (!tripRepo.existsByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending)) {
            throw new BadRequestException("Failed No Binding trips");
        } else if (!stationaryTurnstileRepo.existsByUsernameIgnoreCase(machineUsername)) {
            throw new BadRequestException("The machine Username not found!");
        }

        StationaryTurnstile machine = stationaryTurnstileRepo.findUserByUsername(machineUsername);

        Trip trip = tripRepo.findByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending);

        String ownerId = machine.getOwner().getId();
        System.out.println(trip.getStartStation()+" "+machine.getStation().getStationName());
        double weight = graphWeightService.getWeight(ownerId, trip.getStartStation(), machine.getStation().getStationName());

        long totalTime = rideModel.getTime().getTime() - trip.getStartTime().getTime();

        double price = ticketService.getPrice(ownerId, weight, totalTime);

        boolean can = walletService.withdraw(trip.getPassenger().getWallet(), price);
        if (can) {
            trip.setPrice(price);
            trip.setEndTurnstile(machine);
            trip.setEndTime(rideModel.getTime());
            trip.setStatus(Status.Closed);
            tripRepo.save(trip);
        } else {
            return new ResponseMessage("Can not End Ride", HttpStatus.OK);
        }

        return new ResponseMessage("Success", HttpStatus.OK);
    }
}
