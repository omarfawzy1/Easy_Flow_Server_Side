package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.dto.Views.TripId;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.service.graph_services.GraphWeightService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepo tripRepo;

    private final StationaryTurnstileRepo stationaryTurnstileRepo;
    final
    WalletService walletService;
    private final GraphWeightService graphWeightService;
    private final MovingTurnstileRepo movingTurnstileRepo;
    private final TicketService ticketService;
    private final PassengersRepo passengersRepo;
    private final SubscriptionService subscriptionService;
    private final SubscriptionRepo subscriptionRepo;

    public TripServiceImpl(TripRepo tripRepo, StationaryTurnstileRepo stationaryTurnstileRepo, WalletService walletService, GraphWeightService graphWeightService, MovingTurnstileRepo movingTurnstileRepo, TicketService ticketService, PassengersRepo passengersRepo, SubscriptionService subscriptionService, SubscriptionRepo subscriptionRepo) {
        this.tripRepo = tripRepo;
        this.stationaryTurnstileRepo = stationaryTurnstileRepo;
        this.walletService = walletService;
        this.graphWeightService = graphWeightService;
        this.movingTurnstileRepo = movingTurnstileRepo;
        this.ticketService = ticketService;
        this.passengersRepo = passengersRepo;
        this.subscriptionService = subscriptionService;
        this.subscriptionRepo = subscriptionRepo;
    }

    private void makeOpenTrips(int numOfTrips, String passengerUsername) throws NotFoundException {

        Passenger passenger = passengersRepo.findByUsernameIgnoreCase(passengerUsername);
        if (passenger == null)
            throw new NotFoundException("Passenger Not found");
        List<Trip> trips = new ArrayList<>();
        for (int i = 0; i < numOfTrips; i++) {
            Trip trip = new Trip(passenger, Status.Open);
            trips.add(trip);
        }
        tripRepo.saveAll(trips);

    }

    @Override
    public List<TripId> getOpenTrips(int numOfTrips, String passengerUsername) throws NotFoundException {
        List<TripId> trips = tripRepo.findAllByPassengerUsernameAndStatus(passengerUsername, Status.Open, TripId.class);
        TripId pendingTrip = tripRepo.findByPassengerUsernameAndStatus(passengerUsername, Status.Pending, TripId.class);
        if (pendingTrip != null)
            trips.add(0, pendingTrip);
        if (trips.size() == numOfTrips) return trips;
        else if (trips.size() > numOfTrips) {
            return trips.subList(0, numOfTrips);
        } else {
            makeOpenTrips(numOfTrips - trips.size(), passengerUsername);
            trips = tripRepo.findAllByPassengerUsernameAndStatus(passengerUsername, Status.Open, TripId.class);
            return trips;
        }
    }

    void validateTrip(Trip trip) throws NotFoundException {
        if (trip == null || trip.getStatus() != Status.Open) {
            throw new NotFoundException("The ticket is invalid");
        }
    }

    @Override
    public ResponseMessage makePendingTrip(RideModel rideModel, String machineUsername) {

        Trip trip = tripRepo.findById(rideModel.getTripId(), Trip.class);
        try {
            validateTrip(trip);

        } catch (NotFoundException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        Passenger passenger = trip.getPassenger();

        StationaryTurnstile machine = stationaryTurnstileRepo.findUserByUsername(machineUsername);


        String ownerId = machine.getOwner().getId();

        double minPrice = ticketService.getMinPrice(ownerId) * rideModel.getCompanionCount();
        Subscription bestSubscription = subscriptionService.getbestSubscription(subscriptionRepo.getSubscriptionByPassengerIdAndPlanOwnerName(passenger.getId(), machine.getOwner().getName()), rideModel);

        if (bestSubscription != null) {
            minPrice -= minPrice * bestSubscription.getPlan().getDiscountRate();
        }
        boolean can = walletService.canWithdraw(passenger.getWallet(), minPrice);

        if (can) {
            trip.setStartTurnstile(machine);
            trip.setStartStation(machine.getStation().getStationName());
            trip.setTransportationType(TransportationType.METRO);
            trip.setStartTime(rideModel.getTime());
            trip.setStatus(Status.Pending);
            trip.setCompanionCount(rideModel.getCompanionCount());

            tripRepo.save(trip);
        } else {
            return new ResponseMessage("No enough money", HttpStatus.OK);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    //For Stationery TurnStile
    void validateMakeFinalTrip(Trip trip, String inOwnerId) throws NotFoundException, BadRequestException {
        if (trip == null || trip.getStatus() != Status.Pending) {
            throw new NotFoundException("The ticket is invalid");
        }
        Owner outOwner = trip.getStartTurnstile().getOwner();
        if (!inOwnerId.equals(outOwner.getId())) {
            throw new BadRequestException("Can not ending Trip, Not for the Same owner!");
        }
    }

    @Override
    public ResponseMessage makeFinalTrip(RideModel rideModel, String machineUsername) {

        StationaryTurnstile machine = stationaryTurnstileRepo.findUserByUsername(machineUsername);

        Trip trip = tripRepo.findById(rideModel.getTripId(), Trip.class);

        String ownerId = machine.getOwner().getId();

        try {
            validateMakeFinalTrip(trip, ownerId);
        } catch (NotFoundException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        rideModel.setCompanionCount(trip.getCompanionCount());

        double weight = 0;
        try {
            weight = graphWeightService.getOwnerWeight(ownerId, trip.getStartStation(), machine.getStation().getStationName());
        } catch (NotFoundException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        long totalTime = rideModel.getTime().getTime() - trip.getStartTime().getTime();
        double price = ticketService.getPrice(ownerId, weight, totalTime) * trip.getCompanionCount();
        Subscription bestSubscription = subscriptionService.getbestSubscription(subscriptionRepo.getSubscriptionByPassengerIdAndPlanOwnerName(trip.getPassenger().getId(), machine.getOwner().getName()), rideModel);

        if (bestSubscription != null) {
            price -= price * bestSubscription.getPlan().getDiscountRate();
        }
        boolean can = walletService.withdraw(trip.getPassenger().getWallet(), price);
        if (can) {
            trip.setPrice(price);
            trip.setEndTurnstile(machine);
            trip.setEndTime(rideModel.getTime());
            trip.setStatus(Status.Closed);
            trip.setEndStation(machine.getStation().getStationName());
            if (bestSubscription != null) {
                bestSubscription.withdrawTrips(rideModel.getCompanionCount());
                subscriptionRepo.save(bestSubscription);
            }
            tripRepo.save(trip);
        } else {
            return new ResponseMessage("Can not end trip", HttpStatus.BAD_REQUEST);
        }

        return new ResponseMessage("Success", HttpStatus.OK);

    }

    public ResponseMessage makeTrip(RideModel rideModel, String machineUsername) {
        Trip trip = tripRepo.findById(rideModel.getTripId(), Trip.class);
        try {
            validateTrip(trip);
        } catch (NotFoundException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        Passenger passenger = trip.getPassenger();

        MovingTurnstile machine = movingTurnstileRepo.findUserByUsername(machineUsername);

        String lineId = machine.getLine().getId();
        String ownerId = machine.getLine().getOwner().getId();
        String startStation = rideModel.getStartStation();
        String endStation = rideModel.getEndStation();

        double weight = 0;
        try {
            weight = graphWeightService.getLineWeight(lineId, startStation, endStation);
        } catch (NotFoundException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
        }


        double price = ticketService.getPrice(ownerId, lineId, weight, 0L) * rideModel.getCompanionCount();
        Subscription bestSubscription = subscriptionService.getbestSubscription(subscriptionRepo.getSubscriptionByPassengerIdAndPlanOwnerName(passenger.getId(), machine.getOwner().getName()), rideModel);

        if (bestSubscription != null) {
            price -= price * bestSubscription.getPlan().getDiscountRate();
        }

        boolean can = walletService.withdraw(passenger.getWallet(), price);

        if (can) {
            if (bestSubscription != null) {
                bestSubscription.withdrawTrips(rideModel.getCompanionCount());
                subscriptionRepo.save(bestSubscription);
            }
            Trip closedTrip = new Trip(passenger, machine, machine, rideModel.getTime(), rideModel.getTime(), TransportationType.BUS, price, Status.Closed, startStation, endStation, rideModel.getCompanionCount());
            closedTrip.setId(trip.getId());
            closedTrip.setEndTime(rideModel.getTime());
            tripRepo.save(closedTrip);

        } else {
            return new ResponseMessage("No enough money", HttpStatus.OK);
        }

        return new ResponseMessage("Success", HttpStatus.OK);

    }

}
