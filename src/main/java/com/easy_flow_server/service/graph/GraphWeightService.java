package com.easy_flow_server.service.graph;

import com.easy_flow_server.error.NotFoundException;

public interface GraphWeightService {
    double getLineWeight(String lineId, String station1, String station2) throws NotFoundException;

    double getOwnerWeight(String ownerId, String station1, String station2) throws NotFoundException;
}
