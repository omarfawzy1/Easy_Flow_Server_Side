package com.example.easy_flow_backend.view;

import java.util.Date;

public interface TicketView {

    Long getId();

    UserDetails getPassenger();

    StationView getStartStation();

    StationView getEndStation();

    Date getDate();

    Date getStartTime();

    Date getEndTime();

    float getPrice();

}
