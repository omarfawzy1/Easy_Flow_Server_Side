package com.example.easy_flow_backend.dto.Models;

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
