package com.example.easy_flow_backend.dto.Views;

import java.util.Set;

public interface LiveWithStationsView {
    String getName();
    String getType();
    OwnerView getOwner();
    Set<StationView> getStations();
}
