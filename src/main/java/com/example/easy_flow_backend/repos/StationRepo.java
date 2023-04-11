package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepo extends JpaRepository<Station, String> {
    Station findByStationName(String stationName);
}
