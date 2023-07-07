package com.easy_flow_server.service.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerNotification {
    String message;
    String title;
}
