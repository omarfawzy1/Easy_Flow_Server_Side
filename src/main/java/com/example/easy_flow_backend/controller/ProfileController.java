package com.example.easy_flow_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("profile")
public class ProfileController {

    @GetMapping("index")
    public String index(){
        return "profile/index";
    }
}
