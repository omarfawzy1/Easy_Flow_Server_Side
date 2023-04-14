package com.example.easy_flow_backend.dto.Models;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import lombok.Getter;

import java.util.List;
@Getter
public class GraphModel {
    Graph graph;
    List<GraphEdge> graphEdgeList;

}
