package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.dto.Views.MovingMachineView;
import com.example.easy_flow_backend.entity.MovingTurnstile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovingTurnstileRepo extends TurnstileRepo<MovingTurnstile> {
    boolean existsByUsernameIgnoreCase(String username);

    MovingMachineView findProjectedByUsername(String username);

    <T> List<T> findAllProjectedBy(Class<T> type);
}
