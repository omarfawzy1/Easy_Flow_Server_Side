package com.example.easy_flow_backend.service.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.service.graph.utils.GraphProperties;
import com.example.easy_flow_backend.service.graph.utils.GraphWithStations;

import java.util.ArrayList;
import java.util.List;


public class MultipleLineGraphService extends LineGraphService {
    //TODO to be cached
    public GraphWithStations floydGraphWithStation(String ownerId) {
        List<Graph> graphs = graphService.getOwnerGraph(ownerId);
        List<GraphEdge> totalEdges = new ArrayList<>();
        for (Graph graph : graphs) {
            List<GraphEdge> edges = graphEdgeService.getEdges(graph);
            totalEdges.addAll(edges);
        }

        List<String> stationNames = GraphProperties.getStationNames(totalEdges);
        double[][] gh = GraphProperties.buildGraph(totalEdges, stationNames);
        double[][] floydWarshall = GraphProperties.floydWarshall(gh);

        return new GraphWithStations(floydWarshall, stationNames);
    }

    public double getWeight(String ownerId, String station1, String station2) {
        GraphWithStations graph = floydGraphWithStation(ownerId);
        int idx1 = graph.getStations().indexOf(station1);
        int idx2 = graph.getStations().indexOf(station2);
        return graph.getGraph()[idx1][idx2];
    }


}
