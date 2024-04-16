package com.easy_flow_server.dto.view;

import java.util.Set;

public interface LiveWithStationsView {
    String getName();
    String getType();
    OwnerView getOwner();
    Set<StationView> getStations();
}
