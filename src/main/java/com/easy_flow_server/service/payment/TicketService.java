package com.easy_flow_server.service.payment;

import com.easy_flow_server.dto.model.TicketModel;
import com.easy_flow_server.dto.view.TicketView;
import com.easy_flow_server.entity.Ticket;
import com.easy_flow_server.error.ResponseMessage;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets(String ownerId);

    List<Ticket> getAllTickets(String ownerId, String lineID);

    double getPrice(String ownerId, double weight, long totalTime);

    double getPrice(String ownerId, String lineId, double weight, long totalTime);

    double getMinPrice(String ownerId, String lineId);

    double getMinPrice(String ownerId);

    ResponseMessage addTicket(TicketModel ticketModel);

    void deleteTicket(String id);

    TicketView getTicket(String id);

    List<TicketView> getOwnerTickets(String name);

    List<TicketView> getLineTickets(String name);
}
