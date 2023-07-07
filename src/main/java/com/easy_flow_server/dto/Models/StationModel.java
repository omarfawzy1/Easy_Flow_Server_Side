package com.easy_flow_server.dto.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationModel {
    String name;
    Float latitude;
    Float longitude;

}
