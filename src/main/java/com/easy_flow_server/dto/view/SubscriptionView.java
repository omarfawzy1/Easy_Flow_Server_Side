package com.easy_flow_server.dto.view;


import java.util.Date;

public interface SubscriptionView {
    int getRemainingTrips();

    Date getExpireDate();

    boolean isRepurchase();

    String getPlanName();
    String getPlanOwnerName();

}
