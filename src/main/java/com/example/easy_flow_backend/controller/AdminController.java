package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.service.AdminService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {

    /*
    Todo 3/2/2023
        Users Page :
            Username, Email, Type(Military, Student, etc), Gender, Phone Number
     */
    @GetMapping("get/users")
    public List<Passenger> getUsers(){
        return passengersRepo.findAll();
    }
    /*
    Todo server side
        Analytics Module
        Payment Module
     */
    @Autowired
    private AdminService adminService;
    @Autowired
    private PassengersRepo passengersRepo;

    @GetMapping("index")
    public String index(){
        return "";
    }
    @GetMapping("passengers")
    public ResponseEntity<List<Passenger>> getAllPassangers(){
        return adminService.getAllPassangers();
    }

}
