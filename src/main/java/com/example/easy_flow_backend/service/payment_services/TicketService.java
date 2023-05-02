package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.entity.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets(String ownerId);

    List<Ticket> getAllTickets(String ownerId, String lineID);

    double getPrice(String ownerId, double weight, long totalTime);

    double getPrice(String ownerId, String lineId, double weight, long totalTime);

    double getMinPrice(String ownerId, String lineId);

    double getMinPrice(String ownerId);

}
