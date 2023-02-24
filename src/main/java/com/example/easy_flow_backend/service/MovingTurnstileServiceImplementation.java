package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.repos.MovingTurnstileRepo;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.StationRepo;
import com.example.easy_flow_backend.repos.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MovingTurnstileServiceImplementation implements MovingTurnstileService {
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private MovingTurnstileRepo movingTurnstileRepo;
    @Autowired //Todo Delete
    private StationRepo stationRepo;

    @Override
    public String inRide(RideModel rideModel) throws BadRequestException {
        if (!passengersRepo.existsByUsernameIgnoreCase(rideModel.getUsername())) {
            throw new BadRequestException("Passenger Not found!");
        } else if (!movingTurnstileRepo.existsById(rideModel.getMachineId())) {
            throw new BadRequestException("The machine Id is invalid!");
        }
        Passenger passenger = passengersRepo.findByUsernameIgnoreCase(rideModel.getUsername());
        MovingTurnstile machine = movingTurnstileRepo.findById(rideModel.getMachineId()).get();
        //Todo get Price,Get start Station
        //Todo check for the availability of money
        Ticket closedTicket = new Ticket(passenger, stationRepo.findAll().get(0), stationRepo.findAll().get(1), new Date(), rideModel.getTime(), rideModel.getTime(), 0, Status.Closed);
        ticketRepo.save(closedTicket);
        return "Success";

    }
}
