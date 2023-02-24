package com.example.easy_flow_backend.dto.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @NotNull(message = "Time should not be null")
//    @NotBlank()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:MM:SS")
    LocalTime time;

}
