package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Wallet;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.Dto.Models.RegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class HomeServiceImplementation implements HomeService {
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> Register(RegisterModel registerModel) throws NotFoundException {
        if (passengersRepo.existsByUsernameIgnoreCase(registerModel.getUsername())) {
            throw new NotFoundException("The username already Used");
        } else if (passengersRepo.existsByPhoneNumber(registerModel.getPhoneNumber())) {
            throw new NotFoundException("The Phone already Used");
        }

        Passenger passenger = new Passenger(new Wallet("cc"), registerModel.getFirstName(), registerModel.getLastName(), registerModel.getPhoneNumber(),"Regular", registerModel.getCity(), registerModel.getGender(),registerModel.getBirthDay(), registerModel.getUsername(), passwordEncoder.encode(registerModel.getPassword()),registerModel.getEmail());
        passengersRepo.save(passenger);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
