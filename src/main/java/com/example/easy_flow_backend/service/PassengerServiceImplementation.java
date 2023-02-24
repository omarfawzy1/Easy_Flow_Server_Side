package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.TicketRepo;
import com.example.easy_flow_backend.view.PassagnerDetails;
import com.example.easy_flow_backend.view.TicketView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PassengerServiceImplementation implements PassengerService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private PassengersRepo passengersRepo;

    @Override
    public List<TicketView> getMyTickets() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getPrincipal().toString();
        if (userName == null || userName.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }
        return ticketRepo.findAllProjectedByPassengerUsername(userName);

    }

    @Override
    public List<TicketView> getMyTickets(Date date) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        if (username == null || username.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }
        return ticketRepo.findAllProjectedByPassengerUsernameAndDateGreaterThanEqual(username, date);

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
