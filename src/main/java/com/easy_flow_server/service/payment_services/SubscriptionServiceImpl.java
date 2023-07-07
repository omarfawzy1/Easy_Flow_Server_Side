package com.easy_flow_server.service.payment_services;

import com.easy_flow_server.dto.Models.RideModel;
import com.easy_flow_server.entity.Passenger;
import com.easy_flow_server.entity.Plan;
import com.easy_flow_server.entity.Subscription;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.SubscriptionRepo;
import com.easy_flow_server.service.notification.FirebaseNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
    private final WalletService walletService;
    private final FirebaseNotificationService firebaseNotificationService;

    public SubscriptionServiceImpl(SubscriptionRepo subscriptionRepo, WalletService walletService, FirebaseNotificationService firebaseNotificationService) {
        this.subscriptionRepo = subscriptionRepo;
        this.walletService = walletService;
        this.firebaseNotificationService = firebaseNotificationService;
    }

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
        return walletService.canWithdraw(passenger.getWallet(), plan.getPrice());
    }

    @Override
    public ResponseMessage makeSubscription(Passenger passenger, Plan plan) {

        if (!passenger.getPrivileges().contains(plan.getPrivilege())) {
            return new ResponseMessage("Sorry, you are not compatible with this plan", HttpStatus.FORBIDDEN);
        }
        boolean canWithdraw = walletService.canWithdraw(passenger.getWallet(), plan.getPrice());
        if (!canWithdraw) {
            return new ResponseMessage("Sorry, No enough money", HttpStatus.BAD_REQUEST);
        }
        if (!plan.isAvailable()) {
            return new ResponseMessage("Sorry, The plan is closed", HttpStatus.FORBIDDEN);
        }
        walletService.withdraw(passenger.getWallet(), plan.getPrice());
        Date expirationDate = new Date();
        final long ONE_DAY_IN_MILLISECOND = 1000 * 60 * 60 * 24;
        expirationDate.setTime(expirationDate.getTime() + plan.getDurationDays() * ONE_DAY_IN_MILLISECOND);

        Subscription subscription = new Subscription(plan.getNumberOfTrips(), expirationDate, false, plan, passenger);
        try {
            subscriptionRepo.save(subscription);
        } catch (Exception ex) {
            return new ResponseMessage("You are Already Subscribe this plan", HttpStatus.CONFLICT);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void endSubscription() {
        System.out.println("I'm event end subscription");
        List<Subscription> expiredSubscriptions = subscriptionRepo.getAllByExpireDateBefore(new Date());
        for (Subscription subscription : expiredSubscriptions) {
            boolean canSubscribe = canSubscribe(subscription.getPassenger(), subscription.getPlan());
            boolean canRepurchase = subscription.isRepurchase();

            if (canRepurchase && canSubscribe) {
                makeSubscription(subscription.getPassenger(), subscription.getPlan());
                firebaseNotificationService.notifyPassenger(subscription.getPassenger().getUsername(), firebaseNotificationService.repurchaseNotification);
            } else if (!canSubscribe) {
                firebaseNotificationService.notifyPassenger(subscription.getPassenger().getUsername(), firebaseNotificationService.repurchaseNoEnoughBalanceNotification);
            } else {
                firebaseNotificationService.notifyPassenger(subscription.getPassenger().getUsername(), firebaseNotificationService.expiryNotification);
            }

            subscriptionRepo.delete(subscription);
        }

    }

    public ResponseMessage renewSubscription(Passenger passenger, Plan plan) {
        Subscription oldSubscription = subscriptionRepo.findByPassengerUsernameAndPlanOwnerNameAndPlanName(passenger.getUsername(), plan.getOwner().getName(), plan.getName());

        boolean canSubscribe = canSubscribe(passenger, plan);
        if (canSubscribe) {
            subscriptionRepo.delete(oldSubscription);
            firebaseNotificationService.notifyPassenger(passenger.getUsername(), firebaseNotificationService.repurchaseNotification);
            return makeSubscription(oldSubscription.getPassenger(), oldSubscription.getPlan());
        }
        firebaseNotificationService.notifyPassenger(passenger.getUsername(), firebaseNotificationService.repurchaseNoEnoughBalanceNotification);
        return new ResponseMessage("Can not subscribe", HttpStatus.OK);
    }

}
