package com.easy_flow_server.service.payment;

import com.easy_flow_server.dto.model.TicketModel;
import com.easy_flow_server.dto.view.TicketView;
import com.easy_flow_server.entity.Line;
import com.easy_flow_server.entity.Owner;
import com.easy_flow_server.entity.Ticket;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repository.TicketRepo;
import com.easy_flow_server.service.owner.OwnerService;
import com.easy_flow_server.service.station_line.LineService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepo ticketRepo;
    private final LineService lineService;
    private final OwnerService ownerService;

    public TicketServiceImpl(TicketRepo ticketRepo, LineService lineService, OwnerService ownerService) {
        this.ticketRepo = ticketRepo;
        this.lineService = lineService;
        this.ownerService = ownerService;
    }

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
    public ResponseMessage addTicket(TicketModel ticketModel) {
        Owner owner = null;
        try {
            owner = ownerService.getOwnerByUsername(ticketModel.getOwnerName());
        } catch (NotFoundException e) {
            return new ResponseMessage("Owner not found", HttpStatus.NOT_FOUND);
        }

        Line line = null;
        try {
            line = lineService.getLineByName(ticketModel.getLineName());
            if (!line.getOwner().getId().equals(owner.getId())) {
                return new ResponseMessage("Error, This line not belong to this owner", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            //donothing
        }


        Ticket ticket = new Ticket(owner, line, ticketModel.getPrice(), ticketModel.getWeight(), ticketModel.getTime());
        try {

            ticketRepo.save(ticket);

        } catch (Exception ex) {
            return new ResponseMessage("Can not save the ticket", HttpStatus.INTERNAL_SERVER_ERROR);
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
        return ticketRepo.findByLineName(name, TicketView.class);
    }

}
