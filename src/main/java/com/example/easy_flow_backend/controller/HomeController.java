package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.HomeService;
import com.example.easy_flow_backend.dto.Models.RegisterModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HomeController {
    @Autowired
    private HomeService homeService;

    /*
    TODO 3/2/2023
        Passenger API
            History Return trips
            Request Privilege
     */
    @GetMapping("index")
    public String index() {
        return "index";
    }
    /*
    * Don't Remove (blocks post request to log in)
    */
//    @PostMapping(value = "login")
//    public String login() {
//        return "Login";
//    }
    @PostMapping(value = "Register")
    public ResponseMessage Register(@Valid @RequestBody RegisterModel registerModel) throws NotFoundException {
      return   homeService.Register(registerModel);
    }
}
