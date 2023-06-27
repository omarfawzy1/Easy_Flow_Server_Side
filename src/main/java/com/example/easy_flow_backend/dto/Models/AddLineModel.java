package com.example.easy_flow_backend.dto.Models;

import com.example.easy_flow_backend.entity.TransportationType;
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

    @NotBlank(message = "Owner name Should Not be Blank")
    @NotNull(message = "Owner name Should Not be Null")
    String ownerName;
    @NotNull(message = "Price Should Not be Null")
    String type;
    @NotNull(message = "Line Name Should Not be Null")
    @NotBlank(message = "Line Name Should Not be Blank ")
    String lineName;
}
