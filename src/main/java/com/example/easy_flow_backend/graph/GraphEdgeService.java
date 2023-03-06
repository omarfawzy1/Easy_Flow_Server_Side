package com.example.easy_flow_backend.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;

import java.util.List;

public interface GraphEdgeService {
    List<GraphEdge> getEdges(Graph graph);
}
