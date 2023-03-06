package com.example.easy_flow_backend.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.repos.GraphRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphServiceImpl implements GraphService {
    @Autowired
    private GraphRepo graphRepo;

    public List<Graph> getOwnerGraph(String ownerId) {
        List<Graph> graph = graphRepo.findAllByOwnerId(ownerId);
        return graph;
    }

    public Graph getLineGraph(String ownerId, String lineId) {
        Graph graph = graphRepo.findByOwnerIdAndLineId(ownerId, lineId);
        return graph;
    }


}
