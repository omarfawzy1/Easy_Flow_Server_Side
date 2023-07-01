package com.example.easy_flow_backend.dto.Models;

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
