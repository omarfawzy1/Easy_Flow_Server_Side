package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Ticket;
import com.example.easy_flow_backend.repos.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepo ticketRepo;

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


}
