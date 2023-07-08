package com.easy_flow_server.controller;

import com.easy_flow_server.dto.model.Pair;
import com.easy_flow_server.dto.model.RideModel;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.service.tunstile.MovingTurnstileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("moving-machine")
public class MovingTurnstileController {
    private final MovingTurnstileService movingTurnstileService;

    public MovingTurnstileController(MovingTurnstileService movingTurnstileService) {
        this.movingTurnstileService = movingTurnstileService;
    }

    @PostMapping("in-ride")
    public ResponseMessage inRide(@Valid @RequestBody RideModel rideModel) {
        return movingTurnstileService.inRide(rideModel);
    }

    @GetMapping("line")
    public Pair<String, List<String>> getLineStations() throws NotFoundException {
        return movingTurnstileService.getLineStations();
    }

}
