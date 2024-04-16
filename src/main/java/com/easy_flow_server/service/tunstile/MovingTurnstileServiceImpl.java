package com.easy_flow_server.service.tunstile;

import com.easy_flow_server.dto.model.Pair;
import com.easy_flow_server.dto.model.RideModel;
import com.easy_flow_server.dto.view.MovingMachineView;
import com.easy_flow_server.entity.MovingTurnstile;
import com.easy_flow_server.entity.Station;
import com.easy_flow_server.entity.Turnstile;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repository.MovingTurnstileRepo;
import com.easy_flow_server.service.UserService;
import com.easy_flow_server.service.graph.GraphService;
import com.easy_flow_server.service.payment.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovingTurnstileServiceImpl implements MovingTurnstileService {
    private final MovingTurnstileRepo movingTurnstileRepo;
    private final GraphService graphService;
    private final TripService tripService;

    private final UserService userService;

    public MovingTurnstileServiceImpl(MovingTurnstileRepo movingTurnstileRepo, GraphService graphService, TripService tripService, UserService userService) {
        this.movingTurnstileRepo = movingTurnstileRepo;
        this.graphService = graphService;
        this.tripService = tripService;
        this.userService = userService;
    }

    private void inRideValidation(String machineUsername) throws BadRequestException {

        //validate authentication
        if (machineUsername == null || machineUsername.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }

        //validate that machine is movingTurn stile machine
        if (!movingTurnstileRepo.existsByUsernameIgnoreCase(machineUsername)) {
            throw new BadRequestException("Access Denied!");
        }
    }
    @Override
    public ResponseMessage inRide(RideModel rideModel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();
        try {
            inRideValidation(machineUsername);
        } catch (BadRequestException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return tripService.makeTrip(rideModel, machineUsername);
    }


    @Override
    public Pair<String, List<String>> getLineStations() throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();

        MovingTurnstile machine = movingTurnstileRepo.findUserByUsername(machineUsername);
        if (machine.getLine() == null) return null;

        String lineName = machine.getLine().getName();
        List<Station> stations = graphService.getOrderedStationOfLine(lineName).getFirst();
        List<String> stationNames = new ArrayList<>();
        for (Station station : stations) {
            stationNames.add(station.getStationName());
        }
        return new Pair<>(machine.getLine().getName(), stationNames);
    }

    @Override
    public MovingMachineView findProjectedByUsername(String username) throws NotFoundException {
        if (!movingTurnstileRepo.existsByUsername(username))
            throw new NotFoundException("machine not found");
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
    public ResponseMessage deleteMachine(String username) {
        if (!movingTurnstileRepo.existsByUsername(username)) {
            return new ResponseMessage("machine not found", HttpStatus.NOT_FOUND);

        }
        return userService.deleteUser(username);
    }

}
