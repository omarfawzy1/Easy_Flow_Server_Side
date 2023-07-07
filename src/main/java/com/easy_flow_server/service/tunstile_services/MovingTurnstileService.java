package com.easy_flow_server.service.tunstile_services;

import com.easy_flow_server.dto.Models.Pair;
import com.easy_flow_server.dto.Views.MovingMachineView;
import com.easy_flow_server.error.NotFoundException;

import java.util.List;

public interface MovingTurnstileService extends TurnstileService {
    Pair<String, List<String>> getLineStations() throws NotFoundException;

    MovingMachineView findProjectedByUsername(String username) throws NotFoundException;

    List<MovingMachineView> getMachines();
}
