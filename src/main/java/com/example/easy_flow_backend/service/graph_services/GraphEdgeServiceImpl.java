package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.repos.GraphEdgeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GraphEdgeServiceImpl implements GraphEdgeService {
    @Autowired
    private GraphEdgeRepo graphEdgeRepo;

    public List<GraphEdge> getEdges(Line line) {
        return graphEdgeRepo.findAllByLine(line);
    }

    @Override
    public void deleteEdges(Set<GraphEdge> graphEdges) {
        graphEdgeRepo.deleteAll(graphEdges);

    }

    @Override
    public boolean addEdges(List<GraphEdge> edges) {
        graphEdgeRepo.saveAll(edges);
        return true;
    }

    @Override
    public void deleteEdge(String id) {
        graphEdgeRepo.deleteById(id);
    }


}
