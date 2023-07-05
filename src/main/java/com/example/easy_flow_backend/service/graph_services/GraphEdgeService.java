package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.entity.Line;

import java.util.List;
import java.util.Set;

public interface GraphEdgeService {
    List<GraphEdge> getEdges(Line line);

    void deleteEdges(Set<GraphEdge> graphEdges);

    boolean addEdges(List<GraphEdge> edges);

    void deleteEdge(String id);
}
