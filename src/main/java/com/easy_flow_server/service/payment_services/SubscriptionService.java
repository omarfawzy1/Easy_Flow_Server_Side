package com.easy_flow_server.service.payment_services;

import com.easy_flow_server.dto.Models.RideModel;
import com.easy_flow_server.entity.Passenger;
import com.easy_flow_server.entity.Plan;
import com.easy_flow_server.entity.Subscription;
import com.easy_flow_server.error.ResponseMessage;

import java.util.List;

public interface SubscriptionService {
    Subscription getbestSubscription(List<Subscription> subscriptions, RideModel rideModel);

    ResponseMessage makeSubscription(Passenger passenger, Plan plan);
}
