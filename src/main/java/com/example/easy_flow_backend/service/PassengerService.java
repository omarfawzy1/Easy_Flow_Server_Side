package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.dto.Views.TripView;

import java.util.Date;
import java.util.List;

public interface PassengerService {

    List<TripView> getMytrips() throws BadRequestException;

    List<TripView> getMytrips(Date date) throws BadRequestException;

    PassagnerDetails getMyProfile() throws BadRequestException;
}
