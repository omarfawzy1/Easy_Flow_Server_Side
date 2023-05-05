package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.dto.Views.MachineView;
import com.example.easy_flow_backend.entity.StationaryTurnstile;
import com.example.easy_flow_backend.entity.Turnstile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface TurnstileRepo<T extends Turnstile> extends AbstractRepo<T> {

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
