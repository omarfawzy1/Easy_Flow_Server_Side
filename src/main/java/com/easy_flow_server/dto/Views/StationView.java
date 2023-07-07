package com.easy_flow_server.dto.Views;

import org.springframework.beans.factory.annotation.Value;

public interface StationView {
    @Value("#{target.stationName}")
    String getName();
    Float getLatitude();
    Float getLongitude();
}
