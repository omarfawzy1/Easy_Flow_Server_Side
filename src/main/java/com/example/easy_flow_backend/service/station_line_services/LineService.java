package com.example.easy_flow_backend.service.station_line_services;

import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.LiveWithStationsView;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.OwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {
    @Autowired
    LineRepo lineRepo;
    @Autowired
    OwnerRepo ownerRepo;

    @Autowired
    StationService stationService;

    public List<LineView> getAllLines() {
        return lineRepo.findBy(LineView.class);
    }

    public Line getLineById(String id) throws NotFoundException {
        Line line = lineRepo.findById(id, Line.class);
        if (line == null)
            throw new NotFoundException("The Line Not Exist");

        return line;
    }

    public Line getLineByName(String name) throws NotFoundException {
        Line line = lineRepo.findByName(name, Line.class);
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

    public ResponseMessage addLine(AddLineModel addLineModel) {


        Owner owner = ownerRepo.findByName(addLineModel.getOwnerName());

        if (owner == null) {
            return new ResponseMessage("Owner Not Found", HttpStatus.NOT_FOUND);
        }

        if (lineRepo.existsByName(addLineModel.getLineName())) {
            return new ResponseMessage("Line name is used", HttpStatus.CONFLICT);
        }
        Line tmpLine = new Line(addLineModel.getLineName()
                , TransportationType.valueOf(addLineModel.getType()), owner);

        lineRepo.save(tmpLine);
        return new ResponseMessage("Success", HttpStatus.OK);
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

    public List<Line> getLinesByOwnerId(String ownerId) {
        return lineRepo.findAllByOwnerId(ownerId, Line.class);
    }

    public void saveLine(Line line) {
        lineRepo.save(line);
    }
}
