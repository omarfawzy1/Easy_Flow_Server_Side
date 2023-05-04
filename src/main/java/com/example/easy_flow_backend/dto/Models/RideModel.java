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

    @NotBlank(message = "Start Station should not be blank")
    @NotNull(message = "Start Station should not be null")
    private String startStation;

    @NotBlank(message = "End Station should not be blank")
    @NotNull(message = "End Station should not be null")
    private String endStation;

    @NotNull(message = "Time should not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    Date time;

    String token;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    Date generationTime;
}
