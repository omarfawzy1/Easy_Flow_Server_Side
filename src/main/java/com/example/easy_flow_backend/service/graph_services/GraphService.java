package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.service.graph_services.utils.GraphWithStations;

import java.util.List;

public interface GraphService {
    List<Graph> getOwnerGraph(String ownerId);

    Graph getLineGraph(String ownerId, String lineId);

    List<Graph> getOwnerLineGraph(String ownerId, String lineId);

    GraphWithStations getWeightedGraph(String ownerId, String lineId);

    boolean addGraph(Graph graph);

    List<String> getOrderedStationOfLine( String lineId);
}
