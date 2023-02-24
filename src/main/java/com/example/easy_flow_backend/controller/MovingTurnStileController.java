package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.Dto.Models.RideModel;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.service.MovingTurnstileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("moving-machine")
public class MovingTurnStileController {
    @Autowired
    MovingTurnstileService movingTurnstileService;

    @PostMapping("in-ride")
    public String inRide(@Valid @RequestBody RideModel rideModel) throws BadRequestException {
        return movingTurnstileService.inRide(rideModel);
    }


}
