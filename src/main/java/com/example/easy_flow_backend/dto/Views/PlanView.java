package com.example.easy_flow_backend.dto.Views;

import org.springframework.beans.factory.annotation.Value;

public interface PlanView {
    @Value("#{target.privilege.name}")
    String privilegeName();

    float getPrice();

    int getDurationDays();

    int getNumberOfTrips();

    String getName();

    int getMaxCompanion();

    boolean getAvailable();

    String getOwnerName();

    float getDiscountRate();
}
