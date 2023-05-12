package com.example.easy_flow_backend.dto.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketModel {

    String ownerName;
    String lineName;
    long time;
    double price;
    double weight;

}
