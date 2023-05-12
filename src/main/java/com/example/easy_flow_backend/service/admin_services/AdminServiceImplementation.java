package com.example.easy_flow_backend.service.admin_services;

import com.example.easy_flow_backend.dto.Models.AddOwnerModel;
import com.example.easy_flow_backend.dto.Models.GraphModel;
import com.example.easy_flow_backend.dto.Views.*;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.service.UserService;
import com.example.easy_flow_backend.service.owner_services.OwnerService;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import com.example.easy_flow_backend.service.passenger_services.PassengerService;
import com.example.easy_flow_backend.service.graph_services.GraphEdgeService;
import com.example.easy_flow_backend.service.graph_services.GraphService;
import com.example.easy_flow_backend.service.station_line_services.StationService;
import com.example.easy_flow_backend.service.tunstile_services.MovingTurnstileService;
import com.example.easy_flow_backend.service.tunstile_services.StationeryTurnstileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private GraphService graphService;
    @Autowired
    private GraphEdgeService graphEdgeService;
    @Autowired
    private LineService lineService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private StationeryTurnstileService stationeryTurnstileService;
    @Autowired
    private MovingTurnstileService movingTurnstileService;
    @Autowired
    private UserService userService;
    @Autowired
    private StationService stationService;


    @Override
    public List<LineView> getAllLines() {
        return lineService.getAllLines();
    }

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
    public LineView getLine(String id) throws NotFoundException {
        return lineService.getLine(id);
    }

    @Override
    public ResponseMessage deleteLine(String name) throws NotFoundException {
        if (!lineService.deleteLine(name))
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
    public int getAllPassangersCount() {
        return passengerService.getAllPassangersCount();
    }

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
    public List<OwnerView> getAllOwners() {
        return ownerRepo.findALLBy(OwnerView.class);
    }


    @Override
    public boolean addGraph(GraphModel graphModel) {
        // TODO Graph Model Validation
        if (!graphService.addGraph(graphModel.getGraph())) {
            return false;
        }
        return graphEdgeService.addEdges(graphModel.getGraphEdgeList());
    }

    @Override
    public List<GraphEdge> getGraph(String ownerId, String lineId) {
        return graphEdgeService.getEdges(graphService.getLineGraph(ownerId, lineId));
    }

    @Override
    public ResponseMessage addOwner(AddOwnerModel addOwnerModel) throws BadRequestException {
        if (!ownerService.addOwner(addOwnerModel)) {
            throw new BadRequestException("The Owner Not Exists");
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public List<Object> getOwnerDetails(String ownerName) throws BadRequestException {
        return ownerService.getOwnerDetails(ownerName);

    }

    @Override
    public LiveWithStationsView getLineDetails(String name) {
        return lineService.getLineDetails(name);
    }


    @Override
    public OwnerViewDetails getOwner(String username) throws BadRequestException {
        OwnerViewDetails owner = ownerRepo.findByName(username, OwnerViewDetails.class);
        if (owner == null)
            throw new BadRequestException("The owner Not exist");
        return owner;
    }

    @Override
    public List<Turnstile> getAllMachines() {
        List<Turnstile> machines = new ArrayList<>(movingTurnstileService.getAllMachines());
        machines.addAll(stationeryTurnstileService.getAllMachines());
        return machines;
    }

    @Override
    public MovingMachineView getMovingMachine(String username) throws NotFoundException {
        return movingTurnstileService.findProjectedByUsername(username);
    }

    @Override
    public StationeryMachineView getStationMachine(String username) throws NotFoundException {

        return stationeryTurnstileService.findProjectedByUsername(username);
    }

    @Override
    public List<MovingMachineView> getMovingMachines() {
        return movingTurnstileService.getMachines();
    }

    @Override
    public List<StationeryMachineView> getStationMachines() {
        return stationeryTurnstileService.getMachines();
    }

    @Override
    public ResponseMessage deleteStation(String name) throws BadRequestException {
        return stationService.deleteStation(name);
    }

    @Override
    public ResponseMessage flipUserActive(String username) throws NotFoundException {
        return userService.flipUserActive(username);
    }

    @Override
    public ResponseMessage deleteStationeryMachine(String username) throws NotFoundException {
        return stationeryTurnstileService.deletMachine(username);
    }

    @Override
    public ResponseMessage deleteMovingMachine(String username) throws NotFoundException {
        return movingTurnstileService.deletMachine(username);
    }


    @Override
    public ResponseMessage deleteOwner(String username) throws BadRequestException {
        return ownerService.deleteOwner(username);
    }

}
