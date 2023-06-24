package com.example.easy_flow_backend.service.password_reset_services;

import com.example.easy_flow_backend.entity.Passenger;

public interface ResetPasswordTokenService {
    void createPasswordResetTokenForUser(Passenger passenger, String token);

    String getRandomToken(int length);

    void sendMail(String to, String subject, String text);
}
