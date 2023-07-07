package com.easy_flow_server.service.passenger_services;

import com.easy_flow_server.dto.Models.ResetPassword;
import com.easy_flow_server.dto.Views.*;
import com.easy_flow_server.entity.Passenger;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.dto.Models.UpdatePassword;
import com.easy_flow_server.dto.Models.UpdateProfileModel;

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


    List<TripId> getOpenTrips(int numberOfTickets, String passengerUsername) throws NotFoundException;


    ResponseMessage makeSubscription(String owner_name, String plan_name);

    List<SubscriptionView> getMySubscriptions() throws NotFoundException;
    ResponseMessage sendResetPasswordToken(String email) ;


    ResponseMessage resetPassengerPassword(String key, ResetPassword newPassword);

    ResponseMessage updateProfile(Principal principal, UpdateProfileModel profileModel);

    ResponseMessage setPin(Principal principal,String pin);

    ResponseMessage reverseSubscriptionRepurchase(Principal principal, String ownerName, String planName);

    byte[] getOwnerImage(String ownerName) throws NotFoundException;

    ResponseMessage updatePassword(String name, UpdatePassword updatePassword);
}
