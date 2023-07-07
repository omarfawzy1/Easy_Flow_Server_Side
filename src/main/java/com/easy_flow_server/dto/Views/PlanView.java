package com.easy_flow_server.dto.Views;

public interface PlanView {
    String getPrivilegeName();

    float getPrice();

    int getDurationDays();

    int getNumberOfTrips();

    String getName();

    int getMaxCompanion();

    boolean getAvailable();

    String getOwnerName();

    float getDiscountRate();
}
