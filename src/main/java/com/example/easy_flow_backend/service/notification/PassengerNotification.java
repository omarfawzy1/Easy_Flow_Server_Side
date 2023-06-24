package com.example.easy_flow_backend.service.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerNotification {
    String message;
    String title;
}
