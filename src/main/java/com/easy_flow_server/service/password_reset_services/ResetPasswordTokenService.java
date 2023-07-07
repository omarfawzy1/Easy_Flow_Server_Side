package com.easy_flow_server.service.password_reset_services;

import com.easy_flow_server.entity.Passenger;
import com.easy_flow_server.error.ResponseMessage;

public interface ResetPasswordTokenService {
    void createPasswordResetTokenForUser(Passenger passenger, String token);

    String getRandomToken(int length);

    ResponseMessage sendResetPasswordToken(Passenger passenger);
}
