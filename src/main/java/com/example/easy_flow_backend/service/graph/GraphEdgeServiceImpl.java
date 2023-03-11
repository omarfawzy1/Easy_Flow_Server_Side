package com.example.easy_flow_backend.service.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.repos.GraphEdgeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphEdgeServiceImpl implements GraphEdgeService {
    @Autowired
    private GraphEdgeRepo graphEdgeRepo;

    public List<GraphEdge> getEdges(Graph graph) {
        List<GraphEdge> graphEdges = graphEdgeRepo.findAllByGraph(graph);
        return graphEdges;
    }

}
