package com.example.easy_flow_backend.dto.Views;

import org.springframework.beans.factory.annotation.Value;

public interface StationView {
    @Value("#{target.stationName}")
    String getName();
    Float getLatitude();
    Float getLongitude();
}
