package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.entity.Line;

import java.util.List;

public interface GraphEdgeService {
    List<GraphEdge> getEdges(Line line);

    boolean addEdges(List<GraphEdge> edges);
}
