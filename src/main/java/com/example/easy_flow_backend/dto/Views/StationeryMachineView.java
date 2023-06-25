package com.example.easy_flow_backend.dto.Views;

import org.springframework.beans.factory.annotation.Value;

public interface StationeryMachineView extends MachineView {

//    String getStationStationName();
    @Value("#{target.station.stationName}")
    String getStationName();
}
