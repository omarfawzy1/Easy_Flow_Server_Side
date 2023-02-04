package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.service.AdminService;
import com.example.easy_flow_backend.view.PassagnerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
