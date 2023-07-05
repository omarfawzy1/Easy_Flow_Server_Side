package com.example.easy_flow_backend.service.admin_services;

import com.example.easy_flow_backend.dto.Models.TimePeriod;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.TransactionRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import com.example.easy_flow_backend.repos.UserRepositry;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnalysisService {
    private final TripRepo tripRepo;
    private final UserRepositry userRepositry;
    private final TransactionRepo transactionRepo;
    private final PassengersRepo passengersRepo;

    public AnalysisService(TripRepo tripRepo, UserRepositry userRepositry, TransactionRepo transactionRepo, PassengersRepo passengersRepo) {
        this.tripRepo = tripRepo;
        this.userRepositry = userRepositry;
        this.transactionRepo = transactionRepo;
        this.passengersRepo = passengersRepo;
    }

    public long getRevenue(TimePeriod timePeriod) {
        Optional<Long> revenue = tripRepo.getRevenue(timePeriod.getStart(), timePeriod.getEnd());
        if (revenue.isEmpty())
            return 0;
        return revenue.get();
    }

    public List<Object> getRevenue(TimePeriod timePeriod, String groupBy) throws BadRequestException {
        Optional<List<Object>> revenue;
        if (groupBy.equalsIgnoreCase("day")) {
            revenue = tripRepo.getRevenuePerDay(timePeriod.getStart(), timePeriod.getEnd());
        } else if (groupBy.equalsIgnoreCase("month")) {
            revenue = tripRepo.getRevenuePerMonth(timePeriod.getStart(), timePeriod.getEnd());
        } else if (groupBy.equalsIgnoreCase("year")) {
            revenue = tripRepo.getRevenuePerYear(timePeriod.getStart(), timePeriod.getEnd());
        } else {
            throw new BadRequestException("invalid group by parameter");
        }
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


    public long getTripAvgByTimeUnitForBusLine(TimePeriod timePeriod, Long timeUnit, String lineName) throws BadRequestException {

        long start = timePeriod.getStart().getTime();
        long end = timePeriod.getEnd().getTime();
        int sum = 0;
        if (end < start)
            throw new BadRequestException("invalid time period");
        int count = (int) ((end - start) / timeUnit);
        if (count == 0)
            throw new BadRequestException("the time unit is bigger than the time period");
        int i = 0;
        while (i < count) {
            sum += tripRepo.getTripAvgByTimeUnitForBusLine(new Date(start), new Date(start + timeUnit),
                    lineName);
            start += timeUnit;
            i++;
        }
        return ((long) sum / count);
    }


    public List<Object> getPeekHours(TimePeriod timePeriod, String lineName, int peekNumber) {
        List<Object> result = tripRepo.getPeekHours(timePeriod.getStart(), timePeriod.getEnd(), lineName);
        if (result.size() < peekNumber) {
            return result;
        }
        return result.subList(0, peekNumber);
    }


    public int getTransactionCount() {
        return (int) transactionRepo.count();
    }


    public int getTripCount() {
        return (int) tripRepo.count();
    }

    public List<Object> getTripPerHour(TimePeriod timePeriod) {
        return tripRepo.getTripPerHour(timePeriod.getStart(), timePeriod.getEnd());
    }


}
