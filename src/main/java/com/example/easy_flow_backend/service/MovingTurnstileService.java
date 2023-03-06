package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Station;

import java.util.List;

public interface MovingTurnstileService extends TurnstileService {


    List<Station> getLineStations(String machineId);
}
