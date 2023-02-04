package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.view.PassagnerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImplementation implements AdminService {
    @Autowired
    private PassengersRepo passengerRepo;

    @Override
    public List<PassagnerDetails> getAllPassangers() {
        return passengerRepo.findAllProjectedBy();
    }


}
