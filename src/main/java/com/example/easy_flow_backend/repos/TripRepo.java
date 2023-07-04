package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Status;
import com.example.easy_flow_backend.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepo extends JpaRepository<Trip, String> {
    <T> T findById(String id, Class<T> type);


    <T> List<T> findAllByPassengerUsernameAndStatus(String passenger_username, Status status, Class<T> type);

    <T>List<T>findAllByPassengerUsernameAndStatusAndStartTimeGreaterThanEqual(String passenger_username, Status status, Date startTime,Class<T>type);
    boolean existsByPassengerUsernameAndStatus(String passengerUsername, Status status);

    Trip findByPassengerUsernameAndStatus(String passenger_username, Status status);

    @Query("SELECT SUM (trip.price) " +
            "FROM Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end ")
    Optional<Long> getRevenue(@Param("start") Date start, @Param("end") Date end);
    @Query("SELECT day (trip.startTime), SUM (trip.price) " +
            "FROM Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end " +
            "GROUP BY day(trip.startTime) " +
            "order by day(trip.startTime) ASC")
    Optional<List<Object>> getRevenuePerDay(@Param("start") Date start, @Param("end") Date end);
    @Query("SELECT month(trip.startTime),SUM (trip.price) " +
            "FROM Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end " +
            "GROUP BY month(trip.startTime) " +
            "order by month(trip.startTime) ASC")
    Optional<List<Object>> getRevenuePerMonth(@Param("start") Date start, @Param("end") Date end);
    @Query("SELECT year(trip.startTime),SUM (trip.price) " +
            "FROM Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end " +
            "GROUP BY year(trip.startTime) " +
            "order by year(trip.startTime) ASC")
    Optional<List<Object>> getRevenuePerYear(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT AVG(avgRevenue) FROM " +
            "(SELECT AVG(trip.price) as avgRevenue " +
            "FROM Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end GROUP BY trip.passenger)")
    Optional<Long> getRevenueAvg(@Param("start") Date start, @Param("end") Date end);

    @Query("select avg (trip.price)" +
            "From Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end AND trip.passenger.username= :passengerName ")
    Optional<Long> getRevenueAvgByPassenger(@Param("start") Date start, @Param("end") Date end,
                                            @Param("passengerName") String passengerName);

    @Query("select COUNT (trip.id)" +
            "From Trip trip " +
            "WHERE trip.startTime>= :start AND trip.endTime<= :end " +
            "AND (trip.startStation = :stationName OR trip.endStation = :stationName)")
    int getTripInStationCount(@Param("start") Date start,
                              @Param("end") Date end,
                              @Param("stationName") String stationName);

    @Query("SELECT COUNT (trip.id)" +
            "From Trip trip Join MovingTurnstile mt " +
            "ON (trip.startTurnstile.id = mt.id) " +
            "WHERE trip.transportationType = 0 AND trip.startTime >= :start AND trip.endTime <= :end " +
            "AND mt.line.name = :lineName ")
    long getTripAvgByTimeUnitForBusLine(@Param("start") Date start,
                                        @Param("end") Date end,
                                        @Param("lineName") String lineName);

    @Query( "SELECT hour(trip.startTime) , COUNT (trip.id) as number " +
            "From Trip trip Join MovingTurnstile mt " +
            "ON trip.startTurnstile.id = mt.id " +
            "WHERE trip.transportationType = 0 AND trip.startTime >= :start AND trip.startTime <= :end " +
            "AND mt.line.name = :lineName " +
            "GROUP BY hour(trip.startTime) " +
            "ORDER BY number DESC ")
    List<Object> getPeekHours(@Param("start") Date start,
                              @Param("end") Date end,
                              @Param("lineName") String lineName);

    @Query("SELECT trip " +
            "FROM Trip trip " +
            "WHERE trip.status = 1 AND trip.passenger.id= :passengerId ")
    Trip outRideForgetTicket(@Param("passengerId") String passengerId);
    @Query("SELECT hour(trip.startTime), COUNT (trip.id) " +
            "From Trip trip "+
            "WHERE trip.startTime >= :start AND trip.startTime <= :end " +
            "GROUP BY hour(trip.startTime) " +
            "ORDER BY hour(trip.startTime) ASC")
    List<Object>getTripPerHour(@Param("start") Date start,
                               @Param("end") Date end);
}