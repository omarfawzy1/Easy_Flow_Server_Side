package com.example.easy_flow_backend.service.admin_services;

import com.example.easy_flow_backend.dto.Models.GraphModel;
import com.example.easy_flow_backend.dto.Views.MachineView;
import com.example.easy_flow_backend.dto.Views.PassagnerBriefDetails;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import com.example.easy_flow_backend.service.passenger_services.PassengerService;
import com.example.easy_flow_backend.service.graph_services.GraphEdgeService;
import com.example.easy_flow_backend.service.graph_services.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImplementation implements AdminService {
    @Autowired
    private PassengersRepo passengerRepo;
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
    @Autowired
    private LineService lineService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private AnalysisService analysisService;



    @Override
    public List<LineView> getAllLines() {return lineService.getAllLines();}

    @Override
    public List<PassagnerBriefDetails> getAllPassangers() {
        return passengerService.getAllPassangers();
    }

    @Override
    public PassagnerDetails getPassengerDetails(String username) throws NotFoundException {
        return passengerService.getPassengerDetails(username);
    }

    @Override
    public Passenger getPassenger(String username) throws NotFoundException {
        return passengerService.getPassenger(username);
    }

    @Override
    public ResponseMessage deletePassenger(String username) throws NotFoundException {
        return passengerService.deletePassenger(username);
    }

    @Override
    public ResponseMessage passengerStatus(String username) throws NotFoundException {
        return passengerService.passengerStatus(username);
    }

    @Override
    public LineView getLine(String id) throws NotFoundException {
        return lineService.getLine(id);
    }

    @Override
    public ResponseMessage deleteLine(String id) throws NotFoundException {
        if (!lineService.deleteLine(id))
            throw new NotFoundException("The Line Does Not Exist");
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage addLine(AddLineModel addLineModel) throws BadRequestException {
        if (!lineService.addLine(addLineModel)) {
            throw new BadRequestException("The Owner Not Exists");
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public int getAllPassangersCount() { return passengerService.getAllPassangersCount(); }

    @Override
    public int getAllPassangersCountWithType(String type) {
        return passengerService.getAllPassangersCountWithType(type);
    }

    @Override
    public long getRevenue(TimePeriod timePeriod) {
        return analysisService.getRevenue(timePeriod);
    }

    @Override
    public long getRevenueAvg(TimePeriod timePeriod) {
        return analysisService.getRevenueAvg(timePeriod);
    }

    @Override
    public long getRevenueAvgByPassenger(TimePeriod timePeriod, String passengerId) {
        return analysisService.getRevenueAvgByPassenger(timePeriod, passengerId);
    }

    @Override
    public int getNegativePassengerCount() {
        return analysisService.getNegativePassengerCount();
    }

    @Override
    public int getBelowThresholdCount(long threshold) {
        return analysisService.getBelowThresholdCount(threshold);
    }

    @Override
    public Object getTurnstilesStatus() {
        return analysisService.getTurnstilesStatus();
    }

    @Override
    public int getTripInStationCount(TimePeriod timePeriod, String stationName) {
        return analysisService.getTripInStationCount(timePeriod, stationName);
    }

    @Override
    public long getTripAvgByTimeUnitForBusLine(TimePeriod timePeriod, Long timeUnit, String lineId) {
        return analysisService.getTripAvgByTimeUnitForBusLine(timePeriod, timeUnit, lineId);
    }

    @Override
    public List<Object> getPeekHours(TimePeriod timePeriod, String lineId, TransportationType transportType, int peekNumber) {
        return analysisService.getPeekHours(timePeriod, lineId, transportType, peekNumber);
    }

    @Override
    public int getTransactionCount() {
        return analysisService.getTransactionCount();
    }

    @Override
    public int getTripCount() {
        return analysisService.getTripCount();
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