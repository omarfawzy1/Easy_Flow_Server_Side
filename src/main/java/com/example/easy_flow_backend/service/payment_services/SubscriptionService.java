package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Plan;
import com.example.easy_flow_backend.entity.Subscription;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.util.List;

public interface SubscriptionService {
    Subscription getbestSubscription(List<Subscription> subscriptions, RideModel rideModel);

    ResponseMessage makeSubscription(Passenger passenger, Plan plan);

    ResponseMessage renewSubscription(Passenger passenger, Plan plan);
}
