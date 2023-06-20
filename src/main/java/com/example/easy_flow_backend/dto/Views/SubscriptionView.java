package com.example.easy_flow_backend.dto.Views;


import java.util.Date;

public interface SubscriptionView {
    int getRemainingTrips();

    Date getExpireDate();

    boolean isRepurchase();

    String getPlanName();
    String getPlanOwnerName();

}
