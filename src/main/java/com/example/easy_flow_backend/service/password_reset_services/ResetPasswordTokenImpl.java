package com.example.easy_flow_backend.service.password_reset_services;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.ResetPasswordToken;
import com.example.easy_flow_backend.repos.ResetPasswordTokenRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenImpl implements ResetPasswordTokenService {
    @Autowired
    private ResetPasswordTokenRepo resetPasswordTokenRepo;
    @Autowired
    private Environment env;
    @Autowired
    private JavaMailSender emailSender;
    @Override
    public void createPasswordResetTokenForUser(Passenger passenger, String token) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(passenger, token);
        resetPasswordTokenRepo.save(resetPasswordToken);
    }

    @Override
    public String getRandomToken(int length) {

        boolean useLetters = false;
        boolean useNumbers = true;
        String token = RandomStringUtils.random(length, useLetters, useNumbers);
        return token;
    }




    public void sendMail(String to, String subject, String text) {
        System.out.println(env.getProperty("spring.mail.username"));
        System.out.println(env.getProperty("spring.mail.password"));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@EasyFlow.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }


}
