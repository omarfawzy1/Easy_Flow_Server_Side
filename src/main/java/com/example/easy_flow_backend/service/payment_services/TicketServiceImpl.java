package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.TicketModel;
import com.example.easy_flow_backend.dto.Views.TicketView;
import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.entity.Ticket;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.TicketRepo;
import com.example.easy_flow_backend.service.owner_services.OwnerService;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import io.grpc.ManagedChannelProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    LineService lineService;
    @Autowired
    OwnerService ownerService;

    public List<Ticket> getAllTickets(String ownerId, String lineID) {
        return ticketRepo.findAllByOwnerIdAndLineId(ownerId, lineID);
    }

    public List<Ticket> getAllTickets(String ownerId) {
        return ticketRepo.findAllByOwnerId(ownerId);
    }


    private double getMinPriceWeightBased(List<Ticket> tickets, double weight) {


        double price = Double.MAX_VALUE;

        for (Ticket ticket : tickets) {
            if (ticket.getWeight() >= weight && ticket.getPrice() <= price)
                price = ticket.getPrice();
        }
        return price;
    }

    private double getMinPriceTimeBased(List<Ticket> tickets, long time) {
        if (time == 0) return 0;

        double price = 0.0;

        for (Ticket ticket : tickets) {
            if (ticket.getTime() <= time && ticket.getPrice() > price)
                price = ticket.getPrice();
        }
        return price;
    }

    public double getPrice(String ownerId, String lineId, double weight, long totalTime) {
        List<Ticket> tickets = getAllTickets(ownerId, lineId);

        return Math.max(getMinPriceTimeBased(tickets, totalTime), getMinPriceWeightBased(tickets, weight));
    }

    public double getPrice(String ownerId, double weight, long totalTime) {
        List<Ticket> tickets = getAllTickets(ownerId);
        return Math.max(getMinPriceTimeBased(tickets, totalTime), getMinPriceWeightBased(tickets, weight));
    }


    @Override
    public double getMinPrice(String ownerId, String lineId) {
        List<Ticket> tickets = getAllTickets(ownerId, lineId);
        return getMinPriceWeightBased(tickets, 0);
    }

    @Override
    public double getMinPrice(String ownerId) {
        List<Ticket> tickets = getAllTickets(ownerId);
        return getMinPriceWeightBased(tickets, 0);
    }

    @Override
    public ResponseMessage addTicket(TicketModel ticketModel) throws NotFoundException {
        Owner owner = ownerService.getOwnerByUsername(ticketModel.getOwnerName());

        Line line = null;
        try {
            line = lineService.getLineByName(ticketModel.getLineName());
            if (!line.getOwner().getId().equals(owner.getId())) {
                return new ResponseMessage("Error, This Line not belong to this owner.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            //donothing
        }


        Ticket ticket = new Ticket(owner, line, ticketModel.getPrice(), ticketModel.getWeight(), ticketModel.getTime());
        try {

            ticketRepo.save(ticket);

        } catch (Exception ex) {
            return new ResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public void deleteTicket(String id) {
        try {
            ticketRepo.deleteById(id);
        } catch (Exception ex) {
            return;
        }

    }

    @Override
    public TicketView getTicket(String id) {

        return ticketRepo.findById(id, TicketView.class);
    }

    @Override
    public List<TicketView> getOwnerTickets(String name) {
        return ticketRepo.findAllByOwnerName(name, TicketView.class);
    }

    @Override
    public List<TicketView> getLineTickets(String name) {
        return ticketRepo.findByLineName(name,TicketView.class);
    }

}
