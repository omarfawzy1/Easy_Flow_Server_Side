package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.dto.Models.GraphModel;
import com.example.easy_flow_backend.dto.Models.Pair;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.graph_services.utils.GraphWithStations;

import java.util.List;

public interface GraphService {


    Pair<List<String>, List<Number>> getOrderedStationOfLine(String lineName) throws NotFoundException;

    GraphWithStations getLineWeightedGraph(String lineId) throws NotFoundException;

    //concatenate all the lines of the owner with each other like the metro
    GraphWithStations getOwnerWeightedGraph(String ownerId);

    ResponseMessage addGraph(GraphModel graphModel);

}
