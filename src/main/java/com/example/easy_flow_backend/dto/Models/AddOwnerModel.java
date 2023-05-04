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

public class AddOwnerModel {
    @NotBlank(message = "Owner name Should Not be Blank")
    @NotNull(message = "Owner name Should Not be Null")
    String name;
    @NotBlank(message = "Owner mail Should Not be Blank")
    @NotNull(message = "Owner mail Should Not be Null")
    String mail;
    @NotBlank(message = "Owner bank account Should Not be Blank")
    @NotNull(message = "Owner bank account Should Not be Null")
    String bankAccount;

}
