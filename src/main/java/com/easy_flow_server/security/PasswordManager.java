package com.easy_flow_server.security;

import com.easy_flow_server.dto.model.ResetPassword;
import com.easy_flow_server.entity.User;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordManager  {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    public boolean isValid(String password) {
        return (password.length() >= 8);
    }

    public boolean isValid(String password, String rePassword) {
        return (password.equals(rePassword) && isValid(password));
    }

    public ResponseMessage resetPassword(User user, ResetPassword resetPassword) {
        if (!isValid(resetPassword.getNewPassword(), resetPassword.getReNewPassword())) {
            return new ResponseMessage("Sorry the Password is invalid", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        try {
            userRepo.save(user);
        } catch (Exception ex) {
            return new ResponseMessage("Can not save the password!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

}
