package com.example.easy_flow_backend.dto.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GraphModel  {
    @NotNull(message = "Line name must not be Null")
    @NotBlank(message = "Line name must not be Empty")
    String lineName;

    @NotNull(message = "Owner Name must not be Null")
    @NotBlank(message = "Owner Name must not be Empty")
    String ownerName;
    @NotNull(message = "Stations Name must not be Null")
    List<StationModel> stations;
    @NotNull(message = "weights must not be Null")
    List<Double> weights;
}
