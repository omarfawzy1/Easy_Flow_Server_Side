package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.StationaryTurnstile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationaryTurnstileRepo extends JpaRepository<StationaryTurnstile, String> {
}