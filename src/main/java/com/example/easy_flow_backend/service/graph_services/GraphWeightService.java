package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.error.NotFoundException;

public interface GraphWeightService {
    double getLineWeight(String lineId, String station1, String station2) throws NotFoundException;

    double getOwnerWeight(String ownerId, String station1, String station2) throws NotFoundException;
}
