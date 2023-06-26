package com.example.easy_flow_backend.dto.Views;

import com.example.easy_flow_backend.entity.Privilege;

public interface PlanView {

    Privilege getPrivilege();

    float getPrice();

    int getDurationDays();

    int getNumberOfTrips();

    String getName();

    int getMaxCompanion();

    boolean getAvailable();

    String getOwnerName();

    float getDiscountRate();
}
