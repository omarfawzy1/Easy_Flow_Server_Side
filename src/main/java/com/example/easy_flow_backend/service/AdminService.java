package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
     ResponseEntity<List<Passenger>>getAllPassangers();
}
