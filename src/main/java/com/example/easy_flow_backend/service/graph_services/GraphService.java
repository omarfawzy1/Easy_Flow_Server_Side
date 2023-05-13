package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.service.graph_services.utils.GraphWithStations;

import java.util.List;

public interface GraphService {


    List<String> getOrderedStationOfLine(String lineId) throws NotFoundException;

    GraphWithStations getLineWeightedGraph(String lineId) throws NotFoundException;

    //concatinate all the lines of the owner with each other like the metro
    GraphWithStations getOwnerWeightedGraph(String ownerId);
}
