package com.easy_flow_server.service;

import com.easy_flow_server.dto.Models.ResetPassword;
import com.easy_flow_server.entity.Passenger;
import com.easy_flow_server.entity.Wallet;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.PassengersRepo;
import com.easy_flow_server.repos.PrivilegeRepo;
import com.easy_flow_server.dto.Models.RegisterModel;
import com.easy_flow_server.service.passenger_services.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class HomeServiceImplementation implements HomeService {
    private final PassengersRepo passengersRepo;
    private final PasswordEncoder passwordEncoder;
    private final PassengerService passengerService;
    private final PrivilegeRepo privilegeRepo;

    public HomeServiceImplementation(PassengersRepo passengersRepo, PasswordEncoder passwordEncoder, PassengerService passengerService, PrivilegeRepo privilegeRepo) {
        this.passengersRepo = passengersRepo;
        this.passwordEncoder = passwordEncoder;
        this.passengerService = passengerService;
        this.privilegeRepo = privilegeRepo;
    }

    @Override
    public ResponseMessage Register(RegisterModel registerModel) {
        if (passengersRepo.existsByUsernameIgnoreCase(registerModel.getUsername())) {
            return new ResponseMessage("The username already Used", HttpStatus.CONFLICT);
        } else if (passengersRepo.existsByPhoneNumber(registerModel.getPhoneNumber())) {
            return new ResponseMessage("The Phone already Used", HttpStatus.CONFLICT);

        } else if (passengersRepo.existsByEmail(registerModel.getEmail())) {

            return new ResponseMessage("The email already Used", HttpStatus.CONFLICT);

        }


        Passenger passenger = new Passenger(new Wallet("cc"), registerModel.getFirstName(), registerModel.getLastName(), registerModel.getPhoneNumber(), registerModel.getCity(), registerModel.getGender(), registerModel.getBirthDay(), registerModel.getUsername(), passwordEncoder.encode(registerModel.getPassword()), registerModel.getEmail());
        passenger.addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Regular"));
        passengersRepo.save(passenger);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage sendResetPasswordToken(String email) {
        return passengerService.sendResetPasswordToken(email);
    }

    @Override
    public ResponseMessage resetPassword(String key, ResetPassword newPassword) {
        return passengerService.resetPassengerPassword(key, newPassword);
    }
}
