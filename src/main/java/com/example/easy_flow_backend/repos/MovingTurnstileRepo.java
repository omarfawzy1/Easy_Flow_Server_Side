package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.MovingTurnstile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface MovingTurnstileRepo extends TurnstileRepo<MovingTurnstile>{


}
