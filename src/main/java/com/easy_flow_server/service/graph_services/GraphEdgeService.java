package com.easy_flow_server.service.graph_services;

import com.easy_flow_server.entity.GraphEdge;
import com.easy_flow_server.entity.Line;

import java.util.List;
import java.util.Set;

public interface GraphEdgeService {
    List<GraphEdge> getEdges(Line line);

    void deleteEdges(Set<GraphEdge> graphEdges);

    boolean addEdges(List<GraphEdge> edges);

    void deleteEdge(String id);
}
