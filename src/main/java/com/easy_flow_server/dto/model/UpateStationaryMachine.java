package com.easy_flow_server.dto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpateStationaryMachine {

    @NotBlank(message = "username must not be blank")
    @NotNull(message = "username must not be null")
    String username;

    @NotBlank(message = "new station must not be blank")
    @NotNull(message = "new Station must not be null")
    String newStation;
}
