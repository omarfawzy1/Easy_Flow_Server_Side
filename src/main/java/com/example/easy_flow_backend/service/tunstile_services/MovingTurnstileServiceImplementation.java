package com.example.easy_flow_backend.service.tunstile_services;

import com.example.easy_flow_backend.dto.Models.Pair;
import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.dto.Views.MovingMachineView;
import com.example.easy_flow_backend.entity.MovingTurnstile;
import com.example.easy_flow_backend.entity.Turnstile;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.MovingTurnstileRepo;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.service.TokenValidationService;
import com.example.easy_flow_backend.service.UserService;
import com.example.easy_flow_backend.service.payment_services.TripService;
import com.example.easy_flow_backend.service.graph_services.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovingTurnstileServiceImplementation implements MovingTurnstileService {
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private MovingTurnstileRepo movingTurnstileRepo;
    @Autowired
    private GraphService graphService;
    @Autowired
    TripService tripService;

    @Autowired
    TokenValidationService tokenValidationService;
    @Autowired
    UserService userService;

    private void inRideValidation(RideModel rideModel, String machineUsername) throws BadRequestException {
        if (!tokenValidationService.validatePassengerToken(rideModel.getToken(), rideModel.getUsername()) || !tokenValidationService.validateGenerationTime(rideModel.getGenerationTime(), rideModel.getUsername()))
            throw new BadRequestException("Illegal QR");

        //validate authentication
        if (machineUsername == null || machineUsername.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }

        //validate passenger existence
        if (!passengersRepo.existsByUsernameIgnoreCase(rideModel.getUsername())) {
            throw new BadRequestException("Passenger Not found!");
        }
        //validate that machine is movingTurn stile machine
        if (!movingTurnstileRepo.existsByUsernameIgnoreCase(machineUsername)) {
            throw new BadRequestException("Access Denied!");
        }


    }

    @Override
    public ResponseMessage inRide(RideModel rideModel) throws BadRequestException, NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();
        inRideValidation(rideModel, machineUsername);

        return tripService.makeTrip(rideModel, machineUsername);
    }


    @Override
    public Pair<String, List<String>> getLineStations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();

        MovingTurnstile machine = movingTurnstileRepo.findUserByUsername(machineUsername);

        String lineId = machine.getLine().getId();

        return new Pair<>(machine.getLine().getName(), graphService.getOrderedStationOfLine(lineId));
    }

    @Override
    public MovingMachineView findProjectedByUsername(String username) throws NotFoundException {
        if (!movingTurnstileRepo.existsByUsername(username))
            throw new NotFoundException("machine not exist!");
        return movingTurnstileRepo.findProjectedByUsername(username);
    }

    @Override
    public List<MovingMachineView> getMachines() {
        return movingTurnstileRepo.findAllProjectedBy(MovingMachineView.class);
    }

    @Override
    public List<Turnstile> getAllMachines() {
        return new ArrayList<>(movingTurnstileRepo.findAll());
    }

    @Override
    public ResponseMessage deletMachine(String username) throws NotFoundException {
        if (!movingTurnstileRepo.existsByUsername(username)) {
            throw new NotFoundException("Invalid username!");
        }
        return userService.deleteUser(username);
    }

}
