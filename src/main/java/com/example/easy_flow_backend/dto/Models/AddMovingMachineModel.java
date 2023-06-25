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
public class AddMovingMachineModel {
    @NotBlank(message = "machineName Should Not be Blank")
    @NotNull(message = "machineName Should Not be Null")
    String machineName;

    @NotBlank(message = "password Should Not be Blank")
    @NotNull(message = "password Should Not be Null")
    String password;

    @NotBlank(message = "owner name Should Not be Blank")
    @NotNull(message = "owner name Should Not be Null")
    String ownerName;

    @NotBlank(message = "line name Should Not be Blank")
    @NotNull(message = "line name Should Not be Null")
    String lineName;
}
