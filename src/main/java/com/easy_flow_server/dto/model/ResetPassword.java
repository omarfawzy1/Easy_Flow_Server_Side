package com.easy_flow_server.dto.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPassword {
    @NotNull
    String newPassword;
    @NotNull
    String reNewPassword;

}
