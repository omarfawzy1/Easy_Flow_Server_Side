package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Wallet;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.view.RegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service

public class HomeServiceImplementation implements HomeService {
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> Register(RegisterModel registerModel) throws NotFoundException {
        if (passengersRepo.existsByUsernameIgnoreCase(registerModel.getUserName())) {
            throw new NotFoundException("The username already Used");
        } else if (passengersRepo.existsByPhoneNumber(registerModel.getPhoneNum())) {
            throw new NotFoundException("The Phone already Used");
        }
        Passenger passenger = new Passenger(new Wallet("12345675"), registerModel.getUserName(), registerModel.getLastName(), registerModel.getPhoneNum(), "", "", registerModel.getGender(), registerModel.getBirthDay(), registerModel.getUserName(), passwordEncoder.encode(registerModel.getPassword()));
        passengersRepo.save(passenger);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
