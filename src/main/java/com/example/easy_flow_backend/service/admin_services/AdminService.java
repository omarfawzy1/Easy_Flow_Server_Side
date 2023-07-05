package com.example.easy_flow_backend.service.admin_services;

import com.example.easy_flow_backend.dto.Models.*;
import com.example.easy_flow_backend.dto.Views.*;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Turnstile;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {

    List<LineView> getAllLines();

    List<PassagnerBriefDetails> getAllPassangers();

    PassagnerDetails getPassengerDetails(String username) throws NotFoundException;

    Passenger getPassenger(String username) throws NotFoundException;

    ResponseMessage deletePassenger(String username);

    ResponseMessage deleteLine(String name) ;

    ResponseMessage addLine(AddLineModel addLineModel);

    int getAllPassangersCount();

    int getPassengersCountWithPrivilege(String privilege);

    List<Object> getRevenue(TimePeriod timePeriod, String groupBy) throws BadRequestException;

    long getRevenueAvg(TimePeriod timePeriod);

    long getRevenueAvgByPassenger(TimePeriod timePeriod, String passengerName);

    int getNegativePassengerCount();

    int getBelowThresholdCount(long threshold);

    Object getTurnstilesStatus();

    int getTripInStationCount(TimePeriod timePeriod, String stationName);

    long getTripAvgByTimeUnitForBusLine(TimePeriod timePeriod, Long timeUnit, String lineName) throws BadRequestException;

    List<Object> getPeekHours(TimePeriod timePeriod, String lineName, int peekNumber);

    int getTransactionCount();

    int getTripCount();

    List<OwnerView> getAllOwners();


    GetGraphModel getGraph(String lineName) throws NotFoundException;

    ResponseMessage addOwner(AddOwnerModel addOwnerModel) ;

    List<Object> getOwnerDetails(String ownerName) throws BadRequestException;

    LiveWithStationsView getLineDetails(String name);

    ResponseMessage deleteOwner(String username) ;

    OwnerViewDetails getOwner(String username) throws BadRequestException;

    List<Turnstile> getAllMachines();

    MovingMachineView getMovingMachine(String username) throws NotFoundException;


    StationeryMachineView getStationMachine(String username) throws NotFoundException;

    List<MovingMachineView> getMovingMachines();

    List<StationeryMachineView> getStationMachines();

    ResponseMessage deleteStation(String name) ;

    ResponseMessage flipUserActive(String username) ;

    ResponseMessage deleteStationeryMachine(String username) ;

    ResponseMessage deleteMovingMachine(String username) ;

    ResponseMessage addTicket(TicketModel ticketModel) ;

    void deleteTicket(String id);

    TicketView getTicket(String id);

    List<TicketView> getOwnerTickets(String name);

    List<TicketView> getLineTickets(String name);


    ResponseMessage setOwnerImage(String name, MultipartFile file) ;

    ResponseMessage addPrivilege(String privilege);

    ResponseMessage deletePrivilege(String privilege);

    boolean addStationaryMachine(AddStationaryMachineModel addStationaryMachineModel) throws Exception;

    boolean addMovingMachineModel(AddMovingMachineModel addMovingMachineModel) throws NotFoundException, BadRequestException;

    ResponseMessage deletePassengerPrivilege(String username, String privilege);

    ResponseMessage addGraph(GraphModel graphModel);

    ResponseMessage updateMovingMachineLine(String username, String newLine);

    ResponseMessage updateStationaryMachineStation(String username, String newStation);

    List<String> getPrivileges();

    List<String> getPrivilegesForPassenger(String username) throws NotFoundException;

    List<StationView> getAllStation();

    ResponseMessage addPrivilegesToPassenger(String username, String privilege);

    List<Object> getTripPerHour(TimePeriod timePeriod);

    long getRevenue(TimePeriod timePeriod);
}
