package com.easy_flow_server.dto.Models;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinModel {
    @Pattern(regexp = "^[0-9]+$", message = "Pin should contains only digits")
    @Size(min = 4,max = 4,message ="pin should only of size 4")
    String pin;
}
