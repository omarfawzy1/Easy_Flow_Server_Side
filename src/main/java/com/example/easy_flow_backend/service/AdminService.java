package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.view.AddLineModel;
import com.example.easy_flow_backend.view.LineView;
import com.example.easy_flow_backend.view.PassagnerDetails;
import com.example.easy_flow_backend.view.TimePeriod;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
}
