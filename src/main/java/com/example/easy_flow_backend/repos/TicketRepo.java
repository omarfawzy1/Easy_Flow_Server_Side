package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Status;
import com.example.easy_flow_backend.entity.Ticket;
import com.example.easy_flow_backend.dto.Views.TicketView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
    List<TicketView> findAllProjectedBy();

    List<TicketView> findAllProjectedByPassengerUsername(String passenger_username);

    List<TicketView> findAllProjectedByPassengerUsernameAndDateGreaterThanEqual(String passenger_username, Date date);

    boolean existsByPassengerUsernameAndStatus(String passengerUsername, Status status);

    Ticket findByPassengerUsernameAndStatus(String passenger_username, Status status);

    @Query("SELECT SUM (ticket.price) " +
            "FROM Ticket ticket " +
            "WHERE ticket.date>= :start AND ticket.date<= :end ")
    Optional<Long> getRevenue(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT AVG(avgRevenue) FROM " +
            "(SELECT AVG(ticket.price) as avgRevenue " +
            "FROM Ticket ticket " +
            "WHERE ticket.date>= :start AND ticket.date<= :end GROUP BY ticket.passenger)")
    Optional<Long> getRevenueAvg(@Param("start") Date start, @Param("end") Date end);

    @Query("select avg (ticket.price)" +
        "From Ticket ticket " +
        "WHERE ticket.date>= :start AND ticket.date<= :end AND ticket.passenger.id= :passengerId ")
    Optional<Long> getRevenueAvgByPassenger(@Param("start") Date start, @Param("end") Date end,
                                            @Param("passengerId") String passengerId);
    @Query("select COUNT (passenger.id)" +
            "From Passenger passenger " +
            "WHERE passenger.wallet.balance < 0")
    int getNegativePassengerCount();
    @Query("select COUNT (passenger.id)" +
            "From Passenger passenger " +
            "WHERE passenger.wallet.balance < :threshold")
    int getBelowThresholdCount(@Param("threshold") long threshold);
}
