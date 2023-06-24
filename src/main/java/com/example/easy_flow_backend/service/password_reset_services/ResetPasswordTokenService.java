package com.example.easy_flow_backend.service.password_reset_services;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.net.UnknownHostException;

public interface ResetPasswordTokenService {
    void createPasswordResetTokenForUser(Passenger passenger, String token);

    String getRandomToken(int length);

    ResponseMessage sendResetPasswordToken(Passenger passenger) ;
}
