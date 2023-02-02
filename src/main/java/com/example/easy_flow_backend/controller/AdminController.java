package com.example.easy_flow_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
    @GetMapping("index")
    public String index(){
        return "admin/index";
    }
}
