package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.GraphModel;
import com.example.easy_flow_backend.dto.Views.MachineView;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.util.List;

public interface AdminService {

    List<LineView> getAllLines();

    List<PassagnerBriefDetails> getAllPassangers();

    PassagnerDetails getPassengerDetails(String username) throws NotFoundException;

    Passenger getPassenger(String username) throws NotFoundException;

    ResponseMessage deletePassenger(String username) throws NotFoundException;

    ResponseMessage passengerStatus(String username) throws NotFoundException;

    LineView getLine(String id) throws NotFoundException;

    ResponseMessage deleteLine(String id) throws NotFoundException;

    ResponseMessage addLine(AddLineModel addLineModel) throws BadRequestException;

    int getAllPassangersCount();

    int getAllPassangersCountWithType(String type);

    long getRevenue(TimePeriod timePeriod);

    long getRevenueAvg(TimePeriod timePeriod);

    long getRevenueAvgByPassenger(TimePeriod timePeriod, String passengerId);

    int getNegativePassengerCount();

    int getBelowThresholdCount(long threshold);

    Object getTurnstilesStatus();
    int getTripInStationCount(TimePeriod timePeriod, String stationName);
    long getTripAvgByTimeUnitForBusLine(TimePeriod timePeriod, Long timeUnit, String lineId);

    List<Object> getPeekHours(TimePeriod timePeriod, String lineId, TransportationType transportType, int peekNumber);

    int getTransactionCount();

    int getTripCount();

    List<Owner> getAllOwners();
     List<MachineView> getAllMachines();

    boolean addGraph(GraphModel graphModel);

    List<GraphEdge> getGraph(String ownerId, String lineId);
}
