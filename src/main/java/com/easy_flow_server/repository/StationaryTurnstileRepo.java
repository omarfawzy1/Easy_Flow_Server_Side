package com.easy_flow_server.repository;

import com.easy_flow_server.dto.view.StationeryMachineView;
import com.easy_flow_server.entity.StationaryTurnstile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationaryTurnstileRepo extends TurnstileRepo<StationaryTurnstile> {
    boolean existsByUsernameIgnoreCase(String username);

    StationeryMachineView findProjectedByUsername(String username);

    List<StationeryMachineView> findAllProjectedBy();

}
