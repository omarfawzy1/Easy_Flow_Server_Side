package com.example.easy_flow_backend.service.station_line_services;

import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.entity.Station;
import com.example.easy_flow_backend.entity.StationaryTurnstile;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StationService {
    @Autowired
    private StationRepo stationRepo;
    @Autowired
    private LineRepo lineRepo;

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


    public ResponseMessage deleteStation(String name) throws BadRequestException {
        Station temp=stationRepo.findByStationName(name);
        if(temp==null)throw new BadRequestException("there is no station with this name.");
        Set<Line> lines=temp.getLines();
        for(Line line:lines){
            line.removeStation(temp);
            lineRepo.save(line);
        }
        for(StationaryTurnstile stationaryTurnstile:temp.getStationaryTurnstiles())
            stationaryTurnstile.setStation(null);
        stationRepo.delete(temp);
        return new ResponseMessage("Success", HttpStatus.OK);
    }
}

