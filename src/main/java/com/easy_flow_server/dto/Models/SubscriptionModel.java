package com.easy_flow_server.dto.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionModel {
    String owner_name;
    String plan_name;
}
