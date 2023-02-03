package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImplementation implements AdminService {
    @Autowired
    private PassengersRepo passengerRepo;


    public ResponseEntity<List<Passenger>> getAllPassangers() {
        List<Passenger> passengers = passengerRepo.findAll();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

}
