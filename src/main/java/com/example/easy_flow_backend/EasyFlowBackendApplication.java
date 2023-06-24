package com.example.easy_flow_backend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

// exclude = {DataSourceAutoConfiguration.class }
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class EasyFlowBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyFlowBackendApplication.class, args);
    }
}
