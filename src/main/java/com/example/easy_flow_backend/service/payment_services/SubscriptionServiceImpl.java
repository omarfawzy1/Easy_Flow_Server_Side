package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Plan;
import com.example.easy_flow_backend.entity.Subscription;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public ResponseMessage makeSubscription(Passenger passenger, Plan plan) {

        Date expirationDate = new Date();
        final long ONE_DAY_IN_MILLISECOND = 1000 * 60 * 60 * 24;
        expirationDate.setTime(expirationDate.getTime() + plan.getDurationDays() * ONE_DAY_IN_MILLISECOND);

        Subscription subscription = new Subscription(plan.getNumberOfTrips(), expirationDate, false, plan, passenger);
        try {
            subscriptionRepo.save(subscription);
        } catch (Exception ex) {
            return new ResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }


}
