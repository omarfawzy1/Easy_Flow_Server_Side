package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.AdminService;
import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {

    /*
    Todo 3/2/2023
        Users Page :
            Username, Email, Type(Military, Student, etc), Gender, Phone Number
     */

    /*
    Todo server side
        Analytics Module
        Payment Module
     */
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
    //ToDo
    @GetMapping("turnstile/status")
    public Object getTurnstilesStatus() {
        return adminService.getTurnstilesStatus();
    }
//ToDo{Admin can view the total number of passengers on a specific line (Bus) and at a specific station for a given period .
//Admin can view the total number of passengers on a specific station for a given period.
//Admin can view the average number of passengers on a specific line (Bus) and at a specific station for a given period.
//Admin can view the peak hours for a specific line and at a specific station.
//Admin can view the trend of passenger numbers over time for a specific line and at a specific station.
//
//View system-wide statistics:
//Admin can view the total number of transactions in the system (e.g. ticket purchases, wallet recharges).
//Admin can view the system logs to monitor any errors or issues in the system.}




}
