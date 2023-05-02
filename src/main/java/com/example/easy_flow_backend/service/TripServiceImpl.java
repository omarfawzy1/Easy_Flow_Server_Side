package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.MovingTurnstileRepo;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.StationaryTurnstileRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import com.example.easy_flow_backend.service.graph.GraphWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private StationaryTurnstileRepo stationaryTurnstileRepo;
    @Autowired
    WalletService walletService;
    @Autowired
    private GraphWeightService graphWeightService;
    @Autowired
    private MovingTurnstileRepo movingTurnstileRepo;
    @Autowired
    TicketService ticketService;

    @Override
    public ResponseMessage makePendingTrip(RideModel rideModel, String machineUsername) throws NotFoundException {

        Passenger passenger = passengerService.getPassenger(rideModel.getUsername());

        StationaryTurnstile machine = stationaryTurnstileRepo.findUserByUsername(machineUsername);


        String ownerId = machine.getOwner().getId();

        double minPrice = ticketService.getMinPrice(ownerId);

        boolean can = walletService.canWithdraw(passenger.getWallet(), minPrice);

        if (can) {
            Trip pendingTrip = new Trip(passenger, machine, machine.getStation().getStationName(), TransportationType.METRO, rideModel.getTime(), Status.Pending);
            tripRepo.save(pendingTrip);

            passengerService.updateLastGeneratedTime(passenger.getUsername(), rideModel.getGenerationTime());
        } else {
            return new ResponseMessage("No enough money", HttpStatus.OK);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    //For Stationery TurnStile
    @Override
    public ResponseMessage makeFinalTrip(RideModel rideModel, String machineUsername) {

        StationaryTurnstile machine = stationaryTurnstileRepo.findUserByUsername(machineUsername);

        Trip trip = tripRepo.findByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending);

        String ownerId = machine.getOwner().getId();

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

    public ResponseMessage makeTrip(RideModel rideModel, String machineUsername) throws NotFoundException {
        Passenger passenger = passengerService.getPassenger(rideModel.getUsername());

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

            passengerService.updateLastGeneratedTime(passenger.getUsername(), rideModel.getGenerationTime());
        } else {
            return new ResponseMessage("No enough money", HttpStatus.OK);
        }

        return new ResponseMessage("Success", HttpStatus.OK);

    }

}
