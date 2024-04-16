package com.easy_flow_server.service.graph;

import com.easy_flow_server.dto.model.GraphModel;
import com.easy_flow_server.dto.model.Pair;
import com.easy_flow_server.entity.Station;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.service.graph.utils.GraphWithStations;

import java.util.List;

public interface GraphService {


    Pair<List<Station>, List<Number>> getOrderedStationOfLine(String lineName) throws NotFoundException;

    GraphWithStations getLineWeightedGraph(String lineId) throws NotFoundException;

    //concatenate all the lines of the owner with each other like the metro
    GraphWithStations getOwnerWeightedGraph(String ownerId);

    ResponseMessage addGraph(GraphModel graphModel);

}
