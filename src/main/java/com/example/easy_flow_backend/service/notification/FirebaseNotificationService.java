package com.example.easy_flow_backend.service.notification;

import com.google.firebase.messaging.*;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import org.checkerframework.checker.units.qual.Acceleration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseNotificationService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendAnyMessage(){
        Message message = Message.builder().setTopic("main_topic")
                .putData("body","test")
                .setNotification(
                        Notification.builder()
                                .setBody("test1").setTitle("test Title").build()).build();
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

}
