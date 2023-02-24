package com.example.easy_flow_backend.Dto.Views;

import com.example.easy_flow_backend.entity.Status;

import java.time.LocalTime;
import java.util.Date;

public interface TicketView {

    String getId();

    UserDetails getPassenger();

    StationView getStartStation();

    StationView getEndStation();

    Date getDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    double getPrice();

    Status getStatus();

}
