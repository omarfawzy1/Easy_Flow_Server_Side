package com.easy_flow_server.dto.view;

import org.springframework.beans.factory.annotation.Value;

public interface StationeryMachineView extends MachineView {

//    String getStationStationName();
    @Value("#{target.station.stationName}")
    String getStationName();
}
