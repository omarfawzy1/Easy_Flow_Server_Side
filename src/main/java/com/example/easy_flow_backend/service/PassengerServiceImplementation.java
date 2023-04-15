package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Views.TripView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PassengerServiceImplementation implements PassengerService {

    @Autowired
    private TripRepo tripRepo;

    @Autowired
    private PassengersRepo passengerRepo;

    @Override
    public List<TripView> getMytrips() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getPrincipal().toString();
        if (userName == null || userName.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }
        return tripRepo.findAllProjectedByPassengerUsername(userName);

    }

    @Override
    public List<TripView> getMytrips(Date date) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        if (username == null || username.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }
        return tripRepo.findAllProjectedByPassengerUsernameAndStartTimeGreaterThanEqual(username, date);

    }

    @Override
    public PassagnerDetails getMyProfile() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        if (username == null || username.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }
        return passengersRepo.findProjectedByUsername(username);
    }
}
