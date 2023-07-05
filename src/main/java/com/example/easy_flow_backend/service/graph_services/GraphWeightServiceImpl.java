package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.service.graph_services.utils.GraphWithStations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphWeightServiceImpl implements GraphWeightService {


    private final GraphService graphService;

    public GraphWeightServiceImpl(GraphService graphService) {
        this.graphService = graphService;
    }

    private void validateStationsIndexes(int idx1, int idx2) throws NotFoundException {
        if (idx1 == -1) {
            throw new NotFoundException("Station one not belong to the line.");
        }
        if (idx2 == -1) {
            throw new NotFoundException("Station two not belong to the line.");
        }
    }

    //for graph with multiple lines
    public double getOwnerWeight(String ownerId, String station1, String station2) throws NotFoundException {
        GraphWithStations graph = graphService.getOwnerWeightedGraph(ownerId);
        int idx1 = graph.getStations().indexOf(station1);
        int idx2 = graph.getStations().indexOf(station2);
        validateStationsIndexes(idx1, idx2);
        return graph.getGraph()[idx1][idx2];
    }


    //for graph with Single line
    public double getLineWeight(String lineId, String station1, String station2) throws NotFoundException {

        GraphWithStations graph = graphService.getLineWeightedGraph(lineId);
        int idx1 = graph.getStations().indexOf(station1);
        int idx2 = graph.getStations().indexOf(station2);
        validateStationsIndexes(idx1, idx2);
        return graph.getGraph()[idx1][idx2];
    }

}
