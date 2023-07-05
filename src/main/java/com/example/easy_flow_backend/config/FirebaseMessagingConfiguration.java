package com.example.easy_flow_backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseMessagingConfiguration {

    @Bean
    FirebaseMessaging firebaseMessaging()  {
        GoogleCredentials googleCredentials = null;
        try {
            googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder().setCredentials(googleCredentials).build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions, "easy-flow");
        return FirebaseMessaging.getInstance(firebaseApp);
    }




}
