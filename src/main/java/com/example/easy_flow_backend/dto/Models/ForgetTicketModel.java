package com.example.easy_flow_backend.dto.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetTicketModel {
    @NotBlank(message = "phone number should not be blank")
    @NotNull(message = "phone number should not be null")
    String phoneNumber;
    int pin;
    @NotNull(message = "Time should not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    Date time;
}
