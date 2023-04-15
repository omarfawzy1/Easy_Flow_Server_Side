package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.entity.Status;
import com.example.easy_flow_backend.entity.TransportationType;
import com.example.easy_flow_backend.entity.Trip;
import com.example.easy_flow_backend.dto.Views.TripView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TripRepo extends JpaRepository<Trip, String> {
    List<TripView> findAllProjectedBy();

    List<TripView> findAllProjectedByPassengerUsername(String passenger_username);

    List<TripView> findAllProjectedByPassengerUsernameAndStartTimeGreaterThanEqual(String passenger_username, Date start_time);

    boolean existsByPassengerUsernameAndStatus(String passengerUsername, Status status);

    Trip findByPassengerUsernameAndStatus(String passenger_username, Status status);

    @Query("SELECT SUM (trip.price) " +
            "FROM Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end ")
    Optional<Long> getRevenue(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT AVG(avgRevenue) FROM " +
            "(SELECT AVG(trip.price) as avgRevenue " +
            "FROM Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end GROUP BY trip.passenger)")
    Optional<Long> getRevenueAvg(@Param("start") Date start, @Param("end") Date end);

    @Query("select avg (trip.price)" +
            "From Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end AND trip.passenger.id= :passengerId ")
    Optional<Long> getRevenueAvgByPassenger(@Param("start") Date start, @Param("end") Date end,
                                            @Param("passengerId") String passengerId);

    @Query("select COUNT (trip.id)" +
            "From Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end " +
            "AND (trip.startStation = :stationName OR trip.endStation = :stationName)")
    int getTripInStationCount(@Param("start") Date start,
                              @Param("end") Date end,
                              @Param("stationName") String stationName);

    @Query( "SELECT COUNT (trip.id)" +
            "From Trip trip Join MovingTurnstile mt " +
            "ON (trip.endTurnstile.id = mt.id) OR (trip.startTurnstile.id = mt.id) "+
            "WHERE trip.transportationType = 0 AND trip.startTime >= :start AND trip.endTime <= :end "+
            "AND mt.line.id = :lineId ")
    long getTripAvgByTimeUnitForBusLine(@Param("start") Date start,
                                        @Param("end") Date end,
                                        @Param("lineId") String lineId);
    @Query( value = "SELECT hour(trip.startTime) , COUNT (trip.id) as number " +
            "From Trip trip Join MovingTurnstile mt " +
            "ON (trip.endTurnstile.id = mt.id) OR (trip.startTurnstile.id = mt.id) "+
            "WHERE trip.transportationType = :transportType AND trip.startTime >= :start AND trip.startTime <= :end "+
            "AND mt.line.id = :lineId "+
            "GROUP BY hour(trip.startTime) "+
            "ORDER BY number DESC ")
    List<Object> getPeekHours(@Param("start") Date start,
                              @Param("end") Date end,
                              @Param("lineId") String lineId,
                              @Param("transportType") TransportationType transportType);
//    @Query( "SELECT hour(trip.startTime) , COUNT (trip.id) " +
//            "From Trip trip  " +
//            "WHERE trip.startTime >= :start AND trip.endTime <= :end "+
//            "GROUP BY hour(trip.startTime)")
//    List<Object> getPeekHours(@Param("start") Date start,
//                     @Param("end") Date end);
}