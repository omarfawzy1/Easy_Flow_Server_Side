package com.easy_flow_server.service.station_line_services;

import com.easy_flow_server.dto.Models.AddLineModel;
import com.easy_flow_server.dto.Views.LineView;
import com.easy_flow_server.dto.Views.LiveWithStationsView;
import com.easy_flow_server.entity.*;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.LineRepo;
import com.easy_flow_server.repos.OwnerRepo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {
    private final LineRepo lineRepo;
    private final OwnerRepo ownerRepo;

    private final RedisTemplate<Object, Object> redisTemplate;


    private final StationService stationService;

    public LineService(LineRepo lineRepo, OwnerRepo ownerRepo, RedisTemplate<Object, Object> redisTemplate, StationService stationService) {
        this.lineRepo = lineRepo;
        this.ownerRepo = ownerRepo;
        this.redisTemplate = redisTemplate;
        this.stationService = stationService;
    }

    public List<LineView> getAllLines() {
        return lineRepo.findBy(LineView.class);
    }

    public Line getLineById(String id) throws NotFoundException {
        Line line = lineRepo.findById(id, Line.class);
        if (line == null)
            throw new NotFoundException("The line not exist");

        return line;
    }

    public Line getLineByName(String name) throws NotFoundException {
        Line line = lineRepo.findByName(name, Line.class);
        if (line == null)
            throw new NotFoundException("The line not exist");
        return line;
    }

    public boolean deleteLine(String name) throws BadRequestException {
        Line line = lineRepo.findByName(name);
        if (line == null)
            return false;
        for (MovingTurnstile temp : line.getMovingTurnstiles())
            temp.setLine(null);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(line.getOwner().getId()))) {
            redisTemplate.delete(line.getOwner().getId());
        }

        if (Boolean.TRUE.equals(redisTemplate.hasKey(line.getId()))) {
            redisTemplate.delete(line.getId());
        }
        try {
            lineRepo.delete(line);

        } catch (Exception ex) {
            throw new BadRequestException("Can not delete owner");
        }
        return true;
    }

    public ResponseMessage addLine(AddLineModel addLineModel) {


        Owner owner = ownerRepo.findByName(addLineModel.getOwnerName());

        if (owner == null) {
            return new ResponseMessage("Owner not found", HttpStatus.NOT_FOUND);
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

    public Line saveLine(Line line) {
        return lineRepo.save(line);
    }
}
