package com.example.easy_flow_backend.dto.Views;

import com.example.easy_flow_backend.entity.Status;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;


public interface TripView extends TripId {


    String getStartStation();

    String getEndStation();

    Date getStartTime();

    Date getEndTime();

    double getPrice();

    Status getStatus();
    @Value("#{target.startTurnstile.owner.name}")
    String getOwnerName();

}
