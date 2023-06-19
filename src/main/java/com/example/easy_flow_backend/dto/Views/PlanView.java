package com.example.easy_flow_backend.dto.Views;

import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.entity.PassengerPrivilege;

public interface PlanView {
    String getId();

    PassengerPrivilege getPrivilege();

    float getPrice();

    int getDurationDays();

    int getNumberOfTrips();

    String getName();

    int getMaxCompanion();

    boolean getAvailable();

    String getOwnerName();

    float getDiscountRate();
}
