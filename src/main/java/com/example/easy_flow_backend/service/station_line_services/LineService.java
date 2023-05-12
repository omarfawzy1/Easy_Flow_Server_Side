package com.example.easy_flow_backend.service.station_line_services;

import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.LiveWithStationsView;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.OwnerRepo;
import com.example.easy_flow_backend.repos.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LineService {
    @Autowired
    LineRepo lineRepo;
    @Autowired
    OwnerRepo ownerRepo;

    @Autowired
    StationRepo stationRepo;
    @Autowired
    StationService stationService;

    public List<LineView> getAllLines() {
        return lineRepo.findBy(LineView.class);
    }

    public LineView getLine(String id) throws NotFoundException {
        LineView line = lineRepo.findProjectedById(id);
        if (line == null)
            throw new NotFoundException("The Line Not Exist");
        return line;
    }

    public boolean deleteLine(String name) {
        Line line = lineRepo.findByName(name);
        if (line == null)
            return false;
        for (MovingTurnstile temp : line.getMovingTurnstiles())
            temp.setLine(null);
        lineRepo.delete(line);
        return true;
    }

    public boolean addLine(AddLineModel addLineModel) {
//        if (lineRepo.existsByNameIgnoreCase(line.getName()))
//            return new ResponseEntity<>("The Line Already Exists", HttpStatus.NOT_FOUND);
        Optional<Owner> owner = ownerRepo.findById(addLineModel.getOwnerId());

        if (owner.isEmpty()) {
            return false;
        }
        Line tmpLine = new Line(addLineModel.getLineName()
                , TransportationType.valueOf(addLineModel.getType()), owner.get());

        try {
            lineRepo.save(tmpLine);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }

    public Line addStationToLine(Station station, Line line) {
        station = stationService.addStation(station);
        line.addStation(station);
        return lineRepo.save(line);
    }


    public List<Object> getOwnerDetails(String id) {
        return lineRepo.getOwnerDetails(id);
    }

    public LiveWithStationsView getLineDetails(String name) {
        return lineRepo.getLineDetails(name);
    }

}
