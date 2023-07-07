package com.easy_flow_server.service.graph_services;

import com.easy_flow_server.dto.Models.GraphModel;
import com.easy_flow_server.dto.Models.Pair;
import com.easy_flow_server.entity.GraphEdge;
import com.easy_flow_server.entity.Line;
import com.easy_flow_server.entity.Station;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.StationRepo;
import com.easy_flow_server.dto.Models.StationModel;
import com.easy_flow_server.service.graph_services.utils.GraphProperties;
import com.easy_flow_server.service.graph_services.utils.GraphWithStations;
import com.easy_flow_server.service.station_line_services.LineService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphServiceImpl implements GraphService {

    private final RedisTemplate<Object, Object> redisTemplate;
    protected final GraphEdgeService graphEdgeService;
    private final LineService lineService;
    private final StationRepo stationRepo;

    public GraphServiceImpl(RedisTemplate<Object, Object> redisTemplate, GraphEdgeService graphEdgeService, LineService lineService, StationRepo stationRepo) {
        this.redisTemplate = redisTemplate;
        this.graphEdgeService = graphEdgeService;
        this.lineService = lineService;
        this.stationRepo = stationRepo;
    }

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

    //concatenate all the lines of the owner with each other like the metro
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
            return new ResponseMessage("Invalid Line Name", HttpStatus.NOT_FOUND);
        }

        List<StationModel> modelStations = graphModel.getStations();
        List<Station> stations = new ArrayList<>();

        //Create Stations if not exist
        for (StationModel stationModel : modelStations) {
            Station station;
            if (stationRepo.existsByStationNameIgnoreCase(stationModel.getName())) {
                station = stationRepo.findByStationNameIgnoreCase(stationModel.getName());
            } else {
                station = new Station(stationModel.getName());
            }
            if (station.getLatitude() == null || station.getLongitude() == null) {
                station.setLatitude(stationModel.getLatitude());
                station.setLongitude(stationModel.getLongitude());
            }
            stations.add(station);
        }

        stations = stationRepo.saveAll(stations);

        List<GraphEdge> graphEdges = new ArrayList<>();

        for (int i = 0; i < stations.size() - 1; i++) {
            graphEdges.add(new GraphEdge(line, stations.get(i), stations.get(i + 1), graphModel.getWeights().get(i)));
        }


        try {
            Set<GraphEdge> oldGraphEdges = line.getGraphEdges();
            for (GraphEdge graphEdge : oldGraphEdges) {
                graphEdgeService.deleteEdge(graphEdge.getId()); //work
            }
            line.setStations(new HashSet<>(stations));
            graphEdgeService.addEdges(graphEdges);

            lineService.saveLine(line);
            if (Boolean.TRUE.equals(redisTemplate.hasKey(line.getOwner().getId()))) {
                redisTemplate.delete(line.getOwner().getId());
            }

            if (Boolean.TRUE.equals(redisTemplate.hasKey(line.getId()))) {
                redisTemplate.delete(line.getId());
            }
        } catch (Exception ex) {
            return new ResponseMessage("Field, something wrong happen", HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("Added Successfully", HttpStatus.OK);
    }

    // Get the Stations and weights of the line ordered, Suppose the line is one way with no loops
    @Override
    public Pair<List<Station>, List<Number>> getOrderedStationOfLine(String lineName) throws NotFoundException {

        Line line = lineService.getLineByName(lineName);
        if (line == null) {
            throw new NotFoundException("Invalid Line Name");
        }
        List<GraphEdge> edges = new ArrayList<>(line.getGraphEdges());
        if (edges.isEmpty()) {
            return new Pair<>(new ArrayList<>(), new ArrayList<>());
        }
        Map<Station, List<Pair<Station, Number>>> mp = new HashMap<>();
        for (GraphEdge edge : edges) {

            Station station1 = edge.getFromStation();
            Station station2 = edge.getToStation();
            Number weight = edge.getWeight();

            List<Pair<Station, Number>> l1 = mp.getOrDefault(station1, new ArrayList<>());
            l1.add(new Pair<>(station2, weight));
            if (l1.size() == 1) {
                mp.put(station1, l1);
            }

            List<Pair<Station, Number>> l2 = mp.getOrDefault(station2, new ArrayList<>());
            l2.add(new Pair<>(station1, weight));

            if (l2.size() == 1) {
                mp.put(station2, l2);
            }
        }
        Station rootStation = null;
        for (GraphEdge edge : edges) {

            Station station1 = edge.getFromStation();
            if (mp.get(station1).size() == 1) {
                rootStation = station1;
                break;
            }
        }

        List<Station> stationNames = new ArrayList<>();
        stationNames.add(rootStation);
        List<Number> weights = new ArrayList<>();

        Pair<Station, Number> nxt = mp.get(rootStation).get(0);
        while (true) {
            stationNames.add(nxt.getFirst());
            weights.add(nxt.getSecond());

            List<Pair<Station, Number>> children = mp.get(nxt.getFirst());

            if (children.size() == 1) {
                break;
            }

            for (Pair<Station, Number> child : children) {
                if (!child.getFirst().equals(stationNames.get(stationNames.size() - 2))) {
                    nxt = child;
                    break;
                }
            }


        }

        return new Pair<>(stationNames, weights);
    }

}
