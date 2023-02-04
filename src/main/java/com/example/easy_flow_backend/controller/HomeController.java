package com.example.easy_flow_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {
    /*
    TODO 3/2/2023
        Passenger API
            Register
            History Return Tickets
            Request Privilege
     */
    @GetMapping("index")
    public String index() {
        return "index";
    }
    @GetMapping("login")
    public String login() {return "login";}
}
