package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.view.RideView;
import org.springframework.web.bind.annotation.RequestBody;

public interface StationeryTurnstileService {
    String inRide(RideView rideView) throws BadRequestException;
    String outRide(RideView rideView) throws BadRequestException;
}
