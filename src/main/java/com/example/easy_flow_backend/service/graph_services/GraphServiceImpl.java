package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.repos.GraphRepo;
import com.example.easy_flow_backend.service.graph_services.utils.GraphProperties;
import com.example.easy_flow_backend.service.graph_services.utils.GraphWithStations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphServiceImpl implements GraphService {
    @Autowired
    private GraphRepo graphRepo;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    protected GraphEdgeService graphEdgeService;

    public List<Graph> getOwnerGraph(String ownerId) {
        return graphRepo.findAllByOwnerId(ownerId);
    }

    public Graph getLineGraph(String ownerId, String lineId) {
        return graphRepo.findByOwnerIdAndLineId(ownerId, lineId);
    }

    @Override
    public List<Graph> getOwnerLineGraph(String ownerId, String lineId) {
        return graphRepo.findAllGraphsByOwnerIdAndLineIdOrOwnerId(ownerId, lineId);
    }

    private GraphWithStations convertEdgesToGraphWithStations(List<GraphEdge> edges) {

        List<String> stationNames = GraphProperties.getStationNames(edges);
        double[][] gh = GraphProperties.buildGraph(edges, stationNames);
        double[][] floydWarshall = GraphProperties.floydWarshall(gh);

        return new GraphWithStations(floydWarshall, stationNames);
    }

    public GraphWithStations getWeightedGraph(String ownerId, String lineId) {
        String key = ownerId + '-' + lineId;
//        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
//            return (GraphWithStations) redisTemplate.opsForValue().get(key);
//        }

        List<Graph> graphs = getOwnerLineGraph(ownerId, lineId);
        List<GraphEdge> totalEdges = new ArrayList<>();
        for (Graph graph : graphs) {
            List<GraphEdge> edges = graphEdgeService.getEdges(graph);
            totalEdges.addAll(edges);
        }
        GraphWithStations graph = convertEdgesToGraphWithStations(totalEdges);
//        redisTemplate.opsForValue().set(key, graph);
        return graph;
    }


    @Override
    public boolean addGraph(Graph graph) {
        graphRepo.save(graph);
        return true;
    }

    @Override
    public List<String> getOrderedStationOfLine(String lineId) {
        Graph graph = graphRepo.findByLineId(lineId);
        List<GraphEdge> edges = graphEdgeService.getEdges(graph);
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
