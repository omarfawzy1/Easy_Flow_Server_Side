package com.easy_flow_server.dto.view;

import com.easy_flow_server.entity.Status;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;


public interface TripView extends TripId {


    String getStartStation();

    String getEndStation();

    Date getStartTime();

    Date getEndTime();

    double getPrice();

    Status getStatus();
    @Value("#{target.startTurnstile.owner.name}")
    String getOwnerName();

}
