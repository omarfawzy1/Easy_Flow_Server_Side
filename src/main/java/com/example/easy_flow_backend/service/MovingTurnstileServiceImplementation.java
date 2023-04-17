package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.MovingTurnstileRepo;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import com.example.easy_flow_backend.service.graph.GraphWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovingTurnstileServiceImplementation implements MovingTurnstileService {
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private MovingTurnstileRepo movingTurnstileRepo;

    @Autowired
    private GraphWeightService graphWeightService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TicketService ticketService;

    @Override
    public ResponseMessage inRide(RideModel rideModel) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();
        if (machineUsername == null || machineUsername.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }

        if (!passengersRepo.existsByUsernameIgnoreCase(rideModel.getUsername())) {
            throw new BadRequestException("Passenger Not found!");
        } else if (!movingTurnstileRepo.existsByUsernameIgnoreCase(machineUsername)) {
            throw new BadRequestException("The machine username is invalid!");
        }
        Passenger passenger = passengersRepo.findByUsernameIgnoreCase(rideModel.getUsername());
        MovingTurnstile machine = movingTurnstileRepo.findUserByUsername(machineUsername);

        String lineId = machine.getLine().getId();
        String ownerId = machine.getLine().getOwner().getId();
        String startStation = rideModel.getStartStation();
        String endStation = rideModel.getEndStation();

        double weight = graphWeightService.getWeight(ownerId, lineId, startStation, endStation);

        double price = ticketService.getPrice(ownerId, lineId, weight, 0L);

        boolean can = walletService.withdraw(passenger.getWallet(), price);
        if (can) {

            Trip closedTrip = new Trip(passenger, machine, machine, rideModel.getTime(), rideModel.getTime(),
                    TransportationType.BUS, price, Status.Closed, startStation, endStation);
            tripRepo.save(closedTrip);
        } else {
            return new ResponseMessage("No enough money", HttpStatus.OK);

        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    // TODO implement this function
    @Override
    public List<Station> getLineStations(String machineId) {
        return null;
    }
}
