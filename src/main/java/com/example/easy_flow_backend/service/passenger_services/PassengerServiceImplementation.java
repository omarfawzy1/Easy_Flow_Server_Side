package com.example.easy_flow_backend.service.passenger_services;

import com.example.easy_flow_backend.dto.Views.PassagnerBriefDetails;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Views.TripView;
import com.example.easy_flow_backend.service.payment_services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private WalletService walletService;

    @Override
    public List<TripView> getMytrips(String username) throws BadRequestException {
        if (username == null || username.equalsIgnoreCase("anonymous"))
            throw new BadRequestException("Not Authenticated");

        return tripRepo.findAllProjectedByPassengerUsername(username);
    }

    @Override
    public List<TripView> getMytrips(Date date, String username) throws BadRequestException {
        if (username == null || username.equalsIgnoreCase("anonymous"))
            throw new BadRequestException("Not Authenticated");

        return tripRepo.findAllProjectedByPassengerUsernameAndStartTimeGreaterThanEqual(username, date);
    }

    @Override
    public PassagnerDetails getMyProfile() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        if (username == null || username.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }
        return passengerRepo.findProjectedByUsername(username);
    }

    @Override
    public List<PassagnerBriefDetails> getAllPassangers() {
        return passengerRepo.findAllProjectedBy();
    }

    @Override
    public PassagnerDetails getPassengerDetails(String username) throws NotFoundException {
        PassagnerDetails passenger = passengerRepo.findAllProjectedByUsername(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        return passenger;
    }


    @Override
    public Passenger getPassenger(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        return passenger;
    }

    @Override
    public ResponseMessage deletePassenger(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        passengerRepo.deleteByUsernameIgnoreCase(username);
        return new ResponseMessage("Passenger deleted Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseMessage passengerStatus(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        passenger.setActive(!passenger.isActive());
        passengerRepo.save(passenger);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public int getAllPassangersCount() {
        return (int) passengerRepo.count();
    }

    @Override
    public int getAllPassangersCountWithType(String type) {
        return passengerRepo.getAllPassangersCountWithType(type);
    }

    @Override
    public void rechargePassenger(String username, double amount) {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        walletService.recharge(passenger.getWallet().getId(), amount);
    }

    @Override
    public void updateLastGeneratedTime(String username, Date time) {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        passenger.setLastQrTime(time);
        passengerRepo.save(passenger);
    }

}