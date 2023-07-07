package com.easy_flow_server.service.password_reset_services;

import com.easy_flow_server.entity.ResetPasswordToken;
import com.easy_flow_server.repos.ResetPasswordTokenRepo;
import com.easy_flow_server.service.MailServiceImpl;
import com.easy_flow_server.entity.Passenger;
import com.easy_flow_server.error.ResponseMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResetPasswordTokenImpl implements ResetPasswordTokenService {
    private final ResetPasswordTokenRepo resetPasswordTokenRepo;
    private final MailServiceImpl mailService;


    private static final int TOKEN_LENGTH = 7;

    public ResetPasswordTokenImpl(ResetPasswordTokenRepo resetPasswordTokenRepo, MailServiceImpl mailService) {
        this.resetPasswordTokenRepo = resetPasswordTokenRepo;
        this.mailService = mailService;
    }

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

        String link = "http://ec2-16-170-103-177.eu-north-1.compute.amazonaws.com/" + "/reset/" + token;

        String text = "Please click on the link below to change your password.\n" + link + "\nTake care The link will be expire after 5 minutes.\n";

        try {
            mailService.sendMail(passenger.getEmail(), subject, text);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseMessage("Sorry something wrong,please try again letter.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseMessage("Check your mail.", HttpStatus.OK);
    }

    //TODO BUG
    @Scheduled(cron = "0 0/5 * * * ?")
    private void removeExpiredTokens() {
        resetPasswordTokenRepo.deleteAllByExpiryDateAfter(new Date());
    }

}
