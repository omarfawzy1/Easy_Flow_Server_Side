package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.Pair;
import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Station;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.tunstile_services.MovingTurnstileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("moving-machine")
public class MovingTurnStileController {
    @Autowired
    MovingTurnstileService movingTurnstileService;

    @PostMapping("in-ride")
    public ResponseMessage inRide(@Valid @RequestBody RideModel rideModel) throws BadRequestException, NotFoundException, NotFoundException {
        return movingTurnstileService.inRide(rideModel);
    }

    @GetMapping("line")
    public Pair<String, List<String>> getLineStations() throws NotFoundException {
        return movingTurnstileService.getLineStations();
    }

}
