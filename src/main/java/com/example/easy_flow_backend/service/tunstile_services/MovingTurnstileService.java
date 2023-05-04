package com.example.easy_flow_backend.service.tunstile_services;

import com.example.easy_flow_backend.dto.Models.Pair;
import com.example.easy_flow_backend.dto.Views.MachineView;
import com.example.easy_flow_backend.dto.Views.MovingMachineView;
import com.example.easy_flow_backend.dto.Views.StationeryMachineView;

import java.util.List;

public interface MovingTurnstileService extends TurnstileService {
    Pair<String, List<String>> getLineStations();

    MovingMachineView findProjectedByUsername(String username);

    List<MovingMachineView> getMachines();
}
