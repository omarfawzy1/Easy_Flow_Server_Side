package com.example.easy_flow_backend.service.tunstile_services;

import com.example.easy_flow_backend.dto.Models.Pair;

import java.util.List;

public interface MovingTurnstileService extends TurnstileService {
    Pair<String, List<String>> getLineStations();
}
