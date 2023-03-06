package com.example.easy_flow_backend.graph;

import com.example.easy_flow_backend.entity.Graph;
import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.repos.GraphEdgeRepo;
import com.example.easy_flow_backend.repos.GraphRepo;
import com.example.easy_flow_backend.repos.StationRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphPoperties {
    @Autowired
    private GraphService graphService;
    @Autowired
    private GraphEdgeService graphEdgeService;
    @Autowired
    private GraphRepo graphRepo;
    @Autowired
    private GraphEdgeRepo graphEdgeRepo;
    @Autowired
    private StationRepo stationRepo;

    public Pair<List<GraphEdge>, List<String>> getEdges(String ownerId) {

        List<Graph> graphs = graphService.getOwnerGraph(ownerId);
        List<GraphEdge> edges = new ArrayList<>();
        for (Graph graph : graphs) {
            List<GraphEdge> graphEdges = graphEdgeService.getEdges(graph);
            edges.addAll(graphEdges);
        }

        return getListListPair(edges);

    }

    public Pair<List<GraphEdge>, List<String>> getEdges(String ownerId, String lineId) {

        Graph graph = graphService.getLineGraph(ownerId, lineId);

        List<GraphEdge> edges = graphEdgeService.getEdges(graph);


        return getListListPair(edges);

    }

    @NotNull
    private Pair<List<GraphEdge>, List<String>> getListListPair(List<GraphEdge> edges) {
        Set<String> st = new HashSet<>();
        for (GraphEdge graphEdge : edges) {
            st.add(graphEdge.getFromStation().getStationName());
            st.add(graphEdge.getToStation().getStationName());
        }

        List<String> stationNames = new ArrayList<>(st);
        Pair<List<GraphEdge>, List<String>> ret = new Pair<>(edges, stationNames);
        return ret;
    }

    public double[][] buildGraph(List<GraphEdge> edges, List<String> stationNames) {
        Map<String, Integer> mp = new HashMap<>();
        int sz = stationNames.size();
        for (int i = 0; i < sz; i++) {
            mp.put(stationNames.get(i), i);
        }

        double[][] gp = new double[sz][sz];
        for (GraphEdge graphEdge : edges) {
            int station1id = mp.get(graphEdge.getFromStation().getStationName());
            int station2id = mp.get(graphEdge.getFromStation().getStationName());
            double weight = graphEdge.getWeight();
            gp[station1id][station2id] = weight;
            gp[station2id][station1id] = weight;
        }
        return gp;
    }


    double[][] floydWarshall(double[][] graph) {
        int nV = graph.length;
        final double INF = 1e8;
        double[][] matrix = new double[nV][nV];
        int i, j, k;

        for (i = 0; i < nV; i++)
            for (j = 0; j < nV; j++) {
                if (i == j)
                    matrix[i][j] = 0;
                else if (graph[i][j] != 0.0)
                    matrix[i][j] = graph[i][j];
                else
                    matrix[i][j] = INF;
            }

        // Adding vertices individually
        for (k = 0; k < nV; k++) {
            for (i = 0; i < nV; i++) {
                for (j = 0; j < nV; j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j])
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                }
            }
        }
        return matrix;
    }

}
