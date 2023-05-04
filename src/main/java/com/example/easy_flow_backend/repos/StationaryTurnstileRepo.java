package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.dto.Views.StationeryMachineView;
import com.example.easy_flow_backend.entity.StationaryTurnstile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationaryTurnstileRepo extends TurnstileRepo<StationaryTurnstile> {
    boolean existsByUsernameIgnoreCase(String username);

    StationeryMachineView findProjectedByUsername(String username);

    List<StationeryMachineView> findAllProjectedBy();

}
