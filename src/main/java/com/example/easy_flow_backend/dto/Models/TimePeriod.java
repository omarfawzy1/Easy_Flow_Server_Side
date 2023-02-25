package com.example.easy_flow_backend.dto.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimePeriod {
    @NotNull(message = "start date Should Not be Null :)")
    Date start;
    @NotNull(message = "start date Should Not be Null :)")
    Date end;
}
