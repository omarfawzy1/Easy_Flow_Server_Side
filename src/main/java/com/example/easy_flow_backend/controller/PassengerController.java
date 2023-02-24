package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.service.PassengerService;
import com.example.easy_flow_backend.Dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.Dto.Views.TicketView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("passenger")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @GetMapping("history")
    public List<TicketView> getMyTickets() throws BadRequestException {
        return passengerService.getMyTickets();
    }

    @GetMapping("history/{date}")
    public List<TicketView> getMyTickets(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws BadRequestException {

        return passengerService.getMyTickets(date);
    }

    @GetMapping("profile")
    public PassagnerDetails getMyProfile() throws BadRequestException {

        return passengerService.getMyProfile();
    }

}
