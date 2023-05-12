package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.TicketModel;
import com.example.easy_flow_backend.dto.Views.TicketView;
import com.example.easy_flow_backend.entity.Ticket;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets(String ownerId);

    List<Ticket> getAllTickets(String ownerId, String lineID);

    double getPrice(String ownerId, double weight, long totalTime);

    double getPrice(String ownerId, String lineId, double weight, long totalTime);

    double getMinPrice(String ownerId, String lineId);

    double getMinPrice(String ownerId);

    ResponseMessage addTicket(TicketModel ticketModel) throws NotFoundException;

    void deleteTicket(String id);

    TicketView getTicket(String id);

    List<TicketView> getOwnerTickets(String name);

    List<TicketView> getLineTickets(String name);
}
