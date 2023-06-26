package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Plan;
import com.example.easy_flow_backend.entity.Subscription;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.SubscriptionRepo;
import com.example.easy_flow_backend.service.notification.FirebaseNotificationService;
import com.example.easy_flow_backend.service.notification.PassengerNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepo subscriptionRepo;
    @Autowired
    WalletService walletService;
    @Autowired
    FirebaseNotificationService firebaseNotificationService;

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


    public boolean canSubscribe(Passenger passenger, Plan plan) {
        if (!passenger.getPrivileges().contains(plan.getPrivilege())) {
            return false;
        }
        boolean canWithdraw = walletService.canWithdraw(passenger.getWallet(), plan.getPrice());
        return canWithdraw;
    }

    @Override
    public ResponseMessage makeSubscription(Passenger passenger, Plan plan) {

        if (!passenger.getPrivileges().contains(plan.getPrivilege())) {
            return new ResponseMessage("Sorry, you are not compatible with this plan", HttpStatus.BAD_REQUEST);
        }
        boolean canWithdraw = walletService.canWithdraw(passenger.getWallet(), plan.getPrice());
        if (!canWithdraw) {
            return new ResponseMessage("Sorry, No enough money", HttpStatus.BAD_REQUEST);
        }
        if (!plan.isAvailable()) {
            return new ResponseMessage("Sorry, The plan is closed", HttpStatus.BAD_REQUEST);
        }
        walletService.withdraw(passenger.getWallet(), plan.getPrice());
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

    @Scheduled(cron = "0 0 0 * * *")
    public void endSubscription() {
        System.out.println("I'm event end subscription");
        List<Subscription> expiredSubscriptions = subscriptionRepo.getAllByExpireDateBefore(new Date());
        for (Subscription subscription : expiredSubscriptions) {
            boolean canSubscribe = canSubscribe(subscription.getPassenger(), subscription.getPlan());
            boolean canRepurchase = subscription.isRepurchase();

            if (canRepurchase && canSubscribe) {
                makeSubscription(subscription.getPassenger(), subscription.getPlan());
                firebaseNotificationService.notifyPassenger(subscription.getPassenger().getUsername(), firebaseNotificationService.repurchaseNotification);
            }
            else if(!canSubscribe){
                firebaseNotificationService.notifyPassenger(subscription.getPassenger().getUsername(), firebaseNotificationService.repurchaseNoEnoughBalanceNotification);
            }
            else{
                firebaseNotificationService.notifyPassenger(subscription.getPassenger().getUsername(), firebaseNotificationService.expiryNotification);
            }

            subscriptionRepo.delete(subscription);
        }

    }

}
