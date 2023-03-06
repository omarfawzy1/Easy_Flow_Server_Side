package com.example.easy_flow_backend.graph;

import com.example.easy_flow_backend.entity.Graph;

import java.util.List;

public interface GraphService {
    List<Graph> getOwnerGraph(String ownerId);

    Graph getLineGraph(String ownerId, String lineId);
}
