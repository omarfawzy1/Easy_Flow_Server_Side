package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Subscription;
import com.example.easy_flow_backend.repos.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepo subscriptionRepo;

    @Override
    public Subscription getbestSubscription(List<Subscription> subscriptions, RideModel rideModel) {
        float discountRate = 0F;
        Subscription bestSubscription = null;
        for (Subscription subscription : subscriptions) {
            if (!subscription.isExpired() &&
                    rideModel.getCompanionCount() <= subscription.getPlan().getMaxCompanion() &&
                    subscription.getRemainingTrips() >= rideModel.getCompanionCount() && discountRate <= subscription.getPlan().getDiscountRate()) {
                discountRate = subscription.getPlan().getDiscountRate();
                bestSubscription = subscription;
            }
        }

        return bestSubscription;
    }




}
