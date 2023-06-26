package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.RegisterModel;
import com.example.easy_flow_backend.dto.Models.ResetPassword;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.HomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

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
    public String index() throws UnknownHostException {
        return "index";
    }

    /*
     * Don't Remove (blocks post request to log in)
     */
    @PostMapping(value = "login")
    public String login() {
        return "Login";
    }

    @PostMapping(value = "Register")
    public ResponseMessage Register(@Valid @RequestBody RegisterModel registerModel) throws NotFoundException {
        return homeService.Register(registerModel);
    }

    @PostMapping("reset")
    public ResponseMessage sendResetPasswordToken(@RequestBody String email) throws NotFoundException {
        return homeService.sendResetPasswordToken(email);
    }

    @PostMapping("reset/{key}")
    public ResponseMessage resetPassword(@PathVariable("key") String key, @Valid @RequestBody ResetPassword newPassword) throws NotFoundException {
        return homeService.resetPassword(key, newPassword);
    }

}
