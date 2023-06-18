package com.example.easy_flow_backend.dto.Models;

import com.example.easy_flow_backend.entity.PassengerPrivilege;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanModel {
    private String ownerName;
    private PassengerPrivilege privilege;
    private float price;
    private int durationDays;
    private int trips;
    private String name;
    private int maxCompanion;
    private float discountRate;
}
