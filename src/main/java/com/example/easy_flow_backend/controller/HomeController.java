package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.RegisterModel;
import com.example.easy_flow_backend.dto.Models.ResetPassword;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.HomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.Map;

@RestController
@CrossOrigin
public class HomeController {
    @Autowired
    private HomeService homeService;

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
    public ResponseMessage Register(@Valid @RequestBody RegisterModel registerModel) {
        return homeService.Register(registerModel);
    }

    @PostMapping("reset")
    public ResponseMessage sendResetPasswordToken(@RequestBody Map<String, String> jsonBody) {
        return homeService.sendResetPasswordToken(jsonBody.get("email"));
    }

    @PostMapping("reset/{key}")
    public ResponseMessage resetPassword(@PathVariable("key") String key, @Valid @RequestBody ResetPassword newPassword) {
        return homeService.resetPassword(key, newPassword);
    }

}
