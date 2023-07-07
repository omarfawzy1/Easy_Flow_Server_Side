package com.easy_flow_server.dto.Views;

import org.springframework.beans.factory.annotation.Value;

public interface MovingMachineView extends MachineView {
    @Value("#{target.line.name}")
    String getLineName();
}
