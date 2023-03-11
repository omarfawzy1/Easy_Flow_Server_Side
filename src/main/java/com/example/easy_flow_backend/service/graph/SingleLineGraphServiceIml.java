package com.example.easy_flow_backend.service.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.service.graph.utils.GraphProperties;
import com.example.easy_flow_backend.service.graph.utils.GraphWithStations;

import java.util.List;

public class SingleLineGraphServiceIml extends LineGraphService {
    //TODO be cached
    private GraphWithStations floydGraphWithStation(String ownerId, String lineId) {
        Graph graph = graphService.getLineGraph(ownerId, lineId);
        List<GraphEdge> edges = graphEdgeService.getEdges(graph);
        List<String> stationNames = GraphProperties.getStationNames(edges);
        double[][] gh = GraphProperties.buildGraph(edges, stationNames);
        double[][] floydWarshall = GraphProperties.floydWarshall(gh);

        return new GraphWithStations(floydWarshall, stationNames);
    }


    public double getWeight(String ownerId, String lineId,
                            String station1, String station2) {
        GraphWithStations graph = floydGraphWithStation(ownerId, lineId);
        int idx1 = graph.getStations().indexOf(station1);
        int idx2 = graph.getStations().indexOf(station2);
        return graph.getGraph()[idx1][idx2];
    }


}
