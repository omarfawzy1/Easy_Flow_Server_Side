package com.easy_flow_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class EasyFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyFlowApplication.class, args);
    }
}
