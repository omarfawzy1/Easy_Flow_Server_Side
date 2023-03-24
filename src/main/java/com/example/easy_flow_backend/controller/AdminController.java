package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.TransportationType;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.AdminService;
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


    @GetMapping("passengers")
    public List<PassagnerDetails> getAllPassengers() {
        return adminService.getAllPassangers();
    }
    @GetMapping("passenger/{username}")
    public Passenger getPassenger(@PathVariable String username) throws NotFoundException {
        return adminService.getPassenger(username);
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

    @GetMapping("line/{id}")
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
    @GetMapping("passengers/count")
    public int getPassengersCount() {
        return adminService.getAllPassangersCount();
    }
    @GetMapping("passengers/count/{type}")
    public int getPassengersCountWithType(@PathVariable String type) {
        return adminService.getAllPassangersCountWithType(type);
    }
    @GetMapping("revenue")
    public long getRevenue(@Valid @RequestBody TimePeriod timePeriod) {
        return adminService.getRevenue(timePeriod);
    }
    @GetMapping("revenueAvg")
    public long getRevenueAvg(@Valid @RequestBody TimePeriod timePeriod) {
        return adminService.getRevenueAvg(timePeriod);
    }
    @GetMapping("{passengerId}/revenueAvg")
    public long getRevenueAvgByPassenger(@Valid @RequestBody TimePeriod timePeriod, @PathVariable String passengerId) {
        return adminService.getRevenueAvgByPassenger(timePeriod, passengerId);
    }
    @GetMapping("passenger/negativeBalanceCount")
    public int getNegativePassengerCount() {
        return adminService.getNegativePassengerCount();
    }
    @GetMapping("passenger/belowThresholdCount/{threshold}")
    public int getBelowThresholdCount(@PathVariable long threshold) {
        return adminService.getBelowThresholdCount(threshold);
    }
    @GetMapping("turnstile/status")
    public Object getTurnstilesStatus() {
        return adminService.getTurnstilesStatus();
    }
    @GetMapping("station/passengersCount/{stationName}")
    public Object getTripInStationCount(@Valid @RequestBody TimePeriod timePeriod, @PathVariable String stationName) {
        return adminService.getTripInStationCount(timePeriod, stationName);
    }
    @GetMapping("line/tripAvg/{lineId}/{timeUnit}")
    public Object getTripAvgByTimeUnitForBusLine(@Valid @RequestBody TimePeriod timePeriod,
                                                 @PathVariable String timeUnit,
                                                 @PathVariable String lineId) throws ParseException {
        return adminService.getTripAvgByTimeUnitForBusLine(timePeriod, stringToMilleSecond(timeUnit), lineId);
    }
    @GetMapping("line/peek/{lineId}/{transportType}/{peekNumber}")
    public List<Object> getPeekHours(
            @Valid @RequestBody TimePeriod timePeriod,
            @PathVariable String lineId,
            @PathVariable TransportationType transportType,
            @PathVariable int peekNumber) {
        return adminService.getPeekHours(timePeriod, lineId, transportType ,peekNumber);
    }
    @GetMapping("transaction/count")
    public int getTransactionCount() {
        return adminService.getTransactionCount();
    }
    @GetMapping("trip/count")
    public int getTripCount() {
        return adminService.getTripCount();
    }
    public long stringToMilleSecond(String date) throws ParseException {
        long result=0;
        result+=Long.parseLong(date.substring(0,4)) * 31556952000L;
        result+=Long.parseLong(date.substring(5,7)) * 2629746000L;
        result+=Long.parseLong(date.substring(8,10)) * 86400000L;
        result+=Long.parseLong(date.substring(11,13)) * 3600000L;
        result+=Long.parseLong(date.substring(14,16)) * 60000L;
        result+=Long.parseLong(date.substring(17,19)) * 1000L;
        return result;
    }
//ToDo
//
//View system-wide statistics:
//Admin can view the total number of transactions in the system (e.g. trip purchases, wallet recharges).
//Admin can view the system logs to monitor any errors or issues in the system.

}
