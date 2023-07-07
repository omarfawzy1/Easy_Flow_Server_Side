package com.easy_flow_server.service.graph_services;

import com.easy_flow_server.entity.GraphEdge;
import com.easy_flow_server.entity.Line;
import com.easy_flow_server.repos.GraphEdgeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GraphEdgeServiceImpl implements GraphEdgeService {
    private final GraphEdgeRepo graphEdgeRepo;

    public GraphEdgeServiceImpl(GraphEdgeRepo graphEdgeRepo) {
        this.graphEdgeRepo = graphEdgeRepo;
    }

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
