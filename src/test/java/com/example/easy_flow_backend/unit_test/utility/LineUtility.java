package com.example.easy_flow_backend.unit_test.utility;

import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.service.graph.GraphWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class LineUtility {
    private static String[] generateStationNames(int count){
        String[] stationNames = new String[count];
        for(int i = 0; i < count; i++){
            stationNames[i] = Integer.toString(i);
        }
        return stationNames;
    }
    public static ArrayList<Station> makeStations(int stationsCount){
        ArrayList<Station> stations = new ArrayList<>();
        String[] stationNames = generateStationNames(stationsCount);
        for(String station_name : stationNames){
            Station station = new Station(station_name);
            station.setTransportationType(TransportationType.BUS);
            stations.add(station);
        }
        return stations;
    }
    public Line makeBusLineWithStations(Owner owner, String lineName, int stationsCount){
        Line line = new Line(lineName, 10, owner);
        ArrayList<Station> stations = makeStations(stationsCount);
        //stationRepo.saveAll(stations);
        stations.forEach(line::addStation);
        //lineRepo.save(line);
        return line;
    }
    public Line makeBusLine(Owner owner, String lineName){
        Line line = new Line("BusLine", 10, owner);
        ArrayList<Station> stations = new ArrayList<>();
        String[] stationNames = generateStationNames(5);
        for(String station_name : stationNames){
            Station station = new Station(station_name);
            station.setTransportationType(TransportationType.BUS);
            stations.add(station);
        }
//        stationRepo.saveAll(stations);
//        stations.forEach(line::addStation);
//        lineRepo.save(line);
//        // Graph
//        Graph graph = new Graph();
//        graph.setOwner(owner);
//        graph.setLine(line);
//        graphRepo.save(graph);
//        // Graph Edges
//        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
//        for (int i = 0; i < stations.size() - 1; i++) {
//            graphEdges.add(new GraphEdge(graph, stations.get(i), stations.get(i + 1), 1D));
//        }
//        graphEdgeRepo.saveAll(graphEdges);

        // Turnstiles
//        Map<Integer, MovingTurnstile> movingTurnstiles = new HashMap<>();
//        for (int i = 0; i < 10; i++) {
//            MovingTurnstile bus = new MovingTurnstile("m7_" + i, passwordEncoder.encode("1234"));
//            bus.setLine(line);
//            movingTurnstiles.put(i, bus);
//            userRepositry.save(bus);
//        }

        return line;
    }
}
