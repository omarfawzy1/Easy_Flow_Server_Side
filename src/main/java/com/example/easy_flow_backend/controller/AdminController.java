package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Models.GraphModel;
import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.MachineView;
import com.example.easy_flow_backend.dto.Views.PassagnerBriefDetails;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.admin_services.AdminService;
import com.example.easy_flow_backend.service.utils.Utility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("owners")
    public List<Owner> getALlOwners(){
         return adminService.getAllOwners();
    }
    @GetMapping("passengers")
    public List<PassagnerBriefDetails> getAllPassengers() {
        return adminService.getAllPassangers();
    }

    @GetMapping("passenger/{username}")
    public PassagnerDetails getPassenger(@PathVariable String username) throws NotFoundException {
        return adminService.getPassengerDetails(username);
    }

    @GetMapping("graph")
    public boolean addGraph(@Valid @RequestBody GraphModel graphModel){
        return adminService.addGraph(graphModel);
    }
    @GetMapping("graph/{ownerId}/{lineId}")
    public List<GraphEdge> getGraph(@PathVariable String ownerId, @PathVariable String lineId){
        return adminService.getGraph(ownerId, lineId);
    }

    @DeleteMapping("passenger/{username}")
    public ResponseMessage deletePassenger(@PathVariable String username) throws NotFoundException {
        return adminService.deletePassenger(username);
    }

    @PutMapping("passenger/active/{username}")
    public ResponseMessage passengerStatus(@PathVariable String username) throws NotFoundException {
        return adminService.passengerStatus(username);
    }

    @GetMapping("lines")
    public List<LineView> getAllLines() {
        return adminService.getAllLines();
    }

    @PostMapping("line/{id}")
    public LineView getLine(@PathVariable String id) throws NotFoundException {
        return adminService.getLine(id);
    }

    @DeleteMapping("line/{id}")
    public ResponseMessage deleteLine(@PathVariable String id) throws NotFoundException {
        return adminService.deleteLine(id);
    }

    @PostMapping("line")
    public ResponseMessage addLine(@Valid @RequestBody AddLineModel addLineModel) throws BadRequestException {
        return adminService.addLine(addLineModel);
    }
    @PostMapping("passengers/count")
    public int getPassengersCount() {
        return adminService.getAllPassangersCount();
    }
    @PostMapping("passengers/count/{type}")
    public int getPassengersCountWithType(@PathVariable String type) {
        return adminService.getAllPassangersCountWithType(type);
    }
    @PostMapping("revenue")
    public long getRevenue(@Valid @RequestBody TimePeriod timePeriod) {
        return adminService.getRevenue(timePeriod);
    }
    @PostMapping("revenueAvg")
    public long getRevenueAvg(@Valid @RequestBody TimePeriod timePeriod) {
        return adminService.getRevenueAvg(timePeriod);
    }
    @PostMapping("{passengerId}/revenueAvg")
    public long getRevenueAvgByPassenger(@Valid @RequestBody TimePeriod timePeriod, @PathVariable String passengerId) {
        return adminService.getRevenueAvgByPassenger(timePeriod, passengerId);
    }
    @PostMapping("passenger/negativeBalanceCount")
    public int getNegativePassengerCount() {
        return adminService.getNegativePassengerCount();
    }
    @PostMapping("passenger/belowThresholdCount/{threshold}")
    public int getBelowThresholdCount(@PathVariable long threshold) {
        return adminService.getBelowThresholdCount(threshold);
    }
    @PostMapping("turnstile/status")
    public Object getTurnstilesStatus() {
        return adminService.getTurnstilesStatus();
    }
    @PostMapping("station/passengersCount/{stationName}")
    public Object getTripInStationCount(@Valid @RequestBody TimePeriod timePeriod, @PathVariable String stationName) {
        return adminService.getTripInStationCount(timePeriod, stationName);
    }
    @PostMapping("line/tripAvg/{lineId}/{timeUnit}")
    public Object getTripAvgByTimeUnitForBusLine(@Valid @RequestBody TimePeriod timePeriod,
                                                 @PathVariable String timeUnit,
                                                 @PathVariable String lineId) throws ParseException {
        return adminService.getTripAvgByTimeUnitForBusLine(timePeriod, Utility.stringToMilleSecond(timeUnit), lineId);
    }
    @PostMapping("line/peek/{lineId}/{transportType}/{peekNumber}")
    public List<Object> getPeekHours(
            @Valid @RequestBody TimePeriod timePeriod,
            @PathVariable String lineId,
            @PathVariable TransportationType transportType,
            @PathVariable int peekNumber) {
        return adminService.getPeekHours(timePeriod, lineId, transportType ,peekNumber);
    }
    @PostMapping("transaction/count")
    public int getTransactionCount() {
        return adminService.getTransactionCount();
    }
    @PostMapping("trip/count")
    public int getTripCount() {
        return adminService.getTripCount();
    }

    @GetMapping("mahcines")
    public List<MachineView> getAllMachines(){
        return adminService.getAllMachines();
    }




//ToDo
//
//View system-wide statistics:
//Admin can view the total number of transactions in the system (e.g. trip purchases, wallet recharges).
//Admin can view the system logs to monitor any errors or issues in the system.

}
