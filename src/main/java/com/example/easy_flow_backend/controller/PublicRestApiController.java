package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.repos.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/public")
@CrossOrigin
public class PublicRestApiController {
    @Autowired
    private UserRepositry userRepositry;

    public PublicRestApiController() {
    }
    //Available to all authenticated users
    @GetMapping("test")
    public String test1() {
        return "API Test ";
    }
    //Available to all managers
    @GetMapping("management/reports")
    public String test2() {
        return "Some report data";
    }

    //Available to ROLE_ADMIN
    @GetMapping("admin/users")
    public List<User> users() {
        return this.userRepositry.findAll();
    }

    @GetMapping("passenger/test")
    public String passengerTest(){
        return "have a nice travel :)";
    }

    @GetMapping("admin/test")
    public String adminTest(){
        return "Welcome Admin :)";
    }
}
