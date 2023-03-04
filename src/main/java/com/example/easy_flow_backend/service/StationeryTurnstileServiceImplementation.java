package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.StationaryTurnstile;
import com.example.easy_flow_backend.entity.Status;
import com.example.easy_flow_backend.entity.Trip;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.StationaryTurnstileRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import com.example.easy_flow_backend.dto.Models.RideModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Random;

@Service
public class StationeryTurnstileServiceImplementation implements StationeryTurnstileService, TurnstileService {
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private StationaryTurnstileRepo stationaryTurnstileRepo;


    @Override
    public ResponseMessage inRide(RideModel rideModel) throws BadRequestException {
        if (tripRepo.existsByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending)) {
            throw new BadRequestException("You Can not make Ride as you have pending Request");
        } else if (!passengersRepo.existsByUsernameIgnoreCase(rideModel.getUsername())) {
            throw new BadRequestException("Passenger Not found!");
        } else if (!stationaryTurnstileRepo.existsById(rideModel.getMachineId())) {
            throw new BadRequestException("The machine Id is invalid!");
        }

        Passenger passenger = passengersRepo.findByUsernameIgnoreCase(rideModel.getUsername());

        StationaryTurnstile machine = stationaryTurnstileRepo.findById(rideModel.getMachineId()).get();
        //Todo check for minimum charge
        Trip pendingTrip = new Trip(passenger, machine, Date.valueOf("2020-2-2"), Status.Pending);
        tripRepo.save(pendingTrip);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage outRide(RideModel rideModel) throws BadRequestException {

        if (!tripRepo.existsByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending)) {
            throw new BadRequestException("Failed No Binding trips");
        } else if (!stationaryTurnstileRepo.existsById(rideModel.getMachineId())) {
            throw new BadRequestException("The machine Id is invalid!");
        }

        StationaryTurnstile machine = stationaryTurnstileRepo.findById(rideModel.getMachineId()).get();
        Trip trip = tripRepo.findByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending);
        //Todo calc price

        Random rand = new Random();
        trip.setPrice(rand.nextDouble());
        trip.setEndTurnstile(machine);
        trip.setEndTime(Date.valueOf("2010-2-2"));
        trip.setStatus(Status.Closed);
        tripRepo.save(trip);
        return new ResponseMessage("Success", HttpStatus.OK);
    }
}
