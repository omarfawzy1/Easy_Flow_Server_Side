package com.easy_flow_server.dto.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassword {
    @NotNull(message = "Old password should not be null")
    @NotBlank(message = "Old password should not be blank")
    String oldPassword;
    @NotNull(message = "Reset password should not be null")
    ResetPassword resetPassword;
}
