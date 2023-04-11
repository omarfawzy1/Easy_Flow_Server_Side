package com.example.easy_flow_backend.service.graph;

import com.example.easy_flow_backend.service.graph.utils.GraphWithStations;

public interface GraphWeightService {

    double getWeight(String ownerId, String station1, String station2);
    double getWeight(String ownerId, String lineId, String station1, String station2);
}
