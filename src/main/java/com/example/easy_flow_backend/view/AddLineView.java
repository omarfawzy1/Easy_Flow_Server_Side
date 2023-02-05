package com.example.easy_flow_backend.view;

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

public class AddLineView {

    @NotBlank
    @NotNull
    String ownerId;
    @NotBlank
    @NotNull
    float price;
    @NotBlank
    @NotNull
    String lineName;
}
