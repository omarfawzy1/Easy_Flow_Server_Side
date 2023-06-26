package com.example.easy_flow_backend.service.passenger_services;

import com.example.easy_flow_backend.dto.Models.ResetPassword;
import com.example.easy_flow_backend.dto.Models.UpdateProfileModel;
import com.example.easy_flow_backend.dto.Views.*;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Privilege;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface PassengerService {

    List<TripView> getMytrips(String username) throws BadRequestException;

    List<TripView> getMytrips(Date date, String username) throws BadRequestException;

    PassagnerDetails getMyProfile() throws BadRequestException;

    public List<PassagnerBriefDetails> getAllPassangers();

    public Passenger getPassenger(String username) throws NotFoundException;

    public PassagnerDetails getPassengerDetails(String username) throws NotFoundException;

    public ResponseMessage deletePassenger(String username);

    public ResponseMessage passengerStatus(String username) throws NotFoundException;

    public int getAllPassangersCount();

    public int getPassengersCountWithPrivilege(String privilege);

    void rechargePassenger(String name, double amount);
    //ToDo edit profile - forget password


    List<TripId> getOpenTrips(int numberOfTickets, String passengerUsername) throws NotFoundException;


    ResponseMessage makeSubscription(String owner_name, String plan_name);

    List<SubscriptionView> getMySubscriptions() throws NotFoundException;
    ResponseMessage sendResetPasswordToken(String email) throws NotFoundException;


    ResponseMessage resetPassengerPassword(String key, ResetPassword newPassword);

    ResponseMessage updateProfile(Principal principal, UpdateProfileModel profileModel);
}
