package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Status;
import com.example.easy_flow_backend.entity.Ticket;
import com.example.easy_flow_backend.Dto.Views.TicketView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
    List<TicketView> findAllProjectedBy();

    List<TicketView> findAllProjectedByPassengerUsername(String passenger_username);

    List<TicketView> findAllProjectedByPassengerUsernameAndDateGreaterThanEqual(String passenger_username, Date date);

    boolean existsByPassengerUsernameAndStatus(String passengerUsername, Status status);

    Ticket findByPassengerUsernameAndStatus(String passenger_username, Status status);
    @Query("SELECT SUM (ticket.price) FROM Ticket ticket WHERE ticket.date>= :start and ticket.date<= :end")
    long getRevenue(@Param("start") Date start, @Param("start") Date end);
}
