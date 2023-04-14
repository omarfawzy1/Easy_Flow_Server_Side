package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.GraphModel;
import com.example.easy_flow_backend.dto.Views.MachineView;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.service.graph.GraphEdgeService;
import com.example.easy_flow_backend.service.graph.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class AdminServiceImplementation implements AdminService {
    @Autowired
    private PassengersRepo passengerRepo;
    @Autowired
    private LineRepo lineRepo;
    @Autowired
    private OwnerRepo ownerRepo;
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private UserRepositry userRepositry;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private TurnstileRepo turnstileRepo;
    @Autowired
    private GraphService graphService;
    @Autowired
    private GraphEdgeService graphEdgeService;


    @Override
    public List<LineView> getAllLines() {
        return lineRepo.findAllProjectedBy();
    }

    @Override
    public List<PassagnerDetails> getAllPassangers() {
        return passengerRepo.findAllProjectedBy();
    }

    @Override
    public Passenger getPassenger(String username) throws NotFoundException {

        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        return passenger;
    }

    @Override
    public ResponseMessage deletePassenger(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        passengerRepo.deleteByUsernameIgnoreCase(username);
        return new ResponseMessage("Passenger deleted Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseMessage passengerStatus(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");

        passenger.setActive(!passenger.isActive());
        passengerRepo.save(passenger);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public LineView getLine(String id) throws NotFoundException {
        LineView line = lineRepo.findProjectedById(id);
        if (line == null)
            throw new NotFoundException("The Line Not Exist");

        return line;
    }

    @Override
    public ResponseMessage deleteLine(String id) throws NotFoundException {
        if (!lineRepo.existsById(id))
            throw new NotFoundException("The Line Does Not Exist");
        lineRepo.deleteById(id);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage addLine(AddLineModel addLineModel) throws BadRequestException {
//        if (lineRepo.existsByNameIgnoreCase(line.getName()))
//            return new ResponseEntity<>("The Line Already Exists", HttpStatus.NOT_FOUND);
        Optional<Owner> owner = ownerRepo.findById(addLineModel.getOwnerId());

        if (!owner.isPresent()) {
        throw new BadRequestException("The Owner Not Exists");
        }
        Line tmpLine = new Line(addLineModel.getLineName(), addLineModel.getPrice(), owner.get());

        try {
            lineRepo.save(tmpLine);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public int getAllPassangersCount() {
        return (int) passengerRepo.count();
    }

    @Override
    public int getAllPassangersCountWithType(String type) {
        return passengerRepo.getAllPassangersCountWithType(type);
    }

    @Override
    public long getRevenue(TimePeriod timePeriod) {
        Optional<Long> revenue = tripRepo.getRevenue(timePeriod.getStart(), timePeriod.getEnd());
        if (revenue.isEmpty())
            return 0;
        return revenue.get();
    }

    @Override
    public long getRevenueAvg(TimePeriod timePeriod) {
        Optional<Long> revenue = tripRepo.getRevenueAvg(timePeriod.getStart(), timePeriod.getEnd());
        if (revenue.isEmpty())
            return 0;
        return revenue.get();
    }

    @Override
    public long getRevenueAvgByPassenger(TimePeriod timePeriod, String passengerId) {
        Optional<Long> revenue = tripRepo.getRevenueAvgByPassenger(timePeriod.getStart(), timePeriod.getEnd(), passengerId);
        if (revenue.isEmpty())
            return 0;
        return revenue.get();
    }

    @Override
    public int getNegativePassengerCount() {
        return tripRepo.getNegativePassengerCount();
    }

    @Override
    public int getBelowThresholdCount(long threshold) {
        return tripRepo.getBelowThresholdCount(threshold);
    }

    @Override
    public Object getTurnstilesStatus() {
        Map<String, Integer> result = new HashMap<>();
        return userRepositry.getTurnstilesStatus();
    }

    @Override
    public int getTripInStationCount(TimePeriod timePeriod, String stationName) {
        return tripRepo.getTripInStationCount(timePeriod.getStart(), timePeriod.getEnd(), stationName);
    }

    @Override
    public long getTripAvgByTimeUnitForBusLine(TimePeriod timePeriod, Long timeUnit, String lineId) {
        long sum=0;
        int count=0;
        Long start = timePeriod.getStart().getTime();
        Long end = timePeriod.getEnd().getTime();
        while(start+timeUnit<=end){
            sum+= tripRepo.getTripAvgByTimeUnitForBusLine(new Date(start), new Date(start+timeUnit),
                    lineId);
            start+=timeUnit;
            count++;
        }
        return sum/count;
    }

    @Override
    public List<Object> getPeekHours(TimePeriod timePeriod, String lineId, TransportationType transportType,
                                     int peekNumber) {
        List<Object> result =tripRepo.getPeekHours(timePeriod.getStart(), timePeriod.getEnd(), lineId,
                transportType);
        if(result.size()<peekNumber){
            return result;
        }
        return result.subList(0,peekNumber);
    }

    @Override
    public int getTransactionCount() {
        return (int) transactionRepo.count();
    }

    @Override
    public int getTripCount() {
        return (int) tripRepo.count();
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepo.findAll();
    }
    @Override
    public List<MachineView> getAllMachines(){
        return turnstileRepo.findAllProjectedBy();
    }

    @Override
    public boolean addGraph(GraphModel graphModel) {
        // TODO Graph Model Validation
        if(!graphService.addGraph(graphModel.getGraph())){
            return false;
        }
        return graphEdgeService.addEdges(graphModel.getGraphEdgeList());
    }

    @Override
    public List<GraphEdge> getGraph(String ownerId, String lineId) {
        return graphEdgeService.getEdges(graphService.getLineGraph(ownerId,lineId));
    }
}
