package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.service.graph_services.utils.GraphWithStations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphWeightServiceImpl implements GraphWeightService {


    @Autowired
    private GraphService graphService;

    //for graph with multiple lines
    public double getWeight(String ownerId, String station1, String station2) {
        return getWeight(ownerId, null, station1, station2);
    }

    //for graph with Single line
    public double getWeight(String ownerId, String lineId, String station1, String station2) {

        GraphWithStations graph = graphService.getWeightedGraph(ownerId, lineId);
        int idx1 = graph.getStations().indexOf(station1);
        int idx2 = graph.getStations().indexOf(station2);
        return graph.getGraph()[idx1][idx2];
    }

}
