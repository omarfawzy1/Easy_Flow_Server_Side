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

public class AddLineModel {

    @NotBlank(message = "Owner id Should Not be Blank")
    @NotNull(message = "Owner id Should Not be Null")
    String ownerId;
    @NotNull(message = "Price Should Not be Null")
    double price;
    @NotNull(message = "Line Name Should Not be Null")
    @NotBlank(message = "Line Name Should Not be Blank ")
    String lineName;
}
