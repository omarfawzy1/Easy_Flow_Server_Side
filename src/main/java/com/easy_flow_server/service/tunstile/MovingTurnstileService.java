package com.easy_flow_server.service.tunstile;

import com.easy_flow_server.dto.model.Pair;
import com.easy_flow_server.dto.view.MovingMachineView;
import com.easy_flow_server.error.NotFoundException;

import java.util.List;

public interface MovingTurnstileService extends TurnstileService {
    Pair<String, List<String>> getLineStations() throws NotFoundException;

    MovingMachineView findProjectedByUsername(String username) throws NotFoundException;

    List<MovingMachineView> getMachines();
}
