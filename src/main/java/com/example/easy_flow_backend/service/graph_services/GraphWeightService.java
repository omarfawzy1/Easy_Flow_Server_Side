package com.example.easy_flow_backend.service.graph_services;

public interface GraphWeightService {

    double getWeight(String ownerId, String station1, String station2);
    double getWeight(String ownerId, String lineId, String station1, String station2);
}
