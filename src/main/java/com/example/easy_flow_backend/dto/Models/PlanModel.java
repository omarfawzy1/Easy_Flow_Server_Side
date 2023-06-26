package com.example.easy_flow_backend.dto.Models;

import com.example.easy_flow_backend.entity.Privilege;
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
    @NotBlank(message = "owner name must not null")
    @NotNull(message = "owner name is mandatory")
    private String ownerName;

    @NotNull(message = "privilege is mandatory")
    private Privilege privilege;

    @NotNull(message = "price is mandatory")
    @Min(0)
    private float price;

    @NotNull(message = "duration days is mandatory")
    private int durationDays;

    @NotNull(message = "Number of Trips is mandatory")
    @Min(1)
    private int trips;

    @NotNull(message = "Plan name is mandatory")
    @NotBlank(message = "Plan name must not blank")
    private String name;

    @Min(1)
    private int maxCompanion;

    @NotNull(message = "Discount Rate is mandatory")
    @Range(min = 0, max = 1)
    private float discountRate;

}
