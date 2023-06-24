package com.example.easy_flow_backend.security;

import com.example.easy_flow_backend.dto.Models.ResetPassword;
import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordManager  {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepositry userRepositry;

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
            userRepositry.save(user);
        } catch (Exception ex) {
            return new ResponseMessage("Can not save the password!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

}
