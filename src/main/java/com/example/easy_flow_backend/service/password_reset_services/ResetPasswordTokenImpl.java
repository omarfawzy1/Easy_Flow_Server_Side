package com.example.easy_flow_backend.service.password_reset_services;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.ResetPasswordToken;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.ResetPasswordTokenRepo;
import com.example.easy_flow_backend.service.MailServiceImpl;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class ResetPasswordTokenImpl implements ResetPasswordTokenService {
    @Autowired
    private ResetPasswordTokenRepo resetPasswordTokenRepo;
    @Autowired
    MailServiceImpl mailService;


    private static final int TOKEN_LENGTH = 7;

    @Override
    public void createPasswordResetTokenForUser(Passenger passenger, String token) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(passenger, token);

        resetPasswordTokenRepo.save(resetPasswordToken);
    }


    @Override
    public String getRandomToken(int length) {

        final boolean useLetters = true;
        final boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
    @Override
    public ResponseMessage sendResetPasswordToken(Passenger passenger) {

        String token = getRandomToken(TOKEN_LENGTH);
        createPasswordResetTokenForUser(passenger, token);

        String subject = "EasyFlow Reset password\n";

        String link = "http://" + InetAddress.getLoopbackAddress().getHostAddress() + "/reset/" + token;

        String text = "Please click on the link below to change your password.\n" + link + "\nTake care The link will be expire after 5 minutes.\n";

        try {
            mailService.sendMail(passenger.getEmail(), subject, text);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseMessage("Sorry Something wrong,please try again letter.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseMessage("Check your mail.", HttpStatus.OK);
    }


}
