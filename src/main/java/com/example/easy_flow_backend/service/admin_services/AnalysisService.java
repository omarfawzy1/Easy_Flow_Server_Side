package com.example.easy_flow_backend.service.admin_services;

import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.entity.TransportationType;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.TransactionRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import com.example.easy_flow_backend.repos.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class AnalysisService {
    @Autowired
    TripRepo tripRepo;
    @Autowired
    UserRepositry userRepositry;
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    PassengersRepo passengersRepo;

    public long getRevenue(TimePeriod timePeriod) {
        Optional<Long> revenue = tripRepo.getRevenue(timePeriod.getStart(), timePeriod.getEnd());
        if (revenue.isEmpty())
            return 0;
        return revenue.get();
    }


    public long getRevenueAvg(TimePeriod timePeriod) {
        Optional<Long> revenue = tripRepo.getRevenueAvg(timePeriod.getStart(), timePeriod.getEnd());
        if (revenue.isEmpty())
            return 0;
        return revenue.get();
    }


    public long getRevenueAvgByPassenger(TimePeriod timePeriod, String passengerName) {
        Optional<Long> revenue = tripRepo.getRevenueAvgByPassenger(timePeriod.getStart(), timePeriod.getEnd(), passengerName);
        if (revenue.isEmpty())
            return 0;
        return revenue.get();
    }


    public int getNegativePassengerCount() {
        return passengersRepo.getNegativePassengerCount();
    }


    public int getBelowThresholdCount(long threshold) {
        return passengersRepo.getBelowThresholdCount(threshold);
    }


    public Object getTurnstilesStatus() {
        Map<String, Integer> result = new HashMap<>();
        return userRepositry.getTurnstilesStatus();
    }


    public int getTripInStationCount(TimePeriod timePeriod, String stationName) {
        return tripRepo.getTripInStationCount(timePeriod.getStart(), timePeriod.getEnd(), stationName);
    }


    public long getTripAvgByTimeUnitForBusLine(TimePeriod timePeriod, Long timeUnit, String lineName) {
        long sum=0;
        int count=0;
        Long start = timePeriod.getStart().getTime();
        Long end = timePeriod.getEnd().getTime();
        while(start+timeUnit<=end){
            sum+= tripRepo.getTripAvgByTimeUnitForBusLine(new Date(start), new Date(start+timeUnit),
                    lineName);
            start+=timeUnit;
            count++;
        }
        return sum/count;
    }


    public List<Object> getPeekHours(TimePeriod timePeriod, String lineName, TransportationType transportType,
                                     int peekNumber) {
        List<Object> result =tripRepo.getPeekHours(timePeriod.getStart(), timePeriod.getEnd(), lineName,
                transportType);
        if(result.size()<peekNumber){
            return result;
        }
        return result.subList(0,peekNumber);
    }


    public int getTransactionCount() {
        return (int) transactionRepo.count();
    }


    public int getTripCount() {
        return (int) tripRepo.count();
    }

}
