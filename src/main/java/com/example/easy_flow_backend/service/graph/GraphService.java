package com.example.easy_flow_backend.service.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.service.graph.utils.GraphWithStations;

import java.util.List;

public interface GraphService {
    List<Graph> getOwnerGraph(String ownerId);

    Graph getLineGraph(String ownerId, String lineId);

    List<Graph> getOwnerLineGraph(String ownerId, String lineId);

    GraphWithStations getWeightedGraph(String ownerId, String lineId);
}
