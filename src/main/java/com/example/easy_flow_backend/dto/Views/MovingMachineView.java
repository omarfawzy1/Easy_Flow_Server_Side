package com.example.easy_flow_backend.dto.Views;

import org.springframework.beans.factory.annotation.Value;

public interface MovingMachineView extends MachineView {
    @Value("#{target.line.name}")
    String getLineName();
}
