package com.easy_flow_server.service;

import com.easy_flow_server.entity.User;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseMessage flipUserActive(String username) {
        if (!userRepo.existsByUsername(username)) {
            return new ResponseMessage("Invalid Username", HttpStatus.NOT_FOUND);
        }
        User user = userRepo.findUserByUsername(username);
        user.setActive(!user.isActive());
        userRepo.save(user);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    public ResponseMessage deleteUser(String username) {
        if (!userRepo.existsByUsername(username)) {
            return new ResponseMessage("Invalid Username", HttpStatus.NOT_FOUND);

        }

        userRepo.deleteByUsername(username);
        return new ResponseMessage("Success", HttpStatus.OK);
    }


}
