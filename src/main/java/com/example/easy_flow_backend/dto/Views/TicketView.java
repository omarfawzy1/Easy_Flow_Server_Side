package com.example.easy_flow_backend.dto.Views;

public interface TicketView {
    LineView getLine();

    double getPrice();

    double getWeight();
    long getTime();

}
