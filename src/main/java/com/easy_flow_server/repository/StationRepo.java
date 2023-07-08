package com.easy_flow_server.repository;

import com.easy_flow_server.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepo extends JpaRepository<Station, String> {
    boolean existsByStationNameIgnoreCase(String stationName);

    @Query("select s from Station s where upper(s.stationName) = upper(?1)")
    Station findByStationNameIgnoreCase(String stationName);

    boolean existsByStationName(String stationName);

    Station findByStationName(String stationName);

    <T> List<T> findAllBy(Class<T> type);
}
