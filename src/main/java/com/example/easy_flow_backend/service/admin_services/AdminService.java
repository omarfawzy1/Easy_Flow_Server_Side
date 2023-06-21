package com.example.easy_flow_backend.service.admin_services;

import com.example.easy_flow_backend.dto.Models.*;
import com.example.easy_flow_backend.dto.Views.*;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    List<LineView> getAllLines();

    List<PassagnerBriefDetails> getAllPassangers();

    PassagnerDetails getPassengerDetails(String username) throws NotFoundException;

    Passenger getPassenger(String username) throws NotFoundException;

    ResponseMessage deletePassenger(String username) throws NotFoundException;

    ResponseMessage deleteLine(String name) throws NotFoundException;

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

    List<OwnerView> getAllOwners();


//    boolean addGraph(GraphModel graphModel);

    List<GraphEdge> getGraph(String ownerId, String lineId) throws NotFoundException;

    ResponseMessage addOwner(AddOwnerModel addOwnerModel) throws BadRequestException;

    List<Object> getOwnerDetails(String ownerName) throws BadRequestException;

    LiveWithStationsView getLineDetails(String name);

    ResponseMessage deleteOwner(String username) throws BadRequestException;

    OwnerViewDetails getOwner(String username) throws BadRequestException;

    List<Turnstile> getAllMachines();

    MovingMachineView getMovingMachine(String username) throws NotFoundException;


    StationeryMachineView getStationMachine(String username) throws NotFoundException;

    List<MovingMachineView> getMovingMachines();

    List<StationeryMachineView> getStationMachines();

    ResponseMessage deleteStation(String name) throws BadRequestException;


    ResponseMessage flipUserActive(String username) throws NotFoundException;

    ResponseMessage deleteStationeryMachine(String username) throws NotFoundException;

    ResponseMessage deleteMovingMachine(String username) throws NotFoundException;

    ResponseMessage addTicket(TicketModel ticketModel) throws NotFoundException;

    void deleteTicket(String id);

    TicketView getTicket(String id);

    List<TicketView> getOwnerTickets(String name);

    List<TicketView> getLineTickets(String name);


    ResponseMessage setOwnerImage(String name, MultipartFile file) throws IOException;
}
