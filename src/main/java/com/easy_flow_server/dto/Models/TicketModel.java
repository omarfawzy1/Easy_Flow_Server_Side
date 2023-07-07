package com.easy_flow_server.dto.Models;

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
