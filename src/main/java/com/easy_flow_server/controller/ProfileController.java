package com.easy_flow_server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("profile")
public class ProfileController {

    @GetMapping("index")
    public String index(){
        return "profile/index";
    }
}
