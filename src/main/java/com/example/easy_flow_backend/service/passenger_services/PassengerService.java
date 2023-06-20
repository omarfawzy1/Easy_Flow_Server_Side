package com.example.easy_flow_backend.service.passenger_services;

import com.example.easy_flow_backend.dto.Views.*;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Plan;
import com.example.easy_flow_backend.entity.Subscription;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.util.Date;
import java.util.List;

public interface PassengerService {

    List<TripView> getMytrips(String username) throws BadRequestException;

    List<TripView> getMytrips(Date date, String username) throws BadRequestException;

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


    List<TripId> getOpenTrips(int numberOfTickets, String passengerUsername) throws NotFoundException;


    ResponseMessage makeSubscription(String owner_name, String plan_name);

    List<SubscriptionView> getMySubscriptions() throws NotFoundException;
}
