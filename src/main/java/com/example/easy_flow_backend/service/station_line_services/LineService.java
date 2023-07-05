package com.example.easy_flow_backend.service.station_line_services;

import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.LiveWithStationsView;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.OwnerRepo;
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
            throw new NotFoundException("The Line Not Exist");

        return line;
    }

    public Line getLineByName(String name) throws NotFoundException {
        Line line = lineRepo.findByName(name, Line.class);
        if (line == null)
            throw new NotFoundException("The Line Not Exist");
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
            throw new BadRequestException("Can not delete Owner");
        }
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
