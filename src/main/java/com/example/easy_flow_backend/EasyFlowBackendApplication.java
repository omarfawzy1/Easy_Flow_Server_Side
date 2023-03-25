package com.example.easy_flow_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class EasyFlowBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyFlowBackendApplication.class, args);

    }
}
