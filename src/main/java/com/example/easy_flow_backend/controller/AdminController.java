package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.service.AdminService;
import com.example.easy_flow_backend.view.AddLineView;
import com.example.easy_flow_backend.view.LineView;
import com.example.easy_flow_backend.view.PassagnerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("passenger/{username}")
    public Passenger getPassenger(@PathVariable String username) throws NotFoundException {
        return adminService.getPassenger(username);
    }

    @DeleteMapping("passenger/{username}")
    public ResponseEntity<String> deletePassenger(@PathVariable String username) throws NotFoundException {
        return adminService.deletePassenger(username);
    }

    @PutMapping("passenger/active/{username}")
    public ResponseEntity<String> passengerStatus(@PathVariable String username) throws NotFoundException {
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
    public ResponseEntity<String> deleteLine(@PathVariable String id) throws NotFoundException {
        return adminService.deleteLine(id);
    }

    @PostMapping("line")
    public ResponseEntity<String> addLine(@RequestBody AddLineView addLineView) {
        return adminService.addLine(addLineView);
    }


}
