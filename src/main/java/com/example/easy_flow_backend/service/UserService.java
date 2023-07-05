package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.UserRepositry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepositry userRepositry;


    public UserService(UserRepositry userRepositry) {
        this.userRepositry = userRepositry;
    }

    public ResponseMessage flipUserActive(String username) {
        if (!userRepositry.existsByUsername(username)) {
            return new ResponseMessage("Invalid Username", HttpStatus.NOT_FOUND);
        }
        User user = userRepositry.findUserByUsername(username);
        user.setActive(!user.isActive());
        userRepositry.save(user);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    public ResponseMessage deleteUser(String username) {
        if (!userRepositry.existsByUsername(username)) {
            return new ResponseMessage("Invalid Username", HttpStatus.NOT_FOUND);

        }

        userRepositry.deleteByUsername(username);
        return new ResponseMessage("Success", HttpStatus.OK);
    }


}
