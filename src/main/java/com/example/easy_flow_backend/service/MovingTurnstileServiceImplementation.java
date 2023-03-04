package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.MovingTurnstileRepo;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.StationRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MovingTurnstileServiceImplementation implements MovingTurnstileService {
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private MovingTurnstileRepo movingTurnstileRepo;
    @Autowired //Todo Delete
    private StationRepo stationRepo;

    @Override
    public ResponseMessage inRide(RideModel rideModel) throws BadRequestException {
        if (!passengersRepo.existsByUsernameIgnoreCase(rideModel.getUsername())) {
            throw new BadRequestException("Passenger Not found!");
        } else if (!movingTurnstileRepo.existsById(rideModel.getMachineId())) {
            throw new BadRequestException("The machine Id is invalid!");
        }
        Passenger passenger = passengersRepo.findByUsernameIgnoreCase(rideModel.getUsername());
        MovingTurnstile machine = movingTurnstileRepo.findById(rideModel.getMachineId()).get();
        //Todo get Price,Get start Station
        //Todo check for the availability of money
        //trip closedtrip = new trip(passenger,)
        //trip closedtrip = new trip(passenger, stationRepo.findAll().get(0), stationRepo.findAll().get(1), new Date(), rideModel.getTime(), rideModel.getTime(), 0, Status.Closed);
        //tripRepo.save(closedtrip);
        return new ResponseMessage("Success", HttpStatus.OK);

    }
}
