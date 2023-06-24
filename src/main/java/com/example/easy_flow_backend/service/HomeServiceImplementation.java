package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.ResetPassword;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Wallet;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.dto.Models.RegisterModel;
import com.example.easy_flow_backend.service.passenger_services.PassengerService;
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
    @Autowired
    private PassengerService passengerService;

    @Override
    public ResponseMessage Register(RegisterModel registerModel) throws NotFoundException {
        if (passengersRepo.existsByUsernameIgnoreCase(registerModel.getUsername())) {
            throw new NotFoundException("The username already Used");
        } else if (passengersRepo.existsByPhoneNumber(registerModel.getPhoneNumber())) {
            throw new NotFoundException("The Phone already Used");
        }

        Passenger passenger = new Passenger(new Wallet("cc"), registerModel.getFirstName(), registerModel.getLastName(), registerModel.getPhoneNumber(), registerModel.getCity(), registerModel.getGender(), registerModel.getBirthDay(), registerModel.getUsername(), passwordEncoder.encode(registerModel.getPassword()), registerModel.getEmail());
        passengersRepo.save(passenger);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage sendResetPasswordToken(String email) throws NotFoundException {
        return passengerService.sendResetPasswordToken(email);
    }

    @Override
    public ResponseMessage resetPassword(String key, ResetPassword newPassword) {
        return passengerService.resetPassengerPassword(key, newPassword);
    }
}
