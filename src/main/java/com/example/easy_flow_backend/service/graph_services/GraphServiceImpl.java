package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.service.graph_services.utils.GraphProperties;
import com.example.easy_flow_backend.service.graph_services.utils.GraphWithStations;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphServiceImpl implements GraphService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    protected GraphEdgeService graphEdgeService;
    @Autowired
    private LineService lineService;


    private GraphWithStations convertEdgesToGraphWithStations(List<GraphEdge> edges) {

        List<String> stationNames = GraphProperties.getStationNames(edges);
        double[][] gh = GraphProperties.buildGraph(edges, stationNames);
        double[][] floydWarshall = GraphProperties.floydWarshall(gh);

        return new GraphWithStations(floydWarshall, stationNames);
    }


    public GraphWithStations getLineWeightedGraph(String lineId) throws NotFoundException {


        if (Boolean.TRUE.equals(redisTemplate.hasKey(lineId))) {
            return (GraphWithStations) redisTemplate.opsForValue().get(lineId);
        }

        Line line = lineService.getLineById(lineId);
        List<GraphEdge> totalEdges = new ArrayList<>(line.getGraphEdges());

        GraphWithStations graph = convertEdgesToGraphWithStations(totalEdges);
        redisTemplate.opsForValue().set(lineId, graph);
        return graph;
    }

    public GraphWithStations getOwnerWeightedGraph(String ownerId) {

        if (Boolean.TRUE.equals(redisTemplate.hasKey(ownerId))) {
            return (GraphWithStations) redisTemplate.opsForValue().get(ownerId);
        }

        List<Line> lines = lineService.getLinesByOwnerId(ownerId);
        List<GraphEdge> totalEdges = new ArrayList<>();

        for (Line line : lines) {
            totalEdges.addAll(line.getGraphEdges());
        }
        GraphWithStations graph = convertEdgesToGraphWithStations(totalEdges);
        redisTemplate.opsForValue().set(ownerId, graph);
        return graph;
    }

    @Override
    public List<String> getOrderedStationOfLine(String lineId) throws NotFoundException {
        Line line = lineService.getLineById(lineId);
        List<GraphEdge> edges = new ArrayList<>(line.getGraphEdges());
        Map<String, List<String>> mp = new HashMap<>();
        for (GraphEdge edge : edges) {

            String station1 = edge.getFromStation().getStationName();
            String station2 = edge.getToStation().getStationName();


            List<String> l1 = mp.getOrDefault(station1, new ArrayList<>());
            l1.add(station2);
            if (l1.size() == 1) {
                mp.put(station1, l1);
            }

            List<String> l2 = mp.getOrDefault(station2, new ArrayList<>());
            l2.add(station1);

            if (l2.size() == 1) {
                mp.put(station2, l2);
            }
        }
        String rootStation = "";
        for (GraphEdge edge : edges) {

            String station1 = edge.getFromStation().getStationName();
            String station2 = edge.getToStation().getStationName();
            if (mp.get(station1).size() == 1) {
                rootStation = station1;
                break;
            }
            if (mp.get(station2).size() == 1) {
                rootStation = station2;
                break;
            }

        }
        List<String> stationNames = new ArrayList<>(List.of(rootStation));
        String nxt = mp.get(rootStation).get(0);

        while (true) {
            stationNames.add(nxt);

            List<String> children = mp.get(nxt);
            if (children.size() == 1) {
                break;
            }
            for (String child : children) {
                if (!child.equals(stationNames.get(stationNames.size() - 2))) {
                    nxt = child;
                    break;
                }
            }

        }

        return stationNames;
    }

}
