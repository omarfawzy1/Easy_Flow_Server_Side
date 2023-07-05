package com.example.easy_flow_backend.dto.Models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanModel {
    @NotBlank(message = "owner name should not blank")
    @NotNull(message = "owner name should not null")
    private String ownerName;

    @NotBlank(message = "privilege name should not blank")
    @NotNull(message = "privilege name should not null")
    private String privilegeName;

    @Min(0)
    private float price;
    @Min(1)
    private int durationDays;

    @Min(1)
    private int trips;

    @NotNull(message = "Plan name is mandatory")
    @NotBlank(message = "Plan name must not blank")
    private String name;

    @Min(1)
    private int maxCompanion;

    @Range(min = 0, max = 1)
    private float discountRate;

}
