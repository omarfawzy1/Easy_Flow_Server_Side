package com.example.easy_flow_backend.dto.Models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPassword {
    @NotNull
    String newPassword;
    @NotNull
    String reNewPassword;

}
