package com.easy_flow_server.service.notification;

import com.easy_flow_server.service.utils.Utility;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseNotificationService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;
    public final PassengerNotification repurchaseNotification = new PassengerNotification("Subscription repurchased successfully", "Subscription Repurchased");
    public final PassengerNotification expiryNotification = new PassengerNotification("Your subscription has been expired", "Subscription Expired");
    public final PassengerNotification pinChangeNotification = new PassengerNotification("Your pin has been changed", "Pin changed");
    public final PassengerNotification repurchaseNoEnoughBalanceNotification = new PassengerNotification("No enough balance on wallet", "Auto Repurchase Fail");
    public final PassengerNotification repurchaseSubscriptionNotification = new PassengerNotification("Couldn't repurchase subscription has been removed", "Auto Repurchase Fail");

//    public FirebaseNotificationService(FirebaseMessaging firebaseMessaging) {
//        this.firebaseMessaging = firebaseMessaging;
//    }


    public void notifyPassenger(String username, PassengerNotification notification) {
        try {
            firebaseMessaging.send(Message.builder()
                    .setTopic(username).setAndroidConfig(AndroidConfig.builder().setNotification(
                            AndroidNotification.builder().setBody(notification.getMessage()).setTitle(notification.getTitle()).setTag(Utility.getRandomTag()).build()
                    ).build()).build());
        } catch (FirebaseMessagingException ignored) {
        }
    }
}
