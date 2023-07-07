package com.easy_flow_server.dto.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateMovingMachine {
    @NotBlank(message = "username must not be blank")
    @NotNull(message = "username must not be null")
    String username;

    @NotBlank(message = "new line must not be blank")
    @NotNull(message = "new line must not be null")
    String newLine;
}
