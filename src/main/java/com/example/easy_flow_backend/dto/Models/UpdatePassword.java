package com.example.easy_flow_backend.dto.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
