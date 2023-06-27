package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.dto.Models.GraphModel;
import com.example.easy_flow_backend.dto.Models.Pair;
import com.example.easy_flow_backend.dto.Models.StationModel;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.entity.Station;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.StationRepo;
import com.example.easy_flow_backend.service.graph_services.utils.GraphProperties;
import com.example.easy_flow_backend.service.graph_services.utils.GraphWithStations;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraphServiceImpl implements GraphService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    protected GraphEdgeService graphEdgeService;
    @Autowired
    private LineService lineService;
    @Autowired
    private StationRepo stationRepo;

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
    public ResponseMessage addGraph(GraphModel graphModel) {
        Line line;
        try {
            line = lineService.getLineByName(graphModel.getLineName());
        } catch (NotFoundException e) {
            return new ResponseMessage("Invalid Line Name", HttpStatus.BAD_REQUEST);
        }

        List<StationModel> modelStations = graphModel.getStations();
        List<Station> stations = new ArrayList<>();
        for (StationModel stationModel : modelStations) {
            Station station;
            if (stationRepo.existsByStationNameIgnoreCase(stationModel.getName())) {
                station = stationRepo.findByStationNameIgnoreCase(stationModel.getName());
            } else {
                station = new Station(stationModel.getName());
            }
            if (station.getLatitude() == null || station.getLongitude()==null) {
                station.setLatitude(stationModel.getLatitude());
                station.setLongitude(stationModel.getLongitude());
            }
            stations.add(station);
        }
        stations = stationRepo.saveAll(stations);
        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
        for (int i = 0; i < stations.size() - 1; i++) {
            graphEdges.add(new GraphEdge(line, stations.get(i), stations.get(i + 1), (Double) graphModel.getWeights().get(i)));
        }

        try {
            graphEdgeService.addEdges(graphEdges);
        } catch (Exception ex) {
            return new ResponseMessage("Field, something wrong happen", HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("Added Successfully", HttpStatus.OK);
    }


    @Override
    public Pair<List<String>, List<Number>> getOrderedStationOfLine(String lineName) throws NotFoundException {

        Line line = lineService.getLineByName(lineName);
        if (line == null) {
            throw new NotFoundException("Invalid Line Name");
        }
        List<GraphEdge> edges = new ArrayList<>(line.getGraphEdges());
        if (edges.isEmpty()) {
            return new Pair<>();
        }
        Map<String, List<Pair<String, Number>>> mp = new HashMap<>();
        for (GraphEdge edge : edges) {

            String station1 = edge.getFromStation().getStationName();
            String station2 = edge.getToStation().getStationName();
            Number weight = edge.getWeight();

            List<Pair<String, Number>> l1 = mp.getOrDefault(station1, new ArrayList<>());
            l1.add(new Pair<>(station2, weight));
            if (l1.size() == 1) {
                mp.put(station1, l1);
            }

            List<Pair<String, Number>> l2 = mp.getOrDefault(station2, new ArrayList<>());
            l2.add(new Pair<>(station1, weight));

            if (l2.size() == 1) {
                mp.put(station2, l2);
            }
        }
        String rootStation = "";
        for (GraphEdge edge : edges) {

            String station1 = edge.getFromStation().getStationName();
            if (mp.get(station1).size() == 1) {
                rootStation = station1;
                break;
            }


        }
        List<String> stationNames = new ArrayList<>();
        stationNames.add(rootStation);
        List<Number> weights = new ArrayList<>();

        Pair<String, Number> nxt = mp.get(rootStation).get(0);
        int x = 0;
        while (true) {
            System.out.println(++x);
            stationNames.add(nxt.getFirst());
            weights.add(nxt.getSecond());

            List<Pair<String, Number>> children = mp.get(nxt.getFirst());

            if (children.size() == 1) {
                break;
            }

            for (Pair<String, Number> child : children) {
                if (!child.getFirst().equals(stationNames.get(stationNames.size() - 2))) {
                    nxt = child;
                    break;
                }
            }


        }

        return new Pair<>(stationNames, weights);
    }

}
