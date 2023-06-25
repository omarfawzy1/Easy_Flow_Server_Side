package com.example.easy_flow_backend.dto.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddStationaryMachineModel {
    @NotBlank(message = "username Should Not be Blank")
    @NotNull(message = "username Should Not be Null")
    String machineName;

    @NotBlank(message = "password Should Not be Blank")
    @NotNull(message = "password Should Not be Null")
    String password;

    @NotBlank(message = "ownerName Should Not be Blank")
    @NotNull(message = "ownerName Should Not be Null")
    String ownerName;

    @NotBlank(message = "station Name Should Not be Blank")
    @NotNull(message = "station Name Should Not be Null")
    String stationName;
}
