package com.example.easy_flow_backend.service.passenger_services;

import com.example.easy_flow_backend.dto.Views.PassagnerBriefDetails;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Views.TripView;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public interface PassengerService {

    List<TripView> getMytrips() throws BadRequestException;

    List<TripView> getMytrips(Date date) throws BadRequestException;

    PassagnerDetails getMyProfile() throws BadRequestException;

    public List<PassagnerBriefDetails> getAllPassangers();

    public Passenger getPassenger(String username) throws NotFoundException;

    public PassagnerDetails getPassengerDetails(String username) throws NotFoundException;

    public ResponseMessage deletePassenger(String username) throws NotFoundException;

    public ResponseMessage passengerStatus(String username) throws NotFoundException;

    public int getAllPassangersCount();

    public int getAllPassangersCountWithType(String type);

    void rechargePassenger(String name, double amount);
    //ToDo edit profile - forget password

    void updateLastGeneratedTime(String username, Date time);

}
