package com.easy_flow_server.service.notification;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class FirebaseNotificationService {
    private final FirebaseMessaging firebaseMessaging;
    public final PassengerNotification repurchaseNotification = new PassengerNotification("Subscription repurchased successfully", "Subscription Repurchased");
    public final PassengerNotification expiryNotification = new PassengerNotification("Your subscription has been expired", "Subscription Expired");
    public final PassengerNotification repurchaseSubscriptionNotification = new PassengerNotification("Couldn't repurchase subscription has been removed", "Auto Repurchase Fail");
    public final PassengerNotification repurchaseNoEnoughBalanceNotification = new PassengerNotification("No enough balance on wallet", "Auto Repurchase Fail");

    public FirebaseNotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendAnyMessage(){
//        Message message = Message.builder().setTopic("main_topic")
//                .putData("body","test")
//                .setNotification(
//                        Notification.builder()
//                                .setBody("test1").setTitle("test Title").build()).build();
        try {
            return firebaseMessaging.send(Message.builder()
                    .setTopic("main_topic").setAndroidConfig(AndroidConfig.builder().setNotification(
                            AndroidNotification.builder().setBody("Android Body").setTitle("Title One").build()
                    ).build()).build());
//            return firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            return e.getMessage();
//            throw new RuntimeException(e);
        }
    }

    public boolean notifyPassenger(String username, PassengerNotification notification){
        try {
            firebaseMessaging.send(Message.builder()
                    .setTopic(username).setAndroidConfig(AndroidConfig.builder().setNotification(
                            AndroidNotification.builder().setBody(notification.getMessage()).setTitle(notification.getTitle()).build()
                    ).build()).build());
            return true;
//            return firebaseMessaging.send(message);
        }
        catch (FirebaseMessagingException e) {
            // log
            return false;
//            throw new RuntimeException(e);
        }
    }
}
