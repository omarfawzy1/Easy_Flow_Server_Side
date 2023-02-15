package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.StationaryTurnstile;
import com.example.easy_flow_backend.entity.Status;
import com.example.easy_flow_backend.entity.Ticket;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.StationaryTurnstileRepo;
import com.example.easy_flow_backend.repos.TicketRepo;
import com.example.easy_flow_backend.view.RideView;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.Random;

@Service
public class StationeryTurnstileServiceImplementation implements StationeryTurnstileService {
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private StationaryTurnstileRepo stationaryTurnstileRepo;

    static public void validateRideRequest(@NotNull RideView rideView) throws BadRequestException {
        if (rideView.getMachineId() == null) {
            throw new BadRequestException("The machine id must not null");
        } else if (rideView.getUsername() == null) {
            throw new BadRequestException("Username not null");
        } else if (rideView.getTime() == null) {
            throw new BadRequestException("Time must not null");
        } else if (rideView.getMachineId().isEmpty()) {
            throw new BadRequestException("machine id must not Blank");
        } else if (rideView.getUsername().isEmpty()) {
            throw new BadRequestException("Username must not Blank");
        } else if (rideView.getTime().isAfter(LocalTime.now())) {
            throw new BadRequestException("The Date is invalid");
        }
    }


    @Override
    public String inRide(RideView rideView) throws BadRequestException {
        validateRideRequest(rideView);
        if (ticketRepo.existsByPassengerUsernameAndStatus(rideView.getUsername(), Status.Pending)) {
            throw new BadRequestException("You Can not make Ride as you have pending Request");
        } else if (!passengersRepo.existsByUsernameIgnoreCase(rideView.getUsername())) {
            throw new BadRequestException("Passenger Not found!");
        } else if (!stationaryTurnstileRepo.existsById(rideView.getMachineId())) {
            throw new BadRequestException("The machine Id is invalid!");
        }

        Passenger passenger = passengersRepo.findByUsernameIgnoreCase(rideView.getUsername());

        StationaryTurnstile machine = stationaryTurnstileRepo.findById(rideView.getMachineId()).get();
        //Todo check for minimum charge
        Ticket pendingTicket = new Ticket(passenger, machine.getStation(), new Date(),
                rideView.getTime(), Status.Pending);
        ticketRepo.save(pendingTicket);
        return "Success";
    }

    @Override
    public String outRide(RideView rideView) throws BadRequestException {
        validateRideRequest(rideView);

        if (!ticketRepo.existsByPassengerUsernameAndStatus(rideView.getUsername(), Status.Pending)) {
            throw new BadRequestException("Failed No Binding Tickets");
        } else if (!stationaryTurnstileRepo.existsById(rideView.getMachineId())) {
            throw new BadRequestException("The machine Id is invalid!");
        }

        StationaryTurnstile machine = stationaryTurnstileRepo.findById(rideView.getMachineId()).get();
        Ticket ticket = ticketRepo.findByPassengerUsernameAndStatus(rideView.getUsername(), Status.Pending);
        //Todo calc price

        Random rand = new Random();
        ticket.setPrice(rand.nextDouble());
        ticket.setEndStation(machine.getStation());
        ticket.setEndTime(LocalTime.now());
        ticket.setStatus(Status.Closed);
        ticketRepo.save(ticket);
        return "Success";
    }
}
