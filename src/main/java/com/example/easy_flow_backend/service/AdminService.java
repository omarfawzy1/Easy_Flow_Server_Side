package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.dto.Models.AddLineModel;
import com.example.easy_flow_backend.dto.Views.LineView;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Models.TimePeriod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {

     List<LineView> getAllLines();

    List<PassagnerDetails> getAllPassangers();


    Passenger getPassenger(String username) throws NotFoundException;

    ResponseEntity<String> deletePassenger(String username) throws NotFoundException;

    ResponseEntity<String> passengerStatus(String username) throws NotFoundException;

    LineView getLine(String id) throws NotFoundException;

    ResponseEntity<String> deleteLine(String id) throws NotFoundException;

    ResponseEntity<String> addLine(AddLineModel addLineModel);

    int getAllPassangersCount();

    int getAllPassangersCountWithType(String type);

    long getRevenue(TimePeriod timePeriod);

    long getRevenueAvg(TimePeriod timePeriod);

    long getRevenueAvgByPassenger(TimePeriod timePeriod, String passengerId);

    int getNegativePassengerCount();

    int getBelowThresholdCount(long threshold);

   Object getTurnstilesStatus();
}
