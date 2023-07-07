package com.easy_flow_server.repos;

import com.easy_flow_server.dto.Views.MovingMachineView;
import com.easy_flow_server.entity.MovingTurnstile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovingTurnstileRepo extends TurnstileRepo<MovingTurnstile> {
    boolean existsByUsernameIgnoreCase(String username);

    MovingMachineView findProjectedByUsername(String username);

    <T> List<T> findAllProjectedBy(Class<T> type);
}
