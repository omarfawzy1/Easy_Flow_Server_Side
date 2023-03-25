package com.example.easy_flow_backend.service.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.service.graph.utils.GraphProperties;
import com.example.easy_flow_backend.service.graph.utils.GraphWithStations;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Thread.sleep;

@Service
public class SingleLineGraphServiceIml extends LineGraphService implements SingleLineGraphService {
    //TODO be cached
    @Cacheable(value = "ss", key = "#ownerId +'-'+#lineId")
    public GraphWithStations floydGraphWithStation(String ownerId, String lineId) {
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

    @Cacheable(value = "fun", key = "#id")
    public int fun(int id) {
        long curr = System.currentTimeMillis();

        try {
            sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.currentTimeMillis() - curr);
        return id + 1;
    }

}
//2e932805-77b8-4a3e-b95e-5c958b3e6215-36ff1465-e94c-4420-9e65-65fa2c4f2246