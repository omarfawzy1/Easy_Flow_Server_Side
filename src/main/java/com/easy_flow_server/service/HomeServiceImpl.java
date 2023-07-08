package com.easy_flow_server.service;

import com.easy_flow_server.dto.model.ResetPassword;
import com.easy_flow_server.entity.Passenger;
import com.easy_flow_server.entity.Wallet;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repository.PassengerRepo;
import com.easy_flow_server.repository.PrivilegeRepo;
import com.easy_flow_server.dto.model.RegisterModel;
import com.easy_flow_server.service.passenger.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class HomeServiceImpl implements HomeService {
    private final PassengerRepo passengerRepo;
    private final PasswordEncoder passwordEncoder;
    private final PassengerService passengerService;
    private final PrivilegeRepo privilegeRepo;

    public HomeServiceImpl(PassengerRepo passengerRepo, PasswordEncoder passwordEncoder, PassengerService passengerService, PrivilegeRepo privilegeRepo) {
        this.passengerRepo = passengerRepo;
        this.passwordEncoder = passwordEncoder;
        this.passengerService = passengerService;
        this.privilegeRepo = privilegeRepo;
    }

    @Override
    public ResponseMessage Register(RegisterModel registerModel) {
        if (passengerRepo.existsByUsernameIgnoreCase(registerModel.getUsername())) {
            return new ResponseMessage("The username already used", HttpStatus.CONFLICT);
        } else if (passengerRepo.existsByPhoneNumber(registerModel.getPhoneNumber())) {
            return new ResponseMessage("The Phone already used", HttpStatus.CONFLICT);

        } else if (passengerRepo.existsByEmail(registerModel.getEmail())) {

            return new ResponseMessage("The email already used", HttpStatus.CONFLICT);
        }


        Passenger passenger = new Passenger(new Wallet("cc"), registerModel.getFirstName(), registerModel.getLastName(), registerModel.getPhoneNumber(), registerModel.getCity(), registerModel.getGender(), registerModel.getBirthDay(), registerModel.getUsername(), passwordEncoder.encode(registerModel.getPassword()), registerModel.getEmail());
        passenger.addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Regular"));
        passengerRepo.save(passenger);
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
