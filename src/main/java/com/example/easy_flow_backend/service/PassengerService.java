package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.Dto.Views.PassagnerDetails;
import com.example.easy_flow_backend.Dto.Views.TicketView;

import java.util.Date;
import java.util.List;

public interface PassengerService {

    List<TicketView> getMyTickets() throws BadRequestException;

    List<TicketView> getMyTickets(Date date) throws BadRequestException;

    PassagnerDetails getMyProfile() throws BadRequestException;
}
