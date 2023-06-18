package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription getbestSubscription(List<Subscription> subscriptions, RideModel rideModel);

}
