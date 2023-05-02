package com.example.easy_flow_backend.unit_test.graph;

import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.service.graph_services.GraphWeightService;
import com.example.easy_flow_backend.unit_test.utility.LineUtility;
import com.example.easy_flow_backend.unit_test.utility.OwnerUtility;
import com.example.easy_flow_backend.unit_test.utility.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
class GraphWeightServiceTest extends ServiceTest {
    @Autowired
    private GraphWeightService graphWeightService;
    Owner owner;
    Line line;
    void clearAll(){
//        ownerRepo.deleteAll();
//        stationRepo.deleteAll();
//        lineRepo.deleteAll();
        graphEdgeRepo.deleteAll();
        graphRepo.deleteAll();
    }
    @BeforeEach
    void setUp() {
        clearAll();
        owner = OwnerUtility.makeOwner();
        ownerRepo.save(owner);
        line = new Line("TestLine", 5, owner);
        ArrayList<Station> stations = LineUtility.makeStations(5);
        stationRepo.saveAll(stations);
        stations.forEach(line::addStation);
        lineRepo.save(line);
    }
    @Test
    void getWeightLinearGraphTest() {
        Graph graph = new Graph();
        graph.setOwner(owner);
        graph.setLine(line);
        graphRepo.save(graph);
        // Graph Edges
        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
        int[][] edges = new int[][]{{0,1},{1,2},{2,3},{3,4}};
        Station[] stations = new Station[line.getStations().size()];
        line.getStations().toArray(stations);
        for (int[] edge : edges) {
            graphEdges.add(new GraphEdge(graph, stations[edge[0]], stations[edge[1]], 1D));
        }
        graphEdgeRepo.saveAll(graphEdges);

        double weight = graphWeightService.getWeight(owner.getId(), line.getId(), stations[0].getStationName(), stations[4].getStationName());
        System.out.println(weight);
        assert weight == 4.0;
    }

    @Test
    void testGetWeight() {
        assert true;
    }
}