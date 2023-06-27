package com.example.easy_flow_backend.service.tunstile_services;

import com.example.easy_flow_backend.dto.Models.Pair;
import com.example.easy_flow_backend.dto.Views.MachineView;
import com.example.easy_flow_backend.dto.Views.MovingMachineView;
import com.example.easy_flow_backend.dto.Views.StationeryMachineView;
import com.example.easy_flow_backend.entity.Station;
import com.example.easy_flow_backend.error.NotFoundException;

import java.util.List;

public interface MovingTurnstileService extends TurnstileService {
    Pair<String, List<String>> getLineStations() throws NotFoundException;

    MovingMachineView findProjectedByUsername(String username) throws NotFoundException;

    List<MovingMachineView> getMachines();
}
