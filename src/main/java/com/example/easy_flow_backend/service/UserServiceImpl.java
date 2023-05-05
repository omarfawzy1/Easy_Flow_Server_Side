package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    UserRepositry userRepositry;

    public ResponseMessage flipUserActive(String username) throws NotFoundException {
        if (!userRepositry.existsByUsername(username)) {
            throw new NotFoundException("Invalid Username!");
        }
        User user = userRepositry.findUserByUsername(username);
        user.setActive(!user.isActive());
        userRepositry.save(user);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

}
