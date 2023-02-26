package com.example.easy_flow_backend.dto.Views;

import com.example.easy_flow_backend.entity.Status;

import java.sql.Date;


public interface TicketView {

    String getId();

    UserDetails getPassenger();

    StationView getStartTurnstile();

    StationView getEndTurnstile();


    Date getStartTime();

    Date getEndTime();

    double getPrice();

    Status getStatus();

}
