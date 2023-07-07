package com.easy_flow_server.service;

import com.easy_flow_server.entity.User;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.UserRepositry;
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
