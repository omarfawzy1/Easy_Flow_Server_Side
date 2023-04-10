package com.example.easy_flow_backend.service.graph;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


public interface SingleLineGraphService {
    double getWeight(String ownerId, String lineId, String station1, String station2);
    String floydGraphWithStation(String ownerId, String lineId);
}
