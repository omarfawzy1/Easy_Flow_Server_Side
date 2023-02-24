package com.example.easy_flow_backend.Dto.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class TimePeriod {
    @NotNull(message = "start date Should Not be Null :)")
    @NotBlank(message = "end date Should Not be Blank :)")
    Date start;
    @NotNull(message = "start date Should Not be Null :)")
    @NotBlank(message = "end date Should Not be Blank :)")
    Date end;

    public TimePeriod(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
