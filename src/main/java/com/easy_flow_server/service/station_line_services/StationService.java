package com.easy_flow_server.service.station_line_services;

import com.easy_flow_server.entity.Line;
import com.easy_flow_server.entity.Station;
import com.easy_flow_server.entity.StationaryTurnstile;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.LineRepo;
import com.easy_flow_server.repos.StationRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StationService {
    private final StationRepo stationRepo;
    private final LineRepo lineRepo;

    public StationService(StationRepo stationRepo, LineRepo lineRepo) {
        this.stationRepo = stationRepo;
        this.lineRepo = lineRepo;
    }

    public boolean exists(String stationName) {
        return stationRepo.existsByStationNameIgnoreCase(stationName);
    }

    public Station getStation(String stationName) {
        return stationRepo.findByStationNameIgnoreCase(stationName);
    }

    public Station addStation(Station station) {
        if (!exists(station.getStationName()))
            return stationRepo.save(station);
        return getStation(station.getStationName());
    }


    public ResponseMessage deleteStation(String name) {
        Station temp = stationRepo.findByStationName(name);
        if (temp == null)
            return new ResponseMessage("There is no station with this name", HttpStatus.NOT_FOUND);
        Set<Line> lines = temp.getLines();
        for (Line line : lines) {
            line.removeStation(temp);
            lineRepo.save(line);
        }
        for (StationaryTurnstile stationaryTurnstile : temp.getStationaryTurnstiles())
            stationaryTurnstile.setStation(null);
        stationRepo.delete(temp);
        return new ResponseMessage("Success", HttpStatus.OK);
    }
}

