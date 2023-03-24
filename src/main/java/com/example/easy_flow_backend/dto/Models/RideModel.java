package com.example.easy_flow_backend.dto.Models;

import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.entity.Owner;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RideModel {
    @NotBlank(message = "Username should not be blank")
    @NotNull(message = "username should not be null")
    String username;
    @NotBlank(message = "Machine id should not be blank")
    @NotNull(message = "Machine id should not be null")
    String machineId;

    @NotBlank(message = "Start station should not be blank")
    @NotNull(message = "Start station should not be NULL")
    String startStation;

    String endStation;

    Line line;

    Owner owner;
    @NotNull(message = "Time should not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:MM:SS")
    Date time;

}
