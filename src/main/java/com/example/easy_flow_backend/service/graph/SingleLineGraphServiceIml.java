package com.example.easy_flow_backend.service.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.service.graph.utils.GraphProperties;
import com.example.easy_flow_backend.service.graph.utils.GraphWithStations;
import com.google.gson.Gson;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SingleLineGraphServiceIml extends LineGraphService implements SingleLineGraphService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    //TODO be cached
    @Cacheable(value = "ss", key = "#ownerId +'-'+#lineId")
    public String floydGraphWithStation(String ownerId, String lineId) {
        Graph graph = graphService.getLineGraph(ownerId, lineId);
        List<GraphEdge> edges = graphEdgeService.getEdges(graph);
        List<String> stationNames = GraphProperties.getStationNames(edges);
        double[][] gh = GraphProperties.buildGraph(edges, stationNames);
        double[][] floydWarshall = GraphProperties.floydWarshall(gh);

        System.out.println("inside cache");

        Gson gson = new Gson();
        GraphWithStations g = new GraphWithStations(floydWarshall, stationNames);


        return gson.toJson(g);
    }

    public double getWeight(String ownerId, String lineId,
                            String station1, String station2) {
        String key = ownerId + '-' + lineId;
        String jsonGraph;

        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            jsonGraph = floydGraphWithStation(ownerId, lineId);
            redisTemplate.opsForValue().set(key, jsonGraph);
        } else {
            jsonGraph = (String) redisTemplate.opsForValue().get(key);
        }
        Gson gson = new Gson();
        GraphWithStations graph = gson.fromJson(jsonGraph, GraphWithStations.class);
        int idx1 = graph.getStations().indexOf(station1);
        int idx2 = graph.getStations().indexOf(station2);
        return graph.getGraph()[idx1][idx2];
    }


}
//2e932805-77b8-4a3e-b95e-5c958b3e6215-36ff1465-e94c-4420-9e65-65fa2c4f2246