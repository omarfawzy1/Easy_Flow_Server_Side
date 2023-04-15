package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Station;
import com.example.easy_flow_backend.repos.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    @Autowired
    StationRepo stationRepo;
    public boolean exists(String stationName){
        return stationRepo.existsByStationNameIgnoreCase(stationName);
    }

    public Station getStation(String stationName){
        return stationRepo.findByStationNameIgnoreCase(stationName);
    }

    public Station addStation(Station station){
        if(!exists(station.getStationName()))
            return stationRepo.save(station);
        return getStation(station.getStationName());
    }


}

